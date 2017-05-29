package info.openrpg.ejb.utils;

import info.openrpg.ejb.model.world.Chunk;

public class GeodataDBLoader implements IGeodata {
    @Override
    public Chunk[][] loadFullMap() {
        return new Chunk[0][];
    }

    @Override
    public int getWorldXSize() {
        return 0;
    }

    @Override
    public int getWorldYSize() {
        return 0;
    }

    @Override
    public int getWorldXSizeCells() {
        return 0;
    }

    @Override
    public int getWorldYSizeCells() {
        return 0;
    }

    @Override
    public Chunk getChunk(int x, int y) {
        return null;
    }
}
