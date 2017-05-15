package info.openrpg.gameserver.model.world;

import info.openrpg.database.models.hero.Hero;
import info.openrpg.database.models.world.Cell;
import info.openrpg.gameserver.model.actors.GameObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class BasicWorld implements World {
    public static final Logger LOG = Logger.getLogger(BasicWorld.class.getName());
    private final static int CELLS_PER_AXLE = 100;
    private final Cell[] cells;
    //хешмап для игроков
    private final Map<Integer, Hero> players = new ConcurrentHashMap<>();
    //хешмап для прочих динамических обьектов
    private final Map<Integer, GameObject> globalObjectsMap = new ConcurrentHashMap<>();

    public BasicWorld() {
        cells = new Cell[CELLS_PER_AXLE * CELLS_PER_AXLE];
    }

    public void initchunks() {

    }

    @Override
    public void addHero(Hero hero) {
        if (players.containsKey(hero.getPlayerId())) {
            LOG.warning("Hero " + hero.toString() + " already in playersmap");
            return;
        }
        players.put(hero.getId(), hero);
    }

    @Override
    public void removeHero(Hero hero) {
        players.remove(hero);
    }

    @Override
    public Hero getHeroById(int playerId) {
        return players.get(playerId);
    }

    @Override
    public Map<Integer, Hero> getAllHero() {
        return players;
    }

    @Override
    public int getAllHeroCount() {
        return players.size();
    }

    @Override
    public void addGameObject(GameObject gameObject) {
        if (globalObjectsMap.containsKey(gameObject.getObjectId())) {
            LOG.warning("GameObject " + gameObject.toString() + " already in gameobjectsmap");
            return;
        }
        globalObjectsMap.put(gameObject.getObjectId(), gameObject);
    }

    @Override
    public void removeGameObject(GameObject gameObject) {
        globalObjectsMap.remove(gameObject.getObjectId());
    }

    @Override
    public GameObject getGameObject(int objectId) {
        return globalObjectsMap.get(objectId);
    }

    @Override
    public Map<Integer, GameObject> getAllGameObjects() {
        return globalObjectsMap;
    }

    @Override
    public int getAllGameObjectsCount() {
        return globalObjectsMap.size();
    }
}
