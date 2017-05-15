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

    }

    @Override
    public void removePlayer(Player player) {

    }
}
