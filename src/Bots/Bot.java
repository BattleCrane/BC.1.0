package Bots;

import BattleFields.Point;
import Bonuses.ControllerBonusesCollection;
import Controllers.ControllerMatchMaking;
import Players.Player;

import java.util.List;

public interface Bot {
    void run(ControllerMatchMaking controllerMatchMaking);

    boolean isWinnerTurn(Player player);

    List<Point> probeSafePoints(Player player);

    int probeMaxStep(Player player);

    int isGeneratorUnderDangerous(Player player);

    Point probeAttack();

    List<Integer> probeDangerousFromBonuses(Player player);

    int getStep();

    void setStep(int step);


}
