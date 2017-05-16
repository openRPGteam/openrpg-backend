package info.openrpg.gameserver.model.world;

import info.openrpg.database.models.hero.Hero;
import info.openrpg.database.models.world.Cell;

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
}
