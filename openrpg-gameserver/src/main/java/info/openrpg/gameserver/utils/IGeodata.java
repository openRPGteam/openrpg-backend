package info.openrpg.gameserver.utils;

import info.openrpg.gameserver.model.world.Chunk;

public interface IGeodata {
    Chunk[][] loadFullMap();

    int getWorldSize();
}
