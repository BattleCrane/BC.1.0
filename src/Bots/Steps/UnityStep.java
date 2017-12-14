package Bots.Steps;

import Adjutants.AdjutantAttacker;
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

        class WallChecker{
            private boolean check(){
                if (priorityUnit.getUnity().getId().equals("w")){
                    return battleManager.putDoubleWall(battleManager.getPlayer(), priorityUnit.getPoint(), battleManager.getWall());
                } else {
                    return battleManager.putUnity(battleManager.getPlayer(), priorityUnit.getPoint(), priorityUnit.getUnity());
                }
            }
        }
        WallChecker wallChecker = new WallChecker();
        if (!wallChecker.check()){
            battleManager.upgradeBuilding(priorityUnit.getPoint(), battleManager.getPlayer());
        }

        if (priorityUnit.getUnity().getId().equals("t")){
            new AdjutantAttacker().radiusAttack(battleManager, priorityUnit.getPoint(), 2, 1);
        }
    }
}
