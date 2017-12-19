package polytech.polyCombinations.creatingTools;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import game.unities.Unity;
import org.jetbrains.annotations.Contract;

public abstract class EstimatedUnit {

    @Contract(pure = true)
    public final PolyPlanner takeResources() {
        return startPredicate;
    }

    @Contract(pure = true)
    public final PolyPlanner returnResources() {
        return endPredicate;
    }

    private BattleManager battleManager;
    private Unity unity;
    private PolyPlanner startPredicate;
    private PolyPlanner endPredicate;

    public EstimatedUnit(BattleManager battleManager, Unity unity, PolyPlanner startPredicate, PolyPlanner endPredicate) {
        this.battleManager = battleManager;
        this.unity = unity;
        this.startPredicate = startPredicate;
        this.endPredicate = endPredicate;
    }

    public abstract boolean isPerformedCondition(Point point);

    public BattleManager getBattleManager() {
        return battleManager;
    }

    public Unity getUnity() {
        return unity;
    }
}
