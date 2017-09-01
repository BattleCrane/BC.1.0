package Unities;

public class Unit extends Unity {
    private int hitPoints;

    public Unit(int width, int height, String id, int hitPoints) {
        super(width, height, id);
        this.hitPoints = hitPoints;
    }

    public Unit() {

    }

    public Boolean isDestroyed(){
        return this.hitPoints == 0;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

}