package Unities;

public class BattleUnit extends Unit {
    private int attack;

    public BattleUnit(int width, int height, String id, int hitPoints, int attack) {
        super(width, height, id, hitPoints);
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
