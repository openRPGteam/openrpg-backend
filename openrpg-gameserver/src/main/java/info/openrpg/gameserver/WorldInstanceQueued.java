package info.openrpg.gameserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.inject.GeneralModule;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.events.MovePlayerEvent;
import info.openrpg.gameserver.model.events.RemovePlayerEvent;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.gameserver.model.world.World;

import java.util.Map;

public class WorldInstanceQueued {
    private final World world;

    public WorldInstanceQueued() {
        Injector injector = Guice.createInjector(new GeneralModule());
        this.world = injector.getInstance(World.class);
    }

    public void addPlayer(Player player) {
        //AddPlayerEvent event = new AddPlayerEvent(player);
        //world.addEvent(event);
        world.addPlayer(player);
    }

    public void removePlayer(Player player) {
        RemovePlayerEvent event = new RemovePlayerEvent(player.getPlayerId());
        world.addEvent(event);
    }

    public int getPlayersCount() {
        return world.getAllPlayersCount();
    }


    public void movePlayer(Player player, MoveDirections moveDirections) {
        MovePlayerEvent event = new MovePlayerEvent(player.getPlayerId(), moveDirections);
        world.addEvent(event);
    }

    public void movePlayerById(Integer player, MoveDirections moveDirections) {
        world.getPlayerById(player).move(moveDirections);
    }

    public Chunk getMap(int chunk_x, int chunk_y) {
        return world.getChunkByXY(chunk_x, chunk_y);
    }

    public Map<Integer, Player> getAllPlayers() {
        return world.getAllPlayers();
    }
}
