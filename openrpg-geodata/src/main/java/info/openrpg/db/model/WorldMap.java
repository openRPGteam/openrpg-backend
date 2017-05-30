package info.openrpg.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "worlds")
public class WorldMap implements Serializable {
    private static final long serialVersionUID = 4979246814108008677L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "world_id", updatable = false, nullable = false)
    private Integer world_id;
    @Column(name = "world_name")
    private String worldName;
    @OneToMany(mappedBy = "worldMap")
    private List<ChunkDB> chunksList;

    public WorldMap() {
    }

    public WorldMap(String worldName) {
        this.worldName = worldName;
    }

    public Integer getWorld_id() {
        return world_id;
    }

    public void setWorld_id(Integer world_id) {
        this.world_id = world_id;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public List<ChunkDB> getChunksList() {
        return chunksList;
    }

    public void setChunksList(List<ChunkDB> chunksList) {
        this.chunksList = chunksList;
    }

    @Override
    public String toString() {
        return "WorldMap{" +
                "world_id=" + world_id +
                ", worldName='" + worldName + '\'' +
                ", chunksList=" + chunksList +
                '}';
    }
}
