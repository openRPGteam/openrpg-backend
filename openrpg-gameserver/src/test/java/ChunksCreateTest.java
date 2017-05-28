import info.openrpg.gameserver.enums.TerrainType;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.gameserver.model.world.World;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ChunksCreateTest {
    private World testWorld;

    @Before
    public void init() throws IOException {
        this.testWorld = new World();
    }

    @Test
    public void testWorldCreate() {
        Assert.assertNotNull(testWorld);
    }

    @Test
    public void testRandomChunk() {
        Chunk newchunk = testWorld.randomchunk(TerrainType.GRASS);
        Assert.assertNotNull("Пустой элемент в начале первой строки", newchunk.getChunkmap()[0][0]);
        Assert.assertNotNull("Пустой элемент в конце первой строки", newchunk.getChunkmap()[0][testWorld.CHUNK_SIZE - 1]);
        Assert.assertNotNull("Пустой элемент в начале последней строки", newchunk.getChunkmap()[testWorld.CHUNK_SIZE - 1][0]);
        Assert.assertNotNull("Пустой элемент в конце последней строки", newchunk.getChunkmap()[0][testWorld.CHUNK_SIZE - 1]);
    }

    @Test
    public void testInitChunk() {
        Assert.assertEquals("Размер глобал мапы не очень", testWorld.getMapSizeX(), testWorld.getWorldChunks().length);
        Assert.assertEquals("Размер глобал мапы не очень", testWorld.getMapSizeX(), testWorld.getWorldChunks()[0].length);

    }

}

