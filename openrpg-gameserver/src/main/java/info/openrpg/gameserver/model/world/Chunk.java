package info.openrpg.gameserver.model.world;

import info.openrpg.gameserver.enums.TerrainType;

public class Chunk {
    private final TerrainType[][] chunkmap;

    public Chunk() {
        this.chunkmap = null;
    }

    public Chunk(TerrainType[][] chunkmap) {
        this.chunkmap = chunkmap;
    }

    public TerrainType getTerrainFromChunkByXY(int x, int y) {
        return chunkmap[x - 1][y - 1];
    }

    public TerrainType[][] getChunkmap() {
        return chunkmap;
    }

    public String printChunk() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chunkmap.length; i++) {
            for (int y = 0; y < chunkmap[i].length; y++) {
                sb.append(chunkmap[i][y].name());
            }
            sb.append("\t\n");
        }
        return sb.toString();
    }

    public String printLiteralChunk() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chunkmap.length; i++) {
            for (int y = 0; y < chunkmap[i].length; y++) {
                sb.append(chunkmap[i][y].ordinal());
            }
            sb.append("\t\n");
        }
        return sb.toString();
    }
}
