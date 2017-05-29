package info.openrpg.ejb.model.actors;

import info.openrpg.ejb.enums.GameObjectType;
import info.openrpg.ejb.model.world.Location;

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
