package info.openrpg.ejb.model.events;

import info.openrpg.ejb.enums.EventType;
import info.openrpg.ejb.enums.MoveDirections;
import info.openrpg.ejb.model.actors.Player;

public interface IEvent {
    EventType getEventType();

    Player getPlayer();

    int getPlayerId();

    MoveDirections getDirection();
}
