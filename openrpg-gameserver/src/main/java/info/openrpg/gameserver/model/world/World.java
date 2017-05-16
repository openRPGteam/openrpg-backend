package info.openrpg.gameserver.model.world;

import info.openrpg.database.models.hero.Hero;

import java.util.Map;

public interface World {
    void addHero(Hero hero);

    void removeHero(Hero hero);

    Hero getHeroById(int playerId);

    Map<Integer, Hero> getAllHero();

    int getAllHeroCount();
}
