package Bots.Steps;

import BattleFields.BattleManager;
import Bots.Priority.PriorityUnit;

public abstract class Step {
    protected BattleManager battleManager;
    protected PriorityUnit priorityUnit;

    protected Step (BattleManager battleManager, PriorityUnit priorityUnit){
        this.battleManager = battleManager;
        this.priorityUnit = priorityUnit;
    }

    public abstract void run();
}
