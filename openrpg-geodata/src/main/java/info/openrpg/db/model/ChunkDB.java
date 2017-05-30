package info.openrpg.db.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chunks")
public class ChunkDB implements Serializable {
    private static final long serialVersionUID = -5790106304101684202L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chunk_id", updatable = false, nullable = false)
    private Integer chunk_id;


    @ManyToOne()
    @JoinColumn(name = "world_id")
    private WorldMap worldMap;

    private int chunkX;
    private int chunkY;

    public ChunkDB() {
    }

    public ChunkDB(int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public Integer getChunk_id() {
        return chunk_id;
    }

    public void setChunk_id(Integer chunk_id) {
        this.chunk_id = chunk_id;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public int getChunkY() {
        return chunkY;
    }

    public void setChunkY(int chunkY) {
        this.chunkY = chunkY;
    }

    @Override
    public String toString() {
        return "ChunkDB{" +
                "chunk_id=" + chunk_id +
                ", worldMap=" + worldMap +
                ", chunkX=" + chunkX +
                ", chunkY=" + chunkY +
                '}';
    }
}
