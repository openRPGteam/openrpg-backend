package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.enums.EventType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;

public class RemovePlayerEvent extends PlayerEvent {
    public RemovePlayerEvent(Player player) {
        super(player);
    }

    @Override
    public EventType getEventType() {
        return EventType.REMOVEPLAYER;
    }

    @Override
    public MoveDirections getDirection() {
        return null;
    }
}
