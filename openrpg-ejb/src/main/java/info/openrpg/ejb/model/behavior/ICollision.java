package info.openrpg.ejb.model.behavior;

import info.openrpg.ejb.model.world.Location;

public interface ICollision {
    boolean CheckTerrain(Location location);

    boolean CheckActor(Location location);
}
