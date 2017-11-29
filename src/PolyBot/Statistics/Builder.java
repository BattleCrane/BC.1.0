package PolyBot.Statistics;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Statistics.Builders.BuildersList;
import PolyBot.Statistics.Builders.ConditionalUnit;

import java.util.ArrayList;
import java.util.List;

public class Builder {





    BuildersList bestCombinationOfBuild = new BuildersList(new ArrayList<>(), -10000.0);
    BuildersList currentCombinationOfBuild = new BuildersList(new ArrayList<>(), 0.0);
    double max = 0.0;

    public void findCombination(BattleManager battleManager, int howICanBuild) {
        List<ConditionalUnit> conditionalUnitList = List.of(
                //Barracks:
                new ConditionalUnit(battleManager, battleManager.getBarracks(), (s) -> {}, (e) -> {}) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                                battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer());
                    }
                }
                //Factory:



        );


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                Point point = new Point(j, i);
                if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                    for (ConditionalUnit unit: conditionalUnitList){
                        if (unit.isPerformedCondition(point)){
                            checkConditionalUnit(battleManager, unit, point, currentUnity, howICanBuild);
                        }
                    }



//                    if (battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
//                            battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer())){//Если территория свободна и рядом есть мои строения
//                        PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, battleManager.getBarracks(), point); //Исследуем приоритет на бараки
//                        System.out.println(priorityUnit.toString() + "x: " + j + "            y: " + i);
//                        if (!currentCombinationOfBuild.contains(priorityUnit) && battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
//                            currentCombinationOfBuild.add(priorityUnit);
//                            int nextBuild = howICanBuild - 1;
//                            if (nextBuild > 0) {
//                                findCombination(battleManager, nextBuild);
//                            } else {
//                                if (currentCombinationOfBuild.getSum() > bestCombinationOfBuild.getSum()) {
//                                    max = currentCombinationOfBuild.getSum();
//                                    bestCombinationOfBuild = new BuildersList(new ArrayList<>(), 0);
//                                    for (PriorityUnit p : currentCombinationOfBuild.getPriorityUnitList()) {
//                                        bestCombinationOfBuild.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
//                                    }
//                                }
//                            }
//                            currentCombinationOfBuild.removeLast();
//                            battleManager.removeUnity(point, battleManager.getBarracks(), currentUnity.substring(0, 1));
////                            new AdjutantFielder().flush(battleManager);
////                            new AdjutantFielder().fillZones(battleManager);
//                        }
//                    }


                }
            }
        }
    }

    private void checkConditionalUnit(BattleManager battleManager, ConditionalUnit conditionalUnit,  Point point, String currentUnity, int howICanBuild) {
        if (battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer())) {//Если территория свободна и рядом есть мои строения
            PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, battleManager.getBarracks(), point); //Исследуем приоритет на бараки
            if (!currentCombinationOfBuild.contains(priorityUnit) && battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
                currentCombinationOfBuild.add(priorityUnit);
                int nextBuild = howICanBuild - 1;
                conditionalUnit.getStartPredicate().run(battleManager);
                if (nextBuild > 0) {
                    findCombination(battleManager, nextBuild);
                } else {
                    if (currentCombinationOfBuild.getSum() > bestCombinationOfBuild.getSum()) {
                        max = currentCombinationOfBuild.getSum();
                        bestCombinationOfBuild = new BuildersList(new ArrayList<>(), 0);
                        for (PriorityUnit p : currentCombinationOfBuild.getPriorityUnitList()) {
                            bestCombinationOfBuild.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
                        }
                    }
                }
                currentCombinationOfBuild.removeLast();
                battleManager.removeUnity(point, battleManager.getBarracks(), currentUnity.substring(0, 1));
                conditionalUnit.getEndPredicate().run(battleManager);
//                            new AdjutantFielder().flush(battleManager);
//                            new AdjutantFielder().fillZones(battleManager);
            }
        }
    }

    public BuildersList getBestCombinationOfBuild() {
        System.out.println("Max: " + max);
        return bestCombinationOfBuild;
    }

}
