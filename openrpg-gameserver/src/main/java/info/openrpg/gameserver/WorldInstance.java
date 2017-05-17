package info.openrpg.gameserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.inject.GeneralModule;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.gameserver.model.world.World;

import java.util.Map;

public class WorldInstance {
    private final World world;

    public WorldInstance() {
        Injector injector = Guice.createInjector(new GeneralModule());
        this.world = injector.getInstance(World.class);
    }

    public void addPlayer(Player player) {
        world.addPlayer(player);
    }

    public void removePlayer(Player player) {
        world.removePlayer(player);
    }

    public int getPlayersCount() {
        return world.getAllPlayersCount();
    }



    public void movePlayer(Player player, MoveDirections moveDirections) {
        world.getPlayerById(player.getPlayerId()).move(moveDirections);
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