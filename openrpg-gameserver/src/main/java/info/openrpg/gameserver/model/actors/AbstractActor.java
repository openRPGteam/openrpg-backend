package info.openrpg.gameserver.model.actors;

import com.google.inject.Inject;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.world.Location;

public abstract class AbstractActor implements MoveableActor {
    final IWorld currentWorld;
    Location curLocation;

    @Inject
    public AbstractActor(Location curLocation, IWorld currentWorld) {
        this.currentWorld = currentWorld;
        this.curLocation = curLocation;
    }
}
