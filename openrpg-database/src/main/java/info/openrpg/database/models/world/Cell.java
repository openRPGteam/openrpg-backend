package info.openrpg.database.models.world;


import info.openrpg.database.models.hero.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @see <a href="https://github.com/openRPGteam/openrpg-backend/wiki/%D0%AF%D1%87%D0%B5%D0%B9%D0%BA%D0%B8">Cell desctiption</a>
 */
@Entity
@Table(name = "cell")
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
            unique = true,
            foreignKey = @ForeignKey(name = "hero_fkey"))
    private final Hero hero;
}
