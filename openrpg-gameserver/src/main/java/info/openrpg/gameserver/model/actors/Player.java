package info.openrpg.gameserver.model.actors;

import info.openrpg.gameserver.enums.GameClass;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.enums.Race;
import info.openrpg.gameserver.inject.IWorld;
import info.openrpg.gameserver.model.behavior.PlayerCollision;
import info.openrpg.gameserver.model.world.Location;

import java.io.Serializable;
import java.util.logging.Logger;


public class Player extends AbstractActor implements Serializable {
    public static final Logger LOG = Logger.getLogger(Player.class.getName());
    private static final long serialVersionUID = 1L;
    private final GameClass gameClass;
    private final Race playerRace;
    private final String name;
    private final Integer playerId;
    private PlayerStats curPlayerStats;

    public Player(String name, Location curLocation, IWorld currentWorld, PlayerStats curPlayerStats, GameClass gameClass, Race playerRace, Integer playerId) {
        super(curLocation, currentWorld);
        this.name = name;
        this.curPlayerStats = curPlayerStats;
        this.gameClass = gameClass;
        this.playerRace = playerRace;
        this.playerId = playerId;
    }

    @Override
    public void move(MoveDirections direction) {
        //TODO оче плохое решение. нужно будет отрефакторить
        PlayerCollision pc = new PlayerCollision(this.currentWorld);
        switch (direction) {
            case NORTH: {
                pc.CheckTerrain();
                pc.CheckActor();
            }
        }

    }

    public Integer getPlayerId() {
        return playerId;
    }
}
