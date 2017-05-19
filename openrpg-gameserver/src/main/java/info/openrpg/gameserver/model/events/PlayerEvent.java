package info.openrpg.gameserver.model.events;

import info.openrpg.gameserver.model.actors.Player;

public abstract class PlayerEvent implements IEvent {
    private final Player player;

    protected PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    public int getPlayerId() {
        return player.getPlayerId();
    }

    @Override
    public int hashCode() {
        return player.getPlayerId();
    }

    @Override
    public boolean equals(Object obj) {
        return player.getPlayerId() == ((PlayerEvent) obj).getPlayerId();
    }
}
