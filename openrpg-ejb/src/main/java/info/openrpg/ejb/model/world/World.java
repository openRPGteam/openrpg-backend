package info.openrpg.ejb.model.world;

import info.openrpg.ejb.enums.EventType;
import info.openrpg.ejb.model.actors.AbstractActor;
import info.openrpg.ejb.model.actors.GameObject;
import info.openrpg.ejb.model.actors.Player;
import info.openrpg.ejb.model.events.IEvent;
import info.openrpg.ejb.model.events.PlayerEvent;
import info.openrpg.ejb.utils.GeodataFSLoader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
@Named("WorldBean")
public class World {
    public static final Logger LOG = Logger.getLogger(World.class.getSimpleName());
    // Размер чанка 9x9
    public final int CHUNK_SIZE = 9;
    //время глобального тика
    public final int GLOBALTIME = 3;
    //хешмап для игроков
    private final Map<Integer, Player> players = new ConcurrentHashMap<>();
    //хешмап для прочих динамических обьектов
    private final Map<Integer, GameObject> globalObjectsMap = new ConcurrentHashMap<>();
    //очередь событий
    private final LinkedBlockingQueue<IEvent> eventsQuene = new LinkedBlockingQueue<>();
    // Размер карты 10х10 чанков
    public int MAP_SIZE_X = 45;
    public int MAP_SIZE_Y = 34;
    //карта мира
    private Chunk[][] worldChunks;

    public World() {
    }

    @PostConstruct
    public void init() {
        GeodataFSLoader geodataFSLoader = null;
        try {
            geodataFSLoader = new GeodataFSLoader();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            worldChunks = geodataFSLoader.loadFullMap();
            runGlobalLoop();
        }

    }

    private void runGlobalLoop() {
        ScheduledExecutorService globalLoop = Executors.newSingleThreadScheduledExecutor();
        ExecutorService taskPool = Executors.newFixedThreadPool(100);

        Runnable periodicQueueExecutor = () -> {
            System.out.println(Thread.currentThread().getName() + " preiodic tread in " + new Date());

            while (!Thread.currentThread().isInterrupted()) {
                Set<IEvent> eventHashSet = new LinkedHashSet<>();
                synchronized (eventsQuene) {
                    try {
                        eventsQuene.drainTo(eventHashSet);
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.getMessage());
                    }
                }
                LinkedHashSet<IEvent> addPlayerEvents = eventHashSet.stream()
                        .filter(p -> p.getEventType() == EventType.ADDPLAYER)
                        .collect(Collectors.toCollection(LinkedHashSet::new));
                addPlayerEvents.forEach(e -> taskPool.execute(() -> addPlayer(e.getPlayer())));
                eventHashSet.removeAll(addPlayerEvents);

                LinkedHashSet<IEvent> AnotherPlayerEvents = eventHashSet.stream()
                        .filter(p ->
                                ((p.getEventType() != EventType.ADDPLAYER) && (p.getEventType() != EventType.REMOVEPLAYER)))
                        .collect(Collectors.toCollection(LinkedHashSet::new));
                AnotherPlayerEvents.forEach(e -> taskPool.execute(() -> getPlayerById(e.getPlayerId()).move(e.getDirection())));
                eventHashSet.removeAll(AnotherPlayerEvents);

                LinkedHashSet<IEvent> removePlayerEvents = eventHashSet.stream()
                        .filter(p -> p.getEventType() == EventType.REMOVEPLAYER)
                        .collect(Collectors.toCollection(LinkedHashSet::new));
                removePlayerEvents.forEach(e -> taskPool.execute(() -> removePlayer(e.getPlayer())));


            }
        };
        globalLoop.scheduleWithFixedDelay(periodicQueueExecutor, 0, GLOBALTIME, TimeUnit.SECONDS);
    }

    public void addEvent(PlayerEvent event) {
        try {
            synchronized (eventsQuene) {
                eventsQuene.put(event);
                System.out.println(Thread.currentThread().getName() + " add event " + event.getEventType() + " " + new Date());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean addPlayer(Player player) {
        player.bindWorld(this);
        if (players.containsKey(player.getPlayerId())) {
            LOG.warning("Player " + player.getName() + " already in playersmap");
            return false;
        }
        players.put(player.getPlayerId(), player);
        LOG.info("Player " + player.getName() + " put in playersmap");
        return true;
    }

    public void removePlayer(Player player) {
        players.remove(player.getPlayerId(), player);
        removePlayerFromXYChunk(player.getPlayerId(), player.getCurLocation().getChunk_x(), player.getCurLocation().getChunk_y());
        LOG.info("Player " + player.getName() + " removed from playersmap");
    }

    public Player getPlayerById(int playerId) {
        return players.get(playerId);
    }


    public Map<Integer, Player> getAllPlayers() {
        return players;
    }


    public int getAllPlayersCount() {
        return players.size();
    }


    public void addGameObject(GameObject gameObject) {
        if (globalObjectsMap.containsKey(gameObject.getObjectId())) {
            LOG.warning("GameObject " + gameObject.toString() + " already in gameobjectsmap");
            return;
        }
        globalObjectsMap.put(gameObject.getObjectId(), gameObject);
    }


    public void removeGameObject(GameObject gameObject) {
        globalObjectsMap.remove(gameObject.getObjectId());
    }


    public GameObject getGameObject(int objectId) {
        return globalObjectsMap.get(objectId);
    }


    public Map<Integer, GameObject> getAllGameObjects() {
        return globalObjectsMap;
    }


    public int getAllGameObjectsCount() {
        return globalObjectsMap.size();
    }

    public Chunk[][] getWorldChunks() {
        return worldChunks;
    }


    public Chunk getChunkByXY(int chunk_x, int chunk_y) {

        return worldChunks[chunk_x][chunk_y];
    }


    public Optional<Chunk> getChunkByPlayerId(int id) {
        return Optional.ofNullable(getAllPlayers().get(id))
                .map(AbstractActor::getCurLocation)
                .map(location -> Optional.of(worldChunks[location.getChunk_x()][location.getChunk_y()]))
                .orElseGet(Optional::empty);
    }

    public int getChunkSize() {
        return CHUNK_SIZE;
    }

    public int getMapSizeX() {
        return MAP_SIZE_X;
    }

    public int getMapSizeY() {
        return MAP_SIZE_Y;
    }

    public int getLoopDelay() {
        return GLOBALTIME;
    }

    public void putPlayerToXYChunk(int playerId, int x, int y) {
        if (x < getMapSizeX() && y < getMapSizeY()) {
            worldChunks[x][y].addPlayerInChunk(playerId);
        }
    }

    public void removePlayerFromXYChunk(int playerId, int x, int y) {
        if (x < getMapSizeX() && y < getMapSizeY()) {
            worldChunks[x][y].deletePlayerInChunk(playerId);
        }
    }


    public List<Player> getPlayersByChunkXY(int chunk_x, int chunk_y) {
        List<Player> result = (getWorldChunks()[chunk_x][chunk_y].getPlayersIdInChunks()).stream()
                .map(this::getPlayerById).collect(Collectors.toList());
        return result;
    }
}
