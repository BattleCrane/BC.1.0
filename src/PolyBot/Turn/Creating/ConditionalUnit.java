package PolyBot.Turn.Creating;

import BattleFields.BattleManager;
import BattleFields.Point;
import Unities.Unity;

public abstract class ConditionalUnit {

    public static class CollectionOfConditionalUnits extends ConditionalUnit{

        public CollectionOfConditionalUnits(){}

        @Override
        public boolean isPerformedCondition(Point point) {
            return false;
        }
    }

    public PolyPredicate getStartPredicate() {
        return startPredicate;
    }

    public PolyPredicate getEndPredicate() {
        return endPredicate;
    }

    private BattleManager battleManager;
    private Unity unity;
    private PolyPredicate startPredicate;
    private PolyPredicate endPredicate;

    public ConditionalUnit (){}

    public ConditionalUnit(BattleManager battleManager, Unity unity, PolyPredicate startPredicate, PolyPredicate endPredicate) {
        this.battleManager = battleManager;
        this.unity = unity;
        this.startPredicate = startPredicate;
        this.endPredicate = endPredicate;
    }



    public abstract boolean isPerformedCondition(Point point);


//    List<ConditionalUnit> conditionalUnitList = List.of(
//            //Barracks:
//            new ConditionalUnit(battleManager, battleManager.getBarracks(), (battleManager) -> {}, (battleManager) -> {}) {
//                @Override
//                public boolean isPerformedCondition(Point point) {
//                    return battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
//                            battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer());
//                }
//            }
//            //Factory:
//
//
//
//    );
    public BattleManager getBattleManager() {
        return battleManager;
    }

    public Unity getUnity() {
        return unity;
    }
}
