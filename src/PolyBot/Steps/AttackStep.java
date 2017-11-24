package PolyBot.Steps;

import BattleFields.BattleManager;
import Bots.Priority.PriorityUnit;
import Bots.Steps.Step;

public class AttackStep extends Step {
    private PriorityUnit activePriorityUnit;

    protected AttackStep(BattleManager battleManager, PriorityUnit priorityUnit) {
        super(battleManager, priorityUnit);
    }


    public PriorityUnit getActivePriorityUnit() {
        return activePriorityUnit;
    }

    public void setActivePriorityUnit(PriorityUnit activePriorityUnit) {
        this.activePriorityUnit = activePriorityUnit;
    }

    @Override
    public void run() {

    }
}
