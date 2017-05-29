package info.openrpg.ejb.model.actors;

import info.openrpg.ejb.enums.GameClass;
import info.openrpg.ejb.enums.Race;
import info.openrpg.ejb.model.world.Location;

import java.util.logging.Logger;


public class Player extends AbstractActor {
    public static final Logger LOG = Logger.getLogger(Player.class.getName());
    private final GameClass gameClass;
    private final Race playerRace;
    private final String name;
    private PlayerStats curPlayerStats;

    public Player(String name, Location curLocation, PlayerStats curPlayerStats, GameClass gameClass, Race playerRace, Integer playerId) {
        super(curLocation, playerId);
        this.name = name;
        this.curPlayerStats = curPlayerStats;
        this.gameClass = gameClass;
        this.playerRace = playerRace;

    }

    public Integer getPlayerId() {
        return getActorId();
    }

    public String getName() {
        return name;
    }
}
