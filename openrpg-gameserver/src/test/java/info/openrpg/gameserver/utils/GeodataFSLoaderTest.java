package info.openrpg.gameserver.utils;

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
    public void getWorldYSize() throws Exception {
        assertEquals(34, geodataFSLoader.getWorldYSize());
    }

}