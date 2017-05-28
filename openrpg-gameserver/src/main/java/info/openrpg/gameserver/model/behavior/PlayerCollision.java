package info.openrpg.gameserver.model.behavior;

import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.world.Location;

public class PlayerCollision extends AbstractCollision {

    public PlayerCollision(IWorld currentWorld) {
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
