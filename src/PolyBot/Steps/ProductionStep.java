package PolyBot.Steps;

import BattleFields.BattleManager;
import Bots.Priority.PriorityUnit;
import Bots.Statistics.Probe;
import Bots.Steps.Step;
import Unities.Unity;

public final class ProductionStep extends Step {

    public ProductionStep(BattleManager battleManager, PriorityUnit priorityUnit) {
        super(battleManager, priorityUnit);
    }

    @Override
    public void run() {
        int width = priorityUnit.getWidth();
        int height = priorityUnit.getHeight();
        String id = "" + priorityUnit.getName();
        int hitPoints = priorityUnit.getHitPoints();
        if (priorityUnit.getName() == 'w'){
            battleManager.putDoubleWall(battleManager.getPlayer(), priorityUnit.getPoint(), new Unity(width, height, id, hitPoints));
        } else {
            battleManager.putUnity(battleManager.getPlayer(), priorityUnit.getPoint(), new Unity(width, height, id, hitPoints));
        }
    }
}
