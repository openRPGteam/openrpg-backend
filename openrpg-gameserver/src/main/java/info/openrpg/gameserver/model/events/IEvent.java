package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.enums.EventType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;

public interface IEvent {
    EventType getEventType();

    Player getPlayer();

    int getPlayerId();

    MoveDirections getDirection();
}
