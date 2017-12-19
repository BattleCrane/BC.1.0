package Bots;

import BattleFields.BattleManager;
import PolyBot.Steps.Step;

import java.util.List;

public interface Bot {

    int getCountOfStep();

    void setCountOfStep(int countOfStep);

    List<Step> loadSteps(BattleManager battleManager);
}
