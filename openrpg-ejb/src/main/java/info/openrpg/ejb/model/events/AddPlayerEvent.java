package info.openrpg.ejb.model.events;

import info.openrpg.ejb.enums.EventType;
import info.openrpg.ejb.enums.MoveDirections;
import info.openrpg.ejb.model.actors.Player;

public class AddPlayerEvent extends PlayerEvent {
    public AddPlayerEvent(Player player) {
        super(player);
    }

    @Override
    public EventType getEventType() {
        return EventType.ADDPLAYER;
    }

    @Override
    public MoveDirections getDirection() {
        return null;
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
