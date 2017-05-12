import info.openrpg.gameserver.model.World;
import org.junit.Assert;
import org.junit.Test;

public class WorldCreateTest {

    @Test
    public void testWorldCreate() {
        World testWorld = new World();
        Assert.assertNotNull(testWorld);
    }

}
