package info.openrpg.utils;

import info.openrpg.gameserver.model.Chunk;

public interface IGeodata {
    Chunk[][] loadFullMap();

    int getWorldXSize();

    int getWorldYSize();

    int getWorldXSizeCells();

    int getWorldYSizeCells();

    Chunk getChunk(int x, int y);
}
