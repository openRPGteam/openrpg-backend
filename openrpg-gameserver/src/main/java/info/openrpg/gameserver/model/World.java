package info.openrpg.gameserver.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class World {
    public static final Logger LOG = Logger.getLogger(World.class.getName());
    // Размер чанка 100x100
    public static final int CHUNK_SIZE = 100;
    // Размер карты 10х10 чанков
    public static final int MAP_SIZE_X = 10;


    private final Map<Integer, Player> players = new ConcurrentHashMap<>();

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


}
