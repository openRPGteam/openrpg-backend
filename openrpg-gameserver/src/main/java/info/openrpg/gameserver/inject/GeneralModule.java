package info.openrpg.gameserver.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import info.openrpg.gameserver.model.world.World;

public class GeneralModule extends AbstractModule {
    protected void configure() {
        bind(IWorld.class).to(World.class).in(Scopes.SINGLETON);
    }
}
