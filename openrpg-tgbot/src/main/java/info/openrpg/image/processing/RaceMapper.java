package info.openrpg.image.processing;

import info.openrpg.gameserver.enums.Race;

public class RaceMapper {

    public static final String HUMAN = "DISABLED";

    public static String mapRace(Race race) {
        switch (race) {
            case HUMAN:
                return HUMAN;
        }
        return "DISABLED";
    }
}
