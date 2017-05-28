package info.openrpg.gameserver.model.actors;

import info.openrpg.gameserver.enums.GameObjectType;
import info.openrpg.gameserver.model.world.Location;

public class GameObject extends AbstractActor {
    private GameObjectType objectType;

    public GameObject(GameObjectType objectType, Integer objectId, Location curLocation) {
        super(curLocation, objectId);
        this.objectType = objectType;
    }

    public Integer getObjectId() {
        return getActorId();
    }

}
