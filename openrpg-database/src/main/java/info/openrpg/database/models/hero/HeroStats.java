package info.openrpg.database.models.hero;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hero_stats")
@Data
public class HeroStats {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;
    @Column(name = "strength")
    private final int strength;
    @Column(name = "intelligence")
    private final int intelligence;
}
