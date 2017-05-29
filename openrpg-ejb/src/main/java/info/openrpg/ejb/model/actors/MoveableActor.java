package info.openrpg.ejb.model.actors;

import info.openrpg.ejb.enums.MoveDirections;

public interface MoveableActor {

    public void move(MoveDirections direction);
}
