import info.openrpg.gameserver.WorldInstanceQueued;
import info.openrpg.gameserver.enums.GameClass;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.enums.Race;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.actors.PlayerStats;
import info.openrpg.gameserver.model.world.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class QueueTest {
    private WorldInstanceQueued world;
    private Player player1;
    private Player player2;
    private Player player3;

    @Before
    public void init() {
        world = new WorldInstanceQueued();
        player1 = new Player("lol",
                new Location(1, 1, 1, 1),
                new PlayerStats(1, 1),
                GameClass.KNIGHT, Race.HUMAN, 1);
        player2 = new Player("kek",
                new Location(0, 0, 0, 0),
                new PlayerStats(1, 1),
                GameClass.ARCHER, Race.HUMAN, 2);
        player3 = new Player("cheburek",
                new Location(9, 9, 9, 9),
                new PlayerStats(1, 1),
                GameClass.KNIGHT, Race.HUMAN, 3);
    }

    @Test
    public void RunTest() throws InterruptedException {
        world.addPlayer(player1);
        world.addPlayer(player1);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.addPlayer(player2);
        world.getPlayersCount();
        for (Map.Entry<Integer, Player> p : world.getAllPlayers().entrySet()) {
            System.out.println(p.getValue().getName() + " - " + p.getValue().getCurLocation().toString());
        }
        Thread.sleep(10000);
        for (Map.Entry<Integer, Player> p : world.getAllPlayers().entrySet()) {
            System.out.println(p.getValue().getName() + " - " + p.getValue().getCurLocation().toString());
        }
        world.addPlayer(player3);
        world.movePlayer(player1, MoveDirections.EAST);
        Thread.sleep(10000);
        world.getPlayersCount();
        for (Map.Entry<Integer, Player> p : world.getAllPlayers().entrySet()) {
            System.out.println(p.getValue().getName() + " - " + p.getValue().getCurLocation().toString());
        }

    }
}
