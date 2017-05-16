package info.openrpg.gameserver.enums;

public enum TerrainType {
    EARTH(1),
    DRY_EARTH(1),
    FAT_EARTH(1),
    GRASS(1),
    STONE(0),
    WATER(0),
    MUD(0),
    MOUNTAIN(0);

    private final int passable;

    TerrainType(int i) {
        passable = i;
    }

    public int getPassable() {
        return passable;
    }

    public boolean isPassable() {
        return getPassable() == 1;
    }
}
