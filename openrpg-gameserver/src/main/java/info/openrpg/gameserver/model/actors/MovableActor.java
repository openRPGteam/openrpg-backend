package info.openrpg.gameserver.model.actors;

import info.openrpg.gameserver.enums.MoveDirections;

public interface MovableActor {
    public void move(MoveDirections direction);
}
