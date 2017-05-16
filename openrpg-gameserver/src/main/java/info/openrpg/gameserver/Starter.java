package info.openrpg.gameserver;

import com.google.common.base.MoreObjects;
import info.openrpg.database.models.hero.Hero;
import info.openrpg.database.models.hero.HeroStats;
import info.openrpg.database.models.telegram.Message;
import info.openrpg.database.models.telegram.Player;
import info.openrpg.database.models.world.Cell;
import info.openrpg.database.models.world.Location;
import info.openrpg.database.models.world.TerrainType;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(Starter.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SessionFactory sessionFactory = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Message.class)
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Cell.class)
                .addAnnotatedClass(Hero.class)
                .addAnnotatedClass(HeroStats.class)
                .buildSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Cell cell = new Cell(new Location(i, j), TerrainType.EARTH);
                entityManager.persist(cell);
            }
        }
        entityManager.getTransaction().commit();
    }
}
