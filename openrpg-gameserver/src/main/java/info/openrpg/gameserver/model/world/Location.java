package info.openrpg.gameserver.model.world;

public class Location {
    private int x;
    private int y;
    private int instanceId;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
    }
}
