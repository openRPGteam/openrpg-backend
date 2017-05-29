package info.openrpg.ejb.model.actors;

import info.openrpg.ejb.enums.MoveDirections;
import info.openrpg.ejb.model.behavior.PlayerCollision;
import info.openrpg.ejb.model.world.Location;
import info.openrpg.ejb.model.world.World;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

public abstract class AbstractActor implements MoveableActor, Serializable {
    public static final Logger LOG = Logger.getLogger(AbstractActor.class.getName());
    private static final long serialVersionUID = 4526282569827006593L;
    private final Integer actorId;

    @EJB
    protected World currentWorld;

    private Location curLocation;

    @Inject
    public AbstractActor(Location curLocation, Integer actorId) {
        this.curLocation = curLocation;
        this.actorId = actorId;
    }

    public Integer getActorId() {
        return actorId;
    }

    @Inject
    public void bindWorld(World currentWorld) {
        this.currentWorld = currentWorld;
        currentWorld.putPlayerToXYChunk(actorId, this.curLocation.getChunk_x(), this.curLocation.getChunk_y());
    }

    public Location getCurLocation() {
        return curLocation;
    }

    public void setCurLocation(Location curLocation) {
        this.curLocation = curLocation;
        currentWorld.putPlayerToXYChunk(actorId, this.curLocation.getChunk_x(), this.curLocation.getChunk_y());
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
                if (getCurLocation().getX() == 0 && getCurLocation().getY() == 0)
                    newloc = new Location(currentWorld.getChunkSize() - 1, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y() - 1);
                else if (getCurLocation().getY() == 0)
                    newloc = new Location(getCurLocation().getX() - 1, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() - 1);
                else if (getCurLocation().getX() == 0)
                    newloc = new Location(currentWorld.getChunkSize() - 1, getCurLocation().getY() - 1, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y());
                else
                    newloc = new Location(getCurLocation().getX() - 1, getCurLocation().getY() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case WEST: {
                if (getCurLocation().getY() == 0)
                    newloc = new Location(getCurLocation().getX(), currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() - 1);
                else
                    newloc = new Location(getCurLocation().getX(), getCurLocation().getY() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
            case SOUTHWEST: {
                if (getCurLocation().getY() == 0 && getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y() - 1);
                else if (getCurLocation().getY() == 0)
                    newloc = new Location(getCurLocation().getX() + 1, currentWorld.getChunkSize() - 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() - 1);
                else if (getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, getCurLocation().getY() - 1, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y());
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
                if (getCurLocation().getY() == currentWorld.getChunkSize() - 1 && getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, 0, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y() + 1);
                else if (getCurLocation().getX() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(0, getCurLocation().getY() + 1, getCurLocation().getChunk_x() + 1, getCurLocation().getChunk_y());
                else if (getCurLocation().getY() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(getCurLocation().getX() + 1, 0, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() + 1);
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
                if (getCurLocation().getX() == 0 && getCurLocation().getY() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(currentWorld.getChunkSize() - 1, 0, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y() + 1);
                else if (getCurLocation().getY() == currentWorld.getChunkSize() - 1)
                    newloc = new Location(getCurLocation().getX() - 1, 0, getCurLocation().getChunk_x(), getCurLocation().getChunk_y() + 1);
                else if (getCurLocation().getX() == 0)
                    newloc = new Location(currentWorld.getChunkSize() - 1, getCurLocation().getY() + 1, getCurLocation().getChunk_x() - 1, getCurLocation().getChunk_y());
                else
                    newloc = new Location(getCurLocation().getX() - 1, getCurLocation().getY() + 1, getCurLocation().getChunk_x(), getCurLocation().getChunk_y());
                break;
            }
        }
        if (pc.CheckTerrain(newloc)) {
            if (pc.CheckActor(newloc)) {
                this.setCurLocation(newloc);
                LOG.info("Actor move to " + direction.name());
            } else {
                LOG.info("Actor cannot move");
            }
        } else {
            LOG.info("Actor cannot move");
        }

    }
}
