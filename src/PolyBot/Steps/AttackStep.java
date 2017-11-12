package PolyBot.Steps;

import BattleFields.BattleManager;
import Bots.PriorityUnit;
import Bots.Steps.Step;

public class AttackStep extends Step {
    private PriorityUnit activePriorityUnit;

    public AttackStep(PriorityUnit activePriorityUnit) {
        this.activePriorityUnit = activePriorityUnit;
    }


    @Override
    public void run(BattleManager battleManager) {

    }

    public PriorityUnit getActivePriorityUnit() {
        return activePriorityUnit;
    }

    public void setActivePriorityUnit(PriorityUnit activePriorityUnit) {
        this.activePriorityUnit = activePriorityUnit;
    }

}
