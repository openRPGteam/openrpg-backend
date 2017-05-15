package info.openrpg.gameserver.model.actors;

import info.openrpg.gameserver.enums.GameObjectType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.world.Location;

public class GameObject extends AbstractActor {
    private GameObjectType objectType;
    private Integer objectId;

    public GameObject(GameObjectType objectType, Integer objectId, Location curLocation, IWorld currentWorld) {
        super(curLocation, currentWorld);
        this.objectType = objectType;
        this.objectId = objectId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    @Override
    public void move(MoveDirections direction) {

    }
}
