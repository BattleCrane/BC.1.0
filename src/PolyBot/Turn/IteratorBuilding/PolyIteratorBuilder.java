package PolyBot.Turn.IteratorBuilding;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Turn.Creating.CreatingList;
import PolyBot.Turn.Creating.ConditionalUnit;
import PolyBot.Turn.PolyProbe;

import java.util.ArrayList;
import java.util.List;

public class PolyIteratorBuilder {
    private CreatingList bestCombinationOfBuild = new CreatingList(new ArrayList<>(), -10000.0);
    private CreatingList currentCombinationOfBuild = new CreatingList(new ArrayList<>(), 0.0);
    private double max = 0.0;


    public void findCombination(BattleManager battleManager, int howICanBuild) {
        List<ConditionalUnit> conditionalUnitList = List.of(
                //Barracks:
                new ConditionalUnit(battleManager, battleManager.getBarracks(), (s) -> {}, (e) -> {}) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                                battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer());
                    }
                },
                //Factory:
                new ConditionalUnit(battleManager, battleManager.getFactory(),
                        (s) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() - 1),
                        (e) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() + 1)) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return battleManager.getHowCanBuildFactories() > 0 && battleManager.canConstructBuilding(point,
                                battleManager.getFactory(), battleManager.getPlayer()) &&
                                battleManager.isEmptyTerritory(point, battleManager.getFactory());
                    }
                },
                //Generator:
                new ConditionalUnit(battleManager, battleManager.getGenerator(),
                        (s) -> battleManager.setConstructedGenerator(true),
                        (e) -> battleManager.setConstructedGenerator(false)) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return howICanBuild <= 2 && !battleManager.isConstructedGenerator() &&
                                battleManager.canConstructBuilding(point, battleManager.getGenerator(), battleManager.getPlayer()) &&
                                battleManager.isEmptyTerritory(point, battleManager.getGenerator());
                    }
                }


        );


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                Point point = new Point(j, i);
                if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                    for (ConditionalUnit unit : conditionalUnitList) {
                        if (unit.isPerformedCondition(point)) {
                            checkConditionalUnit(battleManager, unit, point, currentUnity, howICanBuild);
                        }
                    }
                }
            }
        }
    }

    private void checkConditionalUnit(BattleManager battleManager, ConditionalUnit conditionalUnit, Point point, String currentUnity, int howICanBuild) {
        if (conditionalUnit.isPerformedCondition(point)) {//Если территория свободна и рядом есть мои строения
            PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, conditionalUnit.getUnity(), point); //Исследуем приоритет на бараки
            if (!currentCombinationOfBuild.contains(priorityUnit) && battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
                currentCombinationOfBuild.add(priorityUnit);
                int nextBuild = howICanBuild - 1;
                conditionalUnit.getStartPredicate().run(battleManager);
                if (nextBuild > 0) {
                    findCombination(battleManager, nextBuild);
                } else {
                    if (currentCombinationOfBuild.getSum() > bestCombinationOfBuild.getSum()) {
                        max = currentCombinationOfBuild.getSum();
                        bestCombinationOfBuild = new CreatingList(new ArrayList<>(), 0);
                        for (PriorityUnit p : currentCombinationOfBuild.getPriorityUnitList()) {
                            bestCombinationOfBuild.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
                        }
                    }
                }
                currentCombinationOfBuild.removeLast();
                battleManager.removeUnity(point, conditionalUnit.getUnity(), currentUnity.substring(0, 1));
                conditionalUnit.getEndPredicate().run(battleManager);
//                            new AdjutantFielder().flush(battleManager);
//                            new AdjutantFielder().fillZones(battleManager);
            }
        }
    }

    public CreatingList getBestCombinationOfBuild() {
        System.out.println("Max: " + max);
        return bestCombinationOfBuild;
    }

}
