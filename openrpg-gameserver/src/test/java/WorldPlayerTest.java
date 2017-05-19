import info.openrpg.gameserver.WorldInstanceQueued;
import info.openrpg.gameserver.enums.GameClass;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.enums.Race;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.actors.PlayerStats;
import info.openrpg.gameserver.model.world.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorldPlayerTest {
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

    // @Test
    // public void printZeroChunk(){
    //     System.out.println(world.getMap(0,0).printChunk());
    //}
    @Test
    public void addPlayersToMap() {
        world.addPlayer(player1);
        world.addPlayer(player2);
        world.addPlayer(player3);
        world.addPlayer(player1);
        Assert.assertEquals(3, world.getPlayersCount());
        world.removePlayer(player3);
        Assert.assertEquals(2, world.getPlayersCount());
        world.addPlayer(player3);
        Assert.assertEquals(3, world.getPlayersCount());
    }

    @Test
    public void movePlayerEast() throws InterruptedException {
        //движение на восток
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.EAST); //1,2,1,1
        Assert.assertArrayEquals(new int[]{1, 2, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.EAST); //1,3,1,1
        Assert.assertArrayEquals(new int[]{1, 3, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerWest() throws InterruptedException {
        //движение на запад
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.WEST); //1,0,1,1
        Assert.assertArrayEquals(new int[]{1, 0, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.WEST); //1,9,1,0
        Assert.assertArrayEquals(new int[]{1, 9, 1, 0}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerNorth() throws InterruptedException {
        //движение на север
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.NORTH); //0,1,1,1
        Assert.assertArrayEquals(new int[]{0, 1, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.NORTH); //9,1,0,1
        Assert.assertArrayEquals(new int[]{9, 1, 0, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerSouth() throws InterruptedException {
        //движение на юг
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.SOUTH); //2,1,1,1
        Assert.assertArrayEquals(new int[]{2, 1, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.SOUTH); //3,1,1,1
        Assert.assertArrayEquals(new int[]{3, 1, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerNorthEast() throws InterruptedException {
        //движение на северо-восток
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.NORTHEAST); //0,2,1,1
        Assert.assertArrayEquals(new int[]{0, 2, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.NORTHEAST); //9,3,0,1
        Assert.assertArrayEquals(new int[]{9, 3, 0, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerNorthWest() throws InterruptedException {
        //движение на северо-запад
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.NORTHWEST); //0,0,1,1
        Assert.assertArrayEquals(new int[]{0, 0, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.NORTHWEST); //9,9,0,0
        Assert.assertArrayEquals(new int[]{9, 9, 0, 0}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerSouthWest() throws InterruptedException {
        //движение на юго-запад
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.SOUTHWEST); //2,0,1,1
        Assert.assertArrayEquals(new int[]{2, 0, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.SOUTHWEST); //3,9,1,0
        Assert.assertArrayEquals(new int[]{3, 9, 1, 0}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerSouthEast() throws InterruptedException {
        //движение на юго-восток
        world.addPlayer(player1); //1,1,1,1
        world.movePlayer(player1, MoveDirections.SOUTHEAST); //2,2,1,1
        Assert.assertArrayEquals(new int[]{2, 2, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
        Thread.sleep(7000);
        world.movePlayer(player1, MoveDirections.SOUTHEAST); //3,3,1,1
        Assert.assertArrayEquals(new int[]{3, 3, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void moveTryBellowZero() {
        //попытка выйти за пределы карты ниже нуля
        world.addPlayer(player2); //0,0,0,0
        world.movePlayer(player2, MoveDirections.NORTH);
        world.movePlayer(player2, MoveDirections.WEST);
        Assert.assertArrayEquals(new int[]{0, 0, 0, 0}, world.getAllPlayers().get(player2.getPlayerId()).getCurLocation().asArray());

    }

    @Test
    public void moveTryAboveMax() {
        //попытка выйти за пределы карты выше лимита
        world.addPlayer(player3); //9,9,9,9
        world.movePlayer(player3, MoveDirections.SOUTH);
        world.movePlayer(player3, MoveDirections.EAST);
        Assert.assertArrayEquals(new int[]{9, 9, 9, 9}, world.getAllPlayers().get(player3.getPlayerId()).getCurLocation().asArray());
    }

}
