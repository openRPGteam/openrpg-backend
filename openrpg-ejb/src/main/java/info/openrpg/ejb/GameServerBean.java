package info.openrpg.ejb;

import info.openrpg.ejb.enums.MoveDirections;
import info.openrpg.ejb.model.actors.Player;
import info.openrpg.ejb.model.events.AddPlayerEvent;
import info.openrpg.ejb.model.events.MovePlayerEvent;
import info.openrpg.ejb.model.events.RemovePlayerEvent;
import info.openrpg.ejb.model.world.World;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import java.util.List;

@Stateless(name = "GameServerEJB")
@Startup
@LocalBean
public class GameServerBean {
    @EJB
    private World world;

    public GameServerBean() {
    }

    public void addPlayer(Player player) {
        AddPlayerEvent event = new AddPlayerEvent(player);
        world.addEvent(event);
    }

    public void removePlayer(Player player) {
        RemovePlayerEvent event = new RemovePlayerEvent(player);
        world.addEvent(event);
    }

    public int getPlayersCount() {
        return world.getAllPlayersCount();
    }

    public void movePlayer(Player player, MoveDirections moveDirections) {
        MovePlayerEvent event = new MovePlayerEvent(player, moveDirections);
        world.addEvent(event);
    }

    public List<Player> getPlayersInChunk(int chunk_x, int chunk_y) {
        return world.getPlayersByChunkXY(chunk_x, chunk_y);
    }

    public int getWorldDelay() {
        return world.getLoopDelay();
    }

    public int getChunkSize() {
        return world.getChunkSize();
    }

    public int getMapSizeX() {
        return world.getMapSizeX();
    }

    public int getMapSizeY() {
        return world.getMapSizeY();
    }


}
