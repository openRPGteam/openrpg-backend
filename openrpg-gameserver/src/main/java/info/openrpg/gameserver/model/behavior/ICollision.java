package info.openrpg.gameserver.model.behavior;

import info.openrpg.gameserver.model.world.Location;

public interface ICollision {
    boolean CheckTerrain(Location location);

    boolean CheckActor(Location location);
}
