package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.enums.EventType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;

public class MovePlayerEvent extends PlayerEvent {
    private final MoveDirections moveDirections;

    public MovePlayerEvent(Player player, MoveDirections moveDirections) {
        super(player);
        this.moveDirections = moveDirections;
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVEPLAYER;
    }

    @Override
    public MoveDirections getDirection() {
        return moveDirections;
    }

    @Override
    public boolean equals(Object obj) {
        return player.getPlayerId() == ((PlayerEvent) obj).getPlayerId() &&
                getEventType() == ((IEvent) obj).getEventType();
    }

    @Override
    public int hashCode() {
        return 1 * 32 + (int) player.getPlayerId() + (int) getEventType().hashCode();
    }
}
