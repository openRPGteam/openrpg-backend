package info.openrpg.gameserver.model.actors;

import com.google.inject.Inject;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.behavior.PlayerCollision;
import info.openrpg.gameserver.model.world.Location;

import java.util.logging.Logger;

public abstract class AbstractActor implements MoveableActor {
    public static final Logger LOG = Logger.getLogger(AbstractActor.class.getName());
    final IWorld currentWorld;
    private Location curLocation;

    @Inject
    public AbstractActor(Location curLocation, IWorld currentWorld) {
        this.currentWorld = currentWorld;
        this.curLocation = curLocation;
    }

    public Location getCurLocation() {
        return curLocation;
    }

    public void setCurLocation(Location curLocation) {
        this.curLocation = curLocation;
    }

    @Override
    public void move(MoveDirections direction) {
        //TODO нужно как то отрефакторить
        PlayerCollision pc = new PlayerCollision(this.currentWorld);
        Location newloc = null;
        switch (direction) {
            case NORTH: {
                if (getCurLocation().getX() == 0)
                    newloc = new Location(currentWorld.getChunkSize() - 1, getCurLocation().getY(), getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y());
                else
                    newloc = new Location(getCurLocation().getX() - 1, getCurLocation().getY(), getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case NORTHWEST: {
                if (getCurLocation().getX() == 0)
                    newloc = new Location(currentWorld.getChunkSize() - 1, getCurLocation().getY() - 1, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y());
                else if (getCurLocation().getY() == 0)
                    newloc = new Location(getCurLocation().getX() - 1, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() - 1);
                else if (getCurLocation().getX() == 0 && getCurLocation().getY() == 0)
                    newloc = new Location(currentWorld.getChunkSize() - 1, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y() - 1);
                else
                    newloc = new Location(getCurLocation().getX() - 1, getCurLocation().getY() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case WEST: {
                if (getCurLocation().getY() == 0)
                    newloc = new Location(getCurLocation().getX(), currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() - 1);
                newloc = new Location(getCurLocation().getX(), getCurLocation().getY() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case SOUTHWEST: {
                if (getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, getCurLocation().getY() - 1, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y());
                else if (getCurLocation().getY() == 0)
                    newloc = new Location(getCurLocation().getX() + 1, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() - 1);
                else if (getCurLocation().getY() == 0 && getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y() - 1);
                else
                    newloc = new Location(getCurLocation().getX() + 1, getCurLocation().getY() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case SOUTH: {
                if (getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, getCurLocation().getY(), getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y());
                else
                    newloc = new Location(getCurLocation().getX() + 1, getCurLocation().getY(), getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case SOUTHEAST: {
                if (getCurLocation().getY() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(getCurLocation().getX() + 1, 0, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() + 1);
                else if (getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, getCurLocation().getY() + 1, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y());
                else if (getCurLocation().getY() == currentWorld.getChunkSize() - 1 && getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, 0, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y() + 1);
                else
                    newloc = new Location(getCurLocation().getX() + 1, getCurLocation().getY() + 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case EAST: {
                if (getCurLocation().getY() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(getCurLocation().getX(), 0, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() + 1);
                else
                    newloc = new Location(getCurLocation().getX(), getCurLocation().getY() + 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case NORTHEAST: {
                if (getCurLocation().getX() == 0)
                    newloc = new Location(currentWorld.getChunkSize() - 1, getCurLocation().getY() + 1, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y());
                else if (getCurLocation().getY() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(getCurLocation().getX() - 1, 0, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() + 1);
                else if (getCurLocation().getX() == 0 && getCurLocation().getY() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(currentWorld.getChunkSize() - 1, 0, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y() + 1);
                else
                    newloc = new Location(getCurLocation().getX() - 1, getCurLocation().getY() + 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
        }
        if (pc.CheckTerrain(newloc)) {
            if (pc.CheckActor(newloc)) {
                this.setCurLocation(newloc);
            } else {
                LOG.info("Actor cannot move");
            }
        } else {
            LOG.info("Actor cannot move");
        }

    }
}
