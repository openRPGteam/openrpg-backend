package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.enums.EventType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;

public class MovePlayerEvent implements IEvent {
    private final int playerId;
    private final MoveDirections moveDirections;

    public MovePlayerEvent(int playerId, MoveDirections moveDirections) {
        this.playerId = playerId;
        this.moveDirections = moveDirections;
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVEPLAYER;
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
        return moveDirections;
    }

    @Override
    public int hashCode() {
        return playerId;
    }

    @Override
    public boolean equals(Object obj) {
        return playerId == ((RemovePlayerEvent) obj).getPlayerId();
    }
}
