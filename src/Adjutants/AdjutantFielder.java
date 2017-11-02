package Adjutants;

import BattleFields.BattleManager;
import BattleFields.Point;

import java.util.ArrayList;
import java.util.List;

public class AdjutantFielder {
    List<Point> listPassed = new ArrayList<>();

    public void correctTerritory(BattleManager battleManager){

    }
    //Blue:
    private void searchCloseTerritories(BattleManager battleManager){
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                String Unity = battleManager.getBattleField().getMatrix().get(i).get(j);
                Point cursorPoint = new Point(i, j);
                if (Unity.substring(3, 4).equals("+") && !listPassed.contains(cursorPoint)){
                        step(battleManager, cursorPoint);
                }
            }
        }
    }

    private void step(BattleManager battleManager, Point cursorPoint){
        boolean isNotFull = true;
        while (isNotFull){
            List<Point> closestPoints = new ArrayList<>();
            Point pointUp = new Point(cursorPoint.X() + 1, cursorPoint.Y());
            Point pointDown = new Point(cursorPoint.X() - 1, cursorPoint.Y());
            Point pointLeft = new Point(cursorPoint.X(), cursorPoint.Y() - 1);
            Point pointRight = new Point(cursorPoint.X(), cursorPoint.Y() + 1);
            if (checkClosest(battleManager, pointUp) && !listPassed.contains(pointUp)){
                closestPoints.add(pointUp);
            }
            if (checkClosest(battleManager, pointUp) && !listPassed.contains(pointDown)){
                closestPoints.add(pointDown);
            }
            if (checkClosest(battleManager, pointUp) && !listPassed.contains(pointLeft)){
                closestPoints.add(pointLeft);
            }
            if (checkClosest(battleManager, pointUp) && !listPassed.contains(pointRight)){
                closestPoints.add(pointRight);
            }
            if (closestPoints.size() == 0){
                isNotFull = false;
            } else {
                for (int i = 0; i < closestPoints.size() - 1; i++){
                    listPassed.add(closestPoints.get(i));
                    step(battleManager, closestPoints.get(i));
                }
            }
        }
    }
    private boolean checkClosest(BattleManager battleManager, Point point){
        return battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y()).substring(3, 4).equals("+");
    }
    private void fillCloseTerritories(BattleManager battleManager){

    }
}
