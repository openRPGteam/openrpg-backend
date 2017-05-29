package info.openrpg.db.model;

import javax.persistence.*;

@Entity
public class WorldMap {

    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
