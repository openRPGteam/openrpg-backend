package info.openrpg.gameserver.model.world;

public class Location {
    private int x;
    private int y;
    private int instanceId;
    private int chunk_x;
    private int chunk_y;

    public Location(int x, int y, int chunk_x, int chunk_y) {
        this.x = x;
        this.y = y;
        this.chunk_x = chunk_x;
        this.chunk_y = chunk_y;
    }

    public int getChunk_x() {
        return chunk_x;
    }

    public void setChunk_x(int chunk_x) {
        this.chunk_x = chunk_x;
    }

    public int getChunk_y() {
        return chunk_y;
    }

    public void setChunk_y(int chunk_y) {
        this.chunk_y = chunk_y;
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

    public void setChunkXY(int x, int y) {
        this.chunk_x = x;
        this.chunk_y = y;
    }
    public void setLocation(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
        this.chunk_x = loc.getChunk_x();
        this.chunk_y = loc.getChunk_y();
    }
}
