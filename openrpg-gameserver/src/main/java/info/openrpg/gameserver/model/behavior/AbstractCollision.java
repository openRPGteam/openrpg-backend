package info.openrpg.gameserver.model.behavior;

import com.google.inject.Inject;
import info.openrpg.gameserver.inject.IWorld;

public abstract class AbstractCollision implements ICollision {
    private IWorld currentWorld;

    @Inject
    public AbstractCollision(IWorld currentWorld) {
        this.currentWorld = currentWorld;
    }
}
