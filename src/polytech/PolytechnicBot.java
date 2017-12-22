package polytech;

import game.battleFields.BattleManager;
import botInterface.Bot;
import botInterface.priority.PriorityUnit;
import polytech.polyCombinations.PolyCombinator;
import polytech.steps.Step;
import game.controllers.ControllerMatchMaking;
import polytech.steps.UnityStep;

import java.util.ArrayList;
import java.util.List;

public class PolytechnicBot implements Bot {
    private final ControllerMatchMaking controllerMatchMaking;
    private final PolyCombinator combinator;

    public static int step = 0;

    //Конструктор:
    public PolytechnicBot(ControllerMatchMaking controllerMatchMaking) {
        this.controllerMatchMaking = controllerMatchMaking;
        combinator = new PolyCombinator(controllerMatchMaking);
    }

    @Override
    public int getCountOfStep() {
        return 0;
    }

    @Override
    public void setCountOfStep(int countOfStep) {

    }

    @Override
    public List<Step> loadSteps(BattleManager battleManager) {
        List<Step> steps = new ArrayList<>();
        for (PriorityUnit p: combinator.chooseDevelopment().getUnits()){
            steps.add(new UnityStep(battleManager, p));
        }
        steps.addAll(combinator.chooseAttacks());
        System.out.println("!");
        return steps;
    }
}
