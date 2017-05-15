package info.openrpg.gameserver.inject;

import info.openrpg.gameserver.model.actors.Player;

public interface IWorld {
    void addPlayer(Player player);

    void removePlayer(Player player);
}
