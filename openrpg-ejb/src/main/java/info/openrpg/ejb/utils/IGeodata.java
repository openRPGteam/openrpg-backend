package info.openrpg.ejb.utils;

import info.openrpg.ejb.model.world.Chunk;

public interface IGeodata {
    Chunk[][] loadFullMap();

    int getWorldXSize();

    int getWorldYSize();

    int getWorldXSizeCells();

    int getWorldYSizeCells();

    Chunk getChunk(int x, int y);
}
