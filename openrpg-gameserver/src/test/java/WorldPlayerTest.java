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
    public static final int delay = 1;
    private WorldInstanceQueued world;
    private Player player1;
    private Player player2;
    private Player player3;
    private int queueDelay;
    private int chunkSize;
    private int mapSize;

    @Before
    public void init() {
        world = new WorldInstanceQueued();
        this.queueDelay = (world.getWorldDelay() + delay) * 1000;
        this.chunkSize = world.getChunkSize();
        this.mapSize = world.getMapSize();
        player1 = new Player("lol",
                new Location(1, 1, 1, 1),
                new PlayerStats(1, 1),
                GameClass.KNIGHT, Race.HUMAN, 1);
        player2 = new Player("kek",
                new Location(0, 0, 0, 0),
                new PlayerStats(1, 1),
                GameClass.ARCHER, Race.HUMAN, 2);
        player3 = new Player("cheburek",
                new Location(chunkSize - 1, chunkSize - 1, mapSize - 1, mapSize - 1),
                new PlayerStats(1, 1),
                GameClass.KNIGHT, Race.HUMAN, 3);
    }

    // @Test
    // public void printZeroChunk(){
    //     System.out.println(world.getMap(0,0).printChunk());
    //}
    @Test
    public void addPlayersToMap() throws InterruptedException {
        world.addPlayer(player1);
        world.addPlayer(player2);
        world.addPlayer(player3);
        world.addPlayer(player1);
        Thread.sleep(queueDelay);
        Assert.assertEquals(3, world.getPlayersCount());
        world.removePlayer(player3);
        Thread.sleep(queueDelay);
        Assert.assertEquals(2, world.getPlayersCount());
        world.addPlayer(player3);
        Thread.sleep(queueDelay);
        Assert.assertEquals(3, world.getPlayersCount());
    }

    @Test
    public void movePlayerEast() throws InterruptedException {
        //движение на восток
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.EAST); //1,2,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{1, 2, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.EAST); //1,3,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{1, 3, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerWest() throws InterruptedException {
        //движение на запад
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.WEST); //1,0,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{1, 0, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.WEST); //1,8,1,0
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{1, chunkSize - 1, 1, 0}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerNorth() throws InterruptedException {
        //движение на север
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.NORTH); //0,1,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{0, 1, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.NORTH); //8,1,0,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{chunkSize - 1, 1, 0, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerSouth() throws InterruptedException {
        //движение на юг
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.SOUTH); //2,1,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{2, 1, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.SOUTH); //3,1,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{3, 1, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerNorthEast() throws InterruptedException {
        //движение на северо-восток
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.NORTHEAST); //0,2,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{0, 2, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.NORTHEAST); //8,3,0,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{chunkSize - 1, 3, 0, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerNorthWest() throws InterruptedException {
        //движение на северо-запад
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.NORTHWEST); //0,0,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{0, 0, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.NORTHWEST); //8,8,0,0
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{chunkSize - 1, chunkSize - 1, 0, 0}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerSouthWest() throws InterruptedException {
        //движение на юго-запад
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.SOUTHWEST); //2,0,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{2, 0, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.SOUTHWEST); //3,8,1,0
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{3, chunkSize - 1, 1, 0}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void movePlayerSouthEast() throws InterruptedException {
        //движение на юго-восток
        world.addPlayer(player1); //1,1,1,1
        Thread.sleep(queueDelay);

        world.movePlayer(player1, MoveDirections.SOUTHEAST); //2,2,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{2, 2, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());

        world.movePlayer(player1, MoveDirections.SOUTHEAST); //3,3,1,1
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{3, 3, 1, 1}, world.getAllPlayers().get(player1.getPlayerId()).getCurLocation().asArray());
    }

    @Test
    public void moveTryBellowZero() throws InterruptedException {
        //попытка выйти за пределы карты ниже нуля
        world.addPlayer(player2); //0,0,0,0
        Thread.sleep(queueDelay);

        world.movePlayer(player2, MoveDirections.NORTH);
        world.movePlayer(player2, MoveDirections.WEST);
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{0, 0, 0, 0}, world.getAllPlayers().get(player2.getPlayerId()).getCurLocation().asArray());

    }

    @Test
    public void moveTryAboveMax() throws InterruptedException {
        //попытка выйти за пределы карты выше лимита
        world.addPlayer(player3); //8,8,8,8
        Thread.sleep(queueDelay);

        world.movePlayer(player3, MoveDirections.SOUTH);
        world.movePlayer(player3, MoveDirections.EAST);
        Thread.sleep(queueDelay);
        Assert.assertArrayEquals(new int[]{chunkSize - 1, chunkSize - 1, mapSize - 1, mapSize - 1}, world.getAllPlayers().get(player3.getPlayerId()).getCurLocation().asArray());
    }

}
