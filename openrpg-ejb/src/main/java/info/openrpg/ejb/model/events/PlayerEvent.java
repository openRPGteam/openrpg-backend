package info.openrpg.ejb.model.events;

import info.openrpg.ejb.model.actors.Player;

public abstract class PlayerEvent implements IEvent {
    protected final Player player;

    protected PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    public int getPlayerId() {
        return player.getPlayerId();
    }


}
