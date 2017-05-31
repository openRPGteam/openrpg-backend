package info.openrpg.db.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chunks")
public class ChunkDB implements Serializable {

    @Column(name = "chunk_id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chunk_id;

    @Basic
    private int chunkX;

    @Basic
    private int chunkY;

    @ManyToOne(targetEntity = WorldMap.class)
    @JoinColumn(name = "world_id")
    private WorldMap worldMap;

    @Version
    private long attribute;

    public ChunkDB(int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public ChunkDB() {
    }

    public Integer getChunk_id() {
        return this.chunk_id;
    }

    public void setChunk_id(Integer chunk_id) {
        this.chunk_id = chunk_id;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkY() {
        return this.chunkY;
    }

    public void setChunkY(int chunkY) {
        this.chunkY = chunkY;
    }

    public WorldMap getWorldMap() {
        return this.worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public long getAttribute() {
        return this.attribute;
    }

    public void setAttribute(long attribute) {
        this.attribute = attribute;
    }

}
