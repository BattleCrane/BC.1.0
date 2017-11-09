package PolyBot;

import BattleFields.Point;
import Bots.Bot;
import Controllers.ControllerMatchMaking;
import Players.Player;

import java.util.List;

public class PolytechBot implements Bot{
    private int step;


    @Override
    public void run(ControllerMatchMaking controllerMatchMaking) {

    }

    @Override
    public boolean isWinnerTurn(Player player) {
        return false;
    }

    @Override
    public List<Point> probeSafePoints(Player player) {
        return null;
    }

    @Override
    public int probeMaxStep(Player player) {
        return 0;
    }

    @Override
    public int isGeneratorUnderDangerous(Player player) {
        return 0;
    }

    @Override
    public Point probeAttack() {
        return null;
    }

    @Override
    public List<Integer> probeDangerousFromBonuses(Player player) {
        return null;
    }

    @Override
    public int getStep() {
        return 0;
    }

    @Override
    public void setStep(int step) {
        this.step = step;
    }
}
