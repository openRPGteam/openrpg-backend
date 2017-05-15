import info.openrpg.gameserver.model.world.BasicWorld;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BasicWorldCreateTest {
    private BasicWorld testBasicWorld;

    @BeforeClass
    public void init() {
        this.testBasicWorld = new BasicWorld();
    }

    @Test
    public void testrandomchunk() {
    }

    @Test
    public void testinitchunk() {
    }
}

