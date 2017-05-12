package info.openrpg.gameserver.model;

import java.io.Serializable;
import java.util.logging.Logger;


public class Player implements Serializable {
    public static final Logger LOG = Logger.getLogger(Player.class.getName());
    private static final long serialVersionUID = 1L;
    private final GameClass gameClass;
    private final Race playerRace;
    private String name;
    private Location curLocation;
    private PlayerStats curPlayerStats;

    public Player(String name, Location curLocation, PlayerStats curPlayerStats, GameClass gameClass, Race playerRace) {
        this.name = name;
        this.curLocation = curLocation;
        this.curPlayerStats = curPlayerStats;
        this.gameClass = gameClass;
        this.playerRace = playerRace;
    }
}
