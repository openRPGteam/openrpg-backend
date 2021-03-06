package info.openrpg.gameserver.model.actors;

public class PlayerStats {
    private int strength;
    private int intelligence;

    public PlayerStats(int strength, int intelligence) {
        this.strength = strength;
        this.intelligence = intelligence;
    }

    public int getSTR() {
        return strength;
    }

    public void setSTR(int strength) {
        this.strength = strength;
    }

    public int getINT() {
        return intelligence;
    }

    public void setINT(int intelligence) {
        this.intelligence = intelligence;
    }
}
