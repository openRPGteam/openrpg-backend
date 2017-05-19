package info.openrpg.gameserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.inject.GeneralModule;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.gameserver.model.world.Location;
import info.openrpg.gameserver.model.world.World;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorldInstance {
    private final World world;

    public WorldInstance() {
        Injector injector = Guice.createInjector(new GeneralModule());
        this.world = injector.getInstance(World.class);
    }

    public boolean addPlayer(Player player) {
        return world.addPlayer(player);
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

    public Optional<Chunk> getChunkByPlayerId(int id) {
        return world.getChunkByPlayerId(id);
    }

    public List<Player> getPlayersInSameChunkByPlayerId(int id) {
        Location curLocation = world.getPlayerById(id)
                .getCurLocation();

        return world.getAllPlayers()
                .values()
                .stream()
                .filter(player -> player.getCurLocation().isSameChunkLocation(curLocation))
                .collect(Collectors.toList());
    }
}
