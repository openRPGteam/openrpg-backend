package info.openrpg.gameserver.utils;

import info.openrpg.gameserver.model.world.Chunk;

public interface IGeodata {
    Chunk[][] loadFullMap();

    int getWorldXSize();

    int getWorldYSize();

    Chunk getChunk(int x, int y);
}
