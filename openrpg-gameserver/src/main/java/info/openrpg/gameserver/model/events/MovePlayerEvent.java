package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.enums.EventType;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;

public class MovePlayerEvent implements IEvent {
    private final Player player;
    private final MoveDirections moveDirections;

    public MovePlayerEvent(Player player, MoveDirections moveDirections) {
        this.player = player;
        this.moveDirections = moveDirections;
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVEPLAYER;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public MoveDirections getDirection() {
        return moveDirections;
    }
}
