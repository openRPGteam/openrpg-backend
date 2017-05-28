package info.openrpg.gameserver.inject;

import info.openrpg.gameserver.model.actors.GameObject;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.world.Chunk;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IWorld {
    boolean addPlayer(Player player);

    void removePlayer(Player player);

    Player getPlayerById(int playerId);

    Map<Integer, Player> getAllPlayers();

    int getAllPlayersCount();

    void addGameObject(GameObject gameObject);

    void removeGameObject(GameObject gameObject);

    GameObject getGameObject(int objectId);

    Map<Integer, GameObject> getAllGameObjects();

    int getAllGameObjectsCount();

    int getChunkSize();

    int getMapSizeX();

    int getMapSizeY();

    int getLoopDelay();

    Chunk[][] getWorldChunks();

    Chunk getChunkByXY(int chunk_x, int chunk_y);

    List<Player> getPlayersByChunkXY(int chunk_x, int chunk_y);

    Optional<Chunk> getChunkByPlayerId(int id);

    void putPlayerToXYChunk(int playerId, int x, int y);

    void removePlayerFromXYChunk(int playerId, int x, int y);
}
