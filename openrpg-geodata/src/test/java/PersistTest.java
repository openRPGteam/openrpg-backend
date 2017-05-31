import info.openrpg.db.controllers.ChunkDBJpaController;
import info.openrpg.db.controllers.WorldMapJpaController;
import info.openrpg.db.model.ChunkDB;
import info.openrpg.db.model.WorldMap;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistTest {
    public static final String PUNAME = "TestPU";
    private EntityManagerFactory factory;

    @Before
    public void init() {
        this.factory = Persistence.createEntityManagerFactory(PUNAME);
    }

    @Test
    public void addOneToMany() throws Exception {
        WorldMapJpaController wmjc = new WorldMapJpaController(factory);
        ChunkDBJpaController cdjc = new ChunkDBJpaController(factory);
        WorldMap mainWorld = new WorldMap("mainWorld");
        wmjc.create(mainWorld);
        ChunkDB chunk1 = new ChunkDB(1, 1);
        chunk1.setWorldMap(mainWorld);
        cdjc.create(chunk1);
        ChunkDB chunk2 = new ChunkDB(1, 1);
        chunk2.setWorldMap(mainWorld);
        cdjc.create(chunk2);
        //mainWorld.getChunksList().add(chunk1);
        //mainWorld.getChunksList().add(chunk2);
        //wmjc.edit(mainWorld);
    }
}
