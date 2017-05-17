package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.enums.EventType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;

public class RemovePlayerEvent implements IEvent {
    private final Player player;

    public RemovePlayerEvent(Player player) {
        this.player = player;
    }

    @Override
    public EventType getEventType() {
        return EventType.REMOVEPLAYER;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public MoveDirections getDirection() {
        return null;
    }
}
