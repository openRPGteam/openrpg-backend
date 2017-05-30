import info.openrpg.db.model.ChunkDB;
import info.openrpg.db.model.WorldMap;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class PersistTest {
    public static final String PUNAME = "TestPU";
    private EntityManagerFactory factory;

    @Before
    public void init() {
        this.factory = Persistence.createEntityManagerFactory(PUNAME);
    }

    @Test
    public void addOneToMany() {
        EntityManager em = factory.createEntityManager();
        WorldMap mainWorld = new WorldMap("mainWorld");
        em.getTransaction().begin();
        em.persist(mainWorld);
        ChunkDB chunk1 = new ChunkDB(1, 1);
        ChunkDB chunk2 = new ChunkDB(1, 1);
        List<ChunkDB> chunks = new ArrayList<>();
        chunks.add(chunk1);
        chunks.add(chunk2);
        mainWorld.setChunksList(chunks);
        em.merge(mainWorld);
        em.getTransaction().commit();
        //chunk1.setWorldMap(mainWorld);
        //chunk2.setWorldMap(mainWorld);
        em.close();
    }
}
