package Bots.Steps;

import BattleFields.BattleManager;
import Bots.Priority.PriorityUnit;

public class UnityStep implements Step {
    BattleManager battleManager;
    private PriorityUnit priorityUnit;

    public UnityStep(BattleManager battleManager, PriorityUnit priorityUnit) {
        this.battleManager = battleManager;
        this.priorityUnit = priorityUnit;
    }


    @Override
    public void makeStep() {
        if (!battleManager.putUnity(battleManager.getPlayer(), priorityUnit.getPoint(), priorityUnit.getUnity())){
            battleManager.upgradeBuilding(priorityUnit.getPoint(), battleManager.getPlayer());
        }
    }
}
