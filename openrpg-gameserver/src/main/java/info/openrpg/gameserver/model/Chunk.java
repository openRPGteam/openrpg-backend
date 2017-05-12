package info.openrpg.gameserver.model;

public class Chunk {
    private final TerrainType[][] chunkmap;

    public Chunk(TerrainType[][] chunkmap) {
        this.chunkmap = chunkmap;
    }
}
