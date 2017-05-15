package info.openrpg.gameserver.model;

import info.openrpg.gameserver.enums.TerrainType;

public class Chunk {
    private final TerrainType[][] chunkmap;

    public Chunk(TerrainType[][] chunkmap) {
        this.chunkmap = chunkmap;
    }
}
