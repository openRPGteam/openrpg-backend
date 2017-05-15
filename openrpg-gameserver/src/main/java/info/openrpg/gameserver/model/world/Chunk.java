package info.openrpg.gameserver.model.world;

import info.openrpg.gameserver.enums.TerrainType;

public class Chunk {
    private final TerrainType[][] chunkmap;

    public Chunk(TerrainType[][] chunkmap) {
        this.chunkmap = chunkmap;
    }

    public TerrainType getTerrainFromChunkByXY(int x, int y) {
        return chunkmap[x - 1][y - 1];
    }
}
