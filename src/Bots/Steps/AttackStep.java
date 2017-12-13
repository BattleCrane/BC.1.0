package Bots.Steps;

import BattleFields.Point;

public class AttackStep implements Step {
    private Point startPoint;
    private Point shortPoint;

    public AttackStep(Point startPoint, Point shortPoint) {
        this.startPoint = startPoint;
        this.shortPoint = shortPoint;
    }

    @Override
    public void makeStep() {

    }
}
