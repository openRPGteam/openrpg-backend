package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.enums.EventType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;

public class RemovePlayerEvent implements IEvent {
    private final int playerId;

    public RemovePlayerEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public EventType getEventType() {
        return EventType.REMOVEPLAYER;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public MoveDirections getDirection() {
        return null;
    }
}
