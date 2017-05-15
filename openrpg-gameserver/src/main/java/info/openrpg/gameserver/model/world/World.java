package info.openrpg.gameserver.model.world;

import info.openrpg.gameserver.enums.TerrainType;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.actors.GameObject;
import info.openrpg.gameserver.model.actors.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class World implements IWorld {
    public static final Logger LOG = Logger.getLogger(World.class.getName());
    // Размер чанка 100x100
    public static final int CHUNK_SIZE = 100;
    // Размер карты 10х10 чанков
    public static final int MAP_SIZE_X = 10;

    //хешмап для игроков
    private final Map<Integer, Player> players = new ConcurrentHashMap<>();
    //хешмап для прочих динамических обьектов
    private final Map<Integer, GameObject> globalObjectsMap = new ConcurrentHashMap<>();


    private Chunk[][] worldChunks;

    public World() {
        initchunks();
    }

    //TODO из базы подгружать
    private void initchunks() {
        Chunk[][] random = new Chunk[MAP_SIZE_X - 1][MAP_SIZE_X - 1];
        for (int i = 0; i < MAP_SIZE_X - 1; i++) {
            for (int j = 0; j < MAP_SIZE_X - 1; j++) {
                random[i][j] = this.randomchunk(TerrainType.EARTH);
            }
        }
    }

    private Chunk randomchunk(TerrainType type) {
        TerrainType[][] random = new TerrainType[CHUNK_SIZE - 1][CHUNK_SIZE - 1];
        for (int i = 0; i < MAP_SIZE_X - 1; i++) {
            for (int j = 0; j < MAP_SIZE_X - 1; j++) {
                random[i][j] = type;
            }
        }
        return new Chunk(random);
    }


    @Override
    public void addPlayer(Player player) {
        if (players.containsKey(player.getPlayerId())) {
            LOG.warning("Player " + player.toString() + " already in playersmap");
            return;
        }
        players.put(player.getPlayerId(), player);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
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
}
