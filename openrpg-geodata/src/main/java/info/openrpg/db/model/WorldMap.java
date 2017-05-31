package info.openrpg.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "worlds")
public class WorldMap implements Serializable {

    @Column(name = "world_id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer world_id;

    @Column(name = "world_name")
    @Basic
    private String worldName;
    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = ChunkDB.class, orphanRemoval = true, mappedBy = "worldMap")
    private List<ChunkDB> chunksList;

    public WorldMap() {
    }

    public WorldMap(String worldName) {
        this.worldName = worldName;
    }

    public Integer getWorld_id() {
        return this.world_id;
    }

    public void setWorld_id(Integer world_id) {
        this.world_id = world_id;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public List<ChunkDB> getChunksList() {
        return this.chunksList;
    }

    public void setChunksList(List<ChunkDB> chunksList) {
        this.chunksList = chunksList;
    }

}
