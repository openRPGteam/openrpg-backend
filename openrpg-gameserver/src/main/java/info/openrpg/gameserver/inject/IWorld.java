package info.openrpg.gameserver.inject;

import info.openrpg.gameserver.model.actors.GameObject;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.world.Chunk;

import java.util.Map;

public interface IWorld {
    void addPlayer(Player player);
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

    Chunk[][] getWorldChunks();

    Chunk getChunkByXY(int chunk_x, int chunk_y);
}