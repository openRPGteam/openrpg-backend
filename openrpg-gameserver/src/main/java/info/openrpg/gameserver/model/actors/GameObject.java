package info.openrpg.gameserver.model.actors;

import info.openrpg.gameserver.enums.GameObjectType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.world.Location;

public class GameObject extends AbstractActor {
    private GameObjectType objectType;
    private Integer objectId;

    public GameObject(GameObjectType objectType, Integer objectId, Location curLocation) {
        super(curLocation);
        this.objectType = objectType;
        this.objectId = objectId;
    }

    public Integer getObjectId() {
        return objectId;
    }

}