package info.openrpg.gameserver.utils;

import info.openrpg.gameserver.model.world.Chunk;

public interface IGeodata {
    Chunk[][] loadFullMap();

    int getWorldXSize();
    int getWorldYSize();

    int getWorldXSizeCells();

    int getWorldYSizeCells();
    Chunk getChunk(int x, int y);
}
