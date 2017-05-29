package info.openrpg.ejb.model.world;

import info.openrpg.ejb.enums.TerrainType;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    private final TerrainType[][] chunkmap;
    private List<Integer> playersIdInChunks = new ArrayList<>();
    private List<Integer> objectsIdInChunks = new ArrayList<>();

    public Chunk() {
        this.chunkmap = null;
    }

    public Chunk(TerrainType[][] chunkmap) {
        this.chunkmap = chunkmap;
    }

    public List<Integer> getPlayersIdInChunks() {
        return playersIdInChunks;
    }

    public void addPlayerInChunk(Integer playerId) {
        if (!playersIdInChunks.contains(playerId)) {
            playersIdInChunks.add(playerId);
        }
    }

    public void deletePlayerInChunk(Integer playerId) {
        if (playersIdInChunks.contains(playerId)) {
            playersIdInChunks.remove(playerId);
        }
    }

    public List<Integer> getObjectsIdInChunks() {
        return objectsIdInChunks;
    }

    public void addObjectInChunk(Integer objectId) {
        if (!objectsIdInChunks.contains(objectId)) {
            objectsIdInChunks.add(objectId);
        }
    }

    public void deleteObjectInChunk(Integer objectId) {
        if (objectsIdInChunks.contains(objectId)) {
            objectsIdInChunks.remove(objectId);
        }
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
