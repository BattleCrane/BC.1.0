package PolyBot.Statistics;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;

import java.util.ArrayList;

public class Builder {
    PolyProbe.Pair bestCombinationOfBuild = new PolyProbe.Pair(new ArrayList<>(), -10000.0);
    PolyProbe.Pair currentCombinationOfBuild = new PolyProbe.Pair(new ArrayList<>(), -10000.0);


    public void findCombination(BattleManager battleManager, int howICanBuild){
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                String currentPoint = battleManager.getBattleField().getMatrix().get(j).get(i);
                Point point = new Point(i, j);
                if (currentPoint.substring(1).equals("    0")){
                    PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, battleManager.getBarracks(), point);
                    currentCombinationOfBuild.add(priorityUnit);
                    int nextBuild = howICanBuild - 1;
                    if (nextBuild > 0){
                        findCombination(battleManager, nextBuild);
                    } else {

                    }
                }
            }
        }
    }
}
