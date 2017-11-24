package PolyBot.Steps;

import BattleFields.BattleManager;
import Bots.Priority.PriorityUnit;
import Bots.Steps.Step;
import Unities.Unity;

public final class ProductionStep extends Step {

    protected ProductionStep(BattleManager battleManager, PriorityUnit priorityUnit) {
        super(battleManager, priorityUnit);
    }

    @Override
    public void run() {
        if (priorityUnit.getPriority() != Double.MIN_VALUE){
            Unity unity = priorityUnit.getUnity();
            if (unity.getId().equals("w")){
                battleManager.putDoubleWall(battleManager.getPlayer(), priorityUnit.getPoint(), unity);
            } else {
                battleManager.putUnity(battleManager.getPlayer(), priorityUnit.getPoint(), unity);
            }
        }
    }
}
