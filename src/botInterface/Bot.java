package botInterface;

import game.battleFields.BattleManager;
import polytech.steps.Step;

import java.util.List;

public interface Bot {

    int getCountOfStep();

    void setCountOfStep(int countOfStep);

    List<Step> loadSteps(BattleManager battleManager);
}
