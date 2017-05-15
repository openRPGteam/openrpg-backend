package info.openrpg.gameserver.model.actors;

import info.openrpg.gameserver.enums.GameObjectType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.world.Location;

public class GameObject extends AbstractActor {
    private GameObjectType objectType;

    public GameObject(Location curLocation) {
        super(curLocation);
    }

    @Override
    public void move(MoveDirections direction) {

    }
}
