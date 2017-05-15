package info.openrpg.database.models.hero;

import info.openrpg.database.models.telegram.Player;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hero")
@Data
public class Hero {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;
    @Enumerated(EnumType.STRING)
    private final HeroClass heroClass;
    @Enumerated(EnumType.STRING)
    private final Race race;
    @Column(name = "name", unique = true, nullable = false)
    private final String name;
    @ManyToOne
    @JoinColumn(
            name = "player_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "player_id_fkey")
    )
    private final Player playerId;

    @OneToOne
    @JoinColumn(
            name = "stats_id",
            referencedColumnName = "id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "hero_stats_id_fkey")
    )
    private HeroStats heroStats;
}
