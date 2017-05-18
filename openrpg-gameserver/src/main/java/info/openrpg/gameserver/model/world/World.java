package info.openrpg.gameserver.model.world;

import info.openrpg.gameserver.enums.TerrainType;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.actors.GameObject;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.events.IEvent;

import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class World implements IWorld {
    public static final Logger LOG = Logger.getLogger(World.class.getSimpleName());
    // Размер чанка 100x100
    public final int CHUNK_SIZE = 10;
    // Размер карты 10х10 чанков
    public final int MAP_SIZE_X = 10;

    //хешмап для игроков
    private final Map<Integer, Player> players = new ConcurrentHashMap<>();
    //хешмап для прочих динамических обьектов
    private final Map<Integer, GameObject> globalObjectsMap = new ConcurrentHashMap<>();

    //карта мира
    private final Chunk[][] worldChunks;

    //очередь событий
    private final Queue<IEvent> eventsQuene = new ConcurrentLinkedQueue();
    ;

    public World() {
        worldChunks = initchunks();
        runGlobalLoop();
    }

    private void runGlobalLoop() {
        ScheduledExecutorService globalLoop = Executors.newSingleThreadScheduledExecutor();
        ExecutorService taskPool = Executors.newCachedThreadPool();

        Runnable pereodicQueueExecutor = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " preiodic tread in " + new Date());
                synchronized (eventsQuene) {
                    //LOG.info("start processing blocked queue");
                    while (true) {
                        IEvent event = eventsQuene.poll();
                        if (event == null) {
                            System.out.println(Thread.currentThread().getName() + " preiodic tread out " + new Date());
                            return;
                        }
                        taskPool.execute(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getName() + " internal tread start " + new Date());
                                try {
                                    switch (event.getEventType()) {
                                        case ADDPLAYER: {
                                            addPlayer(event.getPlayer());
                                            break;
                                        }
                                        case REMOVEPLAYER: {
                                            removePlayerById(event.getPlayerId());
                                            break;
                                        }
                                        case MOVEPLAYER: {
                                            getPlayerById(event.getPlayerId()).move(event.getDirection());
                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                    LOG.severe("Exception processing event queue");
                                }
                                System.out.println(Thread.currentThread().getName() + " tread stop " + new Date());
                            }
                        });
                    }
                }
            }
        };

        ScheduledFuture<?> periodicFuture = globalLoop.scheduleWithFixedDelay(pereodicQueueExecutor, 0, 5, TimeUnit.SECONDS);
    }

    public void addEvent(IEvent event) {
        eventsQuene.add(event);
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
    public void addPlayer(Player player) {
        player.bindWorld(this);
        if (players.containsKey(player.getPlayerId())) {
            LOG.warning("Player " + player.getName() + " already in playersmap");
            return;
        }
        players.put(player.getPlayerId(), player);
        LOG.info("Player " + player.getName() + " put in playersmap");
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

    public int getChunkSize() {
        return CHUNK_SIZE;
    }

    public int getMapSizeX() {
        return MAP_SIZE_X;
    }
}
