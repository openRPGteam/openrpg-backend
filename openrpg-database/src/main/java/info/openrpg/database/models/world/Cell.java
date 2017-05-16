package info.openrpg.database.models.world;


import info.openrpg.database.models.hero.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @see <a href="https://github.com/openRPGteam/openrpg-backend/wiki/%D0%AF%D1%87%D0%B5%D0%B9%D0%BA%D0%B8">Cell desctiption</a>
 */
@Entity
@Table(
        name = "cell",
        uniqueConstraints = @UniqueConstraint(columnNames = "hero_id", name = "hero_id_uq")
)
@Data
@AllArgsConstructor
public class Cell {

    @EmbeddedId
    private final Location location;

    @Enumerated(EnumType.STRING)
    private final TerrainType terrainType;

    @OneToOne
    @JoinColumn(
            name = "hero_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "hero_fkey"))
    private final Hero hero;

    public Cell(Location location, TerrainType terrainType) {
        this(location, terrainType, null);
    }
}
