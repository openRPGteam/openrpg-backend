import info.openrpg.gameserver.enums.TerrainType;
import org.junit.Assert;
import org.junit.Test;

public class TerrainTypeTest {
    @Test
    public void testTerrainType() {
        for (TerrainType tt : TerrainType.values()) {
            System.out.println("Type " + tt.name() + " passable is" + tt.isPassable());
            //Assert.assertTrue("Type "+tt.name()+" passable",tt.isPassable());
        }
    }
}
