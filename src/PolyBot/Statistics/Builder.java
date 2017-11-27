package PolyBot.Statistics;

import Adjutants.AdjutantFielder;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Statistics.Builders.BuildersList;
import Unities.Unity;

import java.util.ArrayList;
import java.util.List;

public class Builder {


    BuildersList bestCombinationOfBuild = new BuildersList(new ArrayList<>(), -10000.0);
    BuildersList currentCombinationOfBuild = new BuildersList(new ArrayList<>(), 0.0);
    double max = 0.0;


    public void findCombination(BattleManager battleManager, int howICanBuild) {
        for (int i = 9; i < 16; i++) {
            for (int j = 9; j < 16; j++) {
                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                Point point = new Point(j, i);
                if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                    if (battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                            battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer())){//Если территория свободна и рядом есть мои строения
                        PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, battleManager.getBarracks(), point); //Исследуем приоритет на бараки
                        System.out.println(priorityUnit.toString() + "x: " + j + "            y: " + i);
                        if (!currentCombinationOfBuild.contains(priorityUnit) && battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
                            currentCombinationOfBuild.add(priorityUnit);
                            int nextBuild = howICanBuild - 1;
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
                            battleManager.removeUnity(point, battleManager.getBarracks());
                            new AdjutantFielder().flush(battleManager);
                            new AdjutantFielder().fillZones(battleManager);
                        }
                    }


                }
            }
        }
    }

    public BuildersList getBestCombinationOfBuild() {
        System.out.println("Max: " + max);
        return bestCombinationOfBuild;
    }

}
