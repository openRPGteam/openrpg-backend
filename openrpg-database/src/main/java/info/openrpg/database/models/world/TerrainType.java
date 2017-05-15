package info.openrpg.database.models.world;

/**
 * @see <a href="https://github.com/openRPGteam/openrpg-backend/wiki/%D0%9B%D0%B0%D0%BD%D0%B4%D1%88%D0%B0%D1%84%D1%82">Landscapes description</a>
 */
public enum TerrainType {
    //passable
    EARTH(true),
    SCORCHED_EARTH(true),
    BLACK_EARTH(true),
    GRASS(true),
    STONE(true),

    //not passable
    WATER(false),
    MUD(false),
    MOUNTAIN(false);

    private final boolean passable;

    TerrainType(boolean passable) {
        this.passable = passable;
    }

    public boolean isPassable() {
        return passable;
    }
}
