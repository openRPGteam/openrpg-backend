package info.openrpg.ejb.model.behavior;

import info.openrpg.ejb.model.world.Location;
import info.openrpg.ejb.model.world.World;

import javax.inject.Inject;

public class PlayerCollision extends AbstractCollision {

    @Inject
    public PlayerCollision(World currentWorld) {
        super(currentWorld);
    }

    @Override
    public boolean CheckTerrain(Location location) {
        return (location.getChunk_x() >= 0 &&
                location.getChunk_y() >= 0 &&
                location.getChunk_x() < currentWorld.getMapSizeX() &&
                location.getChunk_y() < currentWorld.getMapSizeY() &&
                currentWorld.getWorldChunks()[location.getChunk_x()][location.getChunk_y()]
                        .getChunkmap()[location.getX()][location.getY()].isPassable());
    }

    @Override
    public boolean CheckActor(Location location) {
        return true;
    }
}
