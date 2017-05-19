package info.openrpg.gameserver.model.world;

import info.openrpg.gameserver.enums.TerrainType;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.actors.AbstractActor;
import info.openrpg.gameserver.model.actors.GameObject;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.events.IEvent;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class World implements IWorld {
    public static final Logger LOG = Logger.getLogger(World.class.getSimpleName());
    // Размер чанка 9x9
    public final int CHUNK_SIZE = 9;
    // Размер карты 10х10 чанков
    public final int MAP_SIZE_X = 10;

    //хешмап для игроков
    private final Map<Integer, Player> players = new ConcurrentHashMap<>();
    //хешмап для прочих динамических обьектов
    private final Map<Integer, GameObject> globalObjectsMap = new ConcurrentHashMap<>();

    //карта мира
    private final Chunk[][] worldChunks;

    //очередь событий
    private final LinkedBlockingQueue<IEvent> eventsQuene = new LinkedBlockingQueue();

    public World() {
        worldChunks = initchunks();
        runGlobalLoop();
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
                        System.out.println(e.getMessage());
                    }
                }

                for (IEvent event : eventHashSet) {
                    System.out.println(Thread.currentThread().getName() + " with " + event.getEventType() + " " + event.getDirection() + " tread start " + new Date());
                    try {
                        switch (event.getEventType()) {
                             /*   case ADDPLAYER: {
                                    addPlayer(event.getPlayer());
                                    break;
                                }
                                case REMOVEPLAYER: {
                                    removePlayerById(event.getPlayerId());
                                    break;
                                } */
                            case MOVEPLAYER: {
                                taskPool.execute(() -> {
                                    System.out.println(Thread.currentThread().getName() + " " + getPlayerById(event.getPlayerId()).getName() + " to " + event.getDirection());
                                    getPlayerById(event.getPlayerId()).move(event.getDirection());
                                });
                                break;
                            }
                        }
                    } catch (Exception e) {
                        LOG.severe("Exception processing event queue" + e.getMessage());
                    }
                    System.out.println(Thread.currentThread().getName() + " tread stop " + new Date());
                }
            }
        };
        globalLoop.scheduleWithFixedDelay(periodicQueueExecutor, 5, 5, TimeUnit.SECONDS);
    }

    public void addEvent(IEvent event) {
        try {
            synchronized (eventsQuene) {
                eventsQuene.put(event);
                System.out.println(Thread.currentThread().getName() + " add event " + new Date());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //TODO из базы подгружать
    public Chunk[][] initchunks() {
        Chunk[][] random = new Chunk[MAP_SIZE_X][MAP_SIZE_X];
        for (int i = 0; i < MAP_SIZE_X; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                random[i][j] = this.randomchunk(TerrainType.EARTH);
            }
        }
        return random;
    }

    public Chunk randomchunk(TerrainType type) {
        TerrainType[][] random = new TerrainType[CHUNK_SIZE][CHUNK_SIZE];
        for (int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < CHUNK_SIZE; j++) {
                random[i][j] = type;
            }
        }
        return new Chunk(random);
    }


    @Override
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

    @Override
    public void removePlayer(Player player) {
        players.remove(player.getPlayerId(), player);
        LOG.info("Player " + player.getName() + " removed from playersmap");
    }

    public void removePlayerById(int playerId) {
        players.remove(playerId);
        LOG.info("Player with id" + playerId + " removed from playersmap");
    }

    @Override
    public Player getPlayerById(int playerId) {
        return players.get(playerId);
    }

    @Override
    public Map<Integer, Player> getAllPlayers() {
        return players;
    }

    @Override
    public int getAllPlayersCount() {
        return players.size();
    }

    @Override
    public void addGameObject(GameObject gameObject) {
        if (globalObjectsMap.containsKey(gameObject.getObjectId())) {
            LOG.warning("GameObject " + gameObject.toString() + " already in gameobjectsmap");
            return;
        }
        globalObjectsMap.put(gameObject.getObjectId(), gameObject);
    }

    @Override
    public void removeGameObject(GameObject gameObject) {
        globalObjectsMap.remove(gameObject.getObjectId());
    }

    @Override
    public GameObject getGameObject(int objectId) {
        return globalObjectsMap.get(objectId);
    }

    @Override
    public Map<Integer, GameObject> getAllGameObjects() {
        return globalObjectsMap;
    }

    @Override
    public int getAllGameObjectsCount() {
        return globalObjectsMap.size();
    }

    public Chunk[][] getWorldChunks() {
        return worldChunks;
    }

    @Override
    public Chunk getChunkByXY(int chunk_x, int chunk_y) {

        return worldChunks[chunk_x][chunk_y];
    }

    @Override
    public Optional<Chunk> getChunkByUserId(int id) {
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
}
