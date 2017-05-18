package info.openrpg.gameserver.model.actors;

import info.openrpg.gameserver.enums.GameClass;
import info.openrpg.gameserver.enums.Race;
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

    public Player(String name, Location curLocation, PlayerStats curPlayerStats, GameClass gameClass, Race playerRace, Integer playerId) {
        super(curLocation);
        this.name = name;
        this.curPlayerStats = curPlayerStats;
        this.gameClass = gameClass;
        this.playerRace = playerRace;
        this.playerId = playerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
}
