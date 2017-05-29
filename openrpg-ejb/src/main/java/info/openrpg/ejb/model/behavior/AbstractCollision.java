package info.openrpg.ejb.model.behavior;

import info.openrpg.ejb.model.world.World;

import javax.ejb.EJB;
import javax.inject.Inject;

public abstract class AbstractCollision implements ICollision {

    @EJB
    protected World currentWorld;

    @Inject
    public AbstractCollision(World currentWorld) {
        this.currentWorld = currentWorld;
    }
}
