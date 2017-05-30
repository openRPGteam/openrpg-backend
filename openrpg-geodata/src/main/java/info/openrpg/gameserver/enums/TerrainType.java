package info.openrpg.gameserver.enums;

public enum TerrainType {
    WATER(0),
    GRASS(1),
    SAND(1),
    MOUNTAIN(0),
    SNOW(1),
    MUD(1),
    WHEAT(1),
    FOREST(1),
    ASHES(1);

    private static final TerrainType[] ALL = values();
    private final int passable;

    TerrainType(int i) {
        passable = i;
    }

    public static TerrainType valueOf(int index) {
        return ALL[index];
    }

    public int getPassable() {
        return passable;
    }

    public boolean isPassable() {
        return getPassable() == 1;
    }
}
