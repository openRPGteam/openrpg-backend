package info.openrpg.gameserver.utils;

import info.openrpg.gameserver.model.world.Chunk;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GeodataFSLoaderTest {
    private GeodataFSLoader geodataFSLoader;

    @Before
    public void init() throws IOException {
        geodataFSLoader = new GeodataFSLoader();
    }

    @Test
    public void loadFullMap() throws Exception {
    }

    @Test
    public void getWorldXSize() throws Exception {
        assertEquals(45, geodataFSLoader.getWorldXSize());
    }

    @Test
    public void getWorldXSizeCells() throws Exception {
        assertEquals(409, geodataFSLoader.getWorldXSizeCells());
    }

    @Test
    public void getWorldYSize() throws Exception {
        assertEquals(34, geodataFSLoader.getWorldYSize());
    }

    @Test
    public void getWorldYSizeCells() throws Exception {
        assertEquals(307, geodataFSLoader.getWorldYSizeCells());
    }

    @Test
    public void getChunk() throws Exception {
        Chunk chunk = geodataFSLoader.getChunk(geodataFSLoader.getWorldXSize() / 2, geodataFSLoader.getWorldYSize() / 2);
        String cc = chunk.printLiteralChunk();
        System.out.println(cc);
    }
}