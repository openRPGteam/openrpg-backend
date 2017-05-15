package info.openrpg.gameserver.model.behavior;

import com.google.inject.Inject;
import info.openrpg.gameserver.model.world.World;

public abstract class AbstractCollision implements ICollision {
    private World currentWorld;

    @Inject
    public AbstractCollision(World currentWorld) {
        this.currentWorld = currentWorld;
    }
}
