package info.openrpg.gameserver.model.world;

import info.openrpg.database.models.hero.Hero;
import info.openrpg.gameserver.model.actors.GameObject;

import java.util.Map;

public interface World {
    void addHero(Hero hero);
    void removeHero(Hero hero);

    Hero getHeroById(int playerId);

    Map<Integer, Hero> getAllHero();

    int getAllHeroCount();

    void addGameObject(GameObject gameObject);

    void removeGameObject(GameObject gameObject);

    GameObject getGameObject(int objectId);

    Map<Integer, GameObject> getAllGameObjects();

    int getAllGameObjectsCount();
}
