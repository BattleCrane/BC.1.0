package Bots.Statistics;

import BattleFields.BattleManager;
import BattleFields.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Adjutants.AdjutantAttacker.max;
import static Adjutants.AdjutantAttacker.min;

public interface Probe {
    default List<Point> probeSafeZone(BattleManager battleManager){
        List<Point> listSafeZone = new ArrayList<>();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        Pattern patternTurrets = Pattern.compile("[tu]");
        Pattern pattern = Pattern.compile("[GT]");
        Pattern patternBonus = Pattern.compile("[HCBEiQ]");
        for (int i = 0;  i < 16; i++){
            for (int j = 0; j < 16; j++){
                String current = matrix.get(j).get(i);
                if (current.substring(3, 4).equals(battleManager.getOpponentPlayer().getColorType())){
                    Matcher matcher = pattern.matcher(current);
                    Matcher matcherBonus = patternBonus.matcher(current);
                    Matcher matcherTurrets = patternTurrets.matcher(current);
                    if (matcherBonus.find() || matcher.find()){




                    }
                    if ( matcherTurrets.find()){

                    }
                }

            }
        }


        return listSafeZone;
    }

    private void shift(List<List<String>> matrix, List<Point> listSafeZone, int dx, int dy, Point start){
        if (matrix.get(start.Y() + dy).get(start.X() + dx).substring(1).equals("    0")){
            Point nextPoint = new Point(start.Y() + dy, start.X() + dx);
            listSafeZone.add(nextPoint);
            shift(matrix, listSafeZone, dx, dy, nextPoint);
        }
    }

//    private boolean markDangerous(BattleManager battleManager, Point attackerPoint) {
//        while ()
//
//
//
//
//
//
//
//
//
//
//
//        if (attackerPoint.X() == targetPoint.X()) {
//            for (int j = min(attackerPoint.Y(), targetPoint.Y()) + 1; j < max(attackerPoint.Y(), targetPoint.Y()); j++) {
//                String currentUnity = battleManager.getBattleField().getMatrix().get(attackerPoint.X()).get(j);
//                Matcher matcher = pattern.matcher(currentUnity);
//                Matcher matcherBonuses = patternBonuses.matcher(currentUnity);
//                if ((matcher.find() || matcherBonuses.find()) && !currentUnity.contains(battleManager.getPlayer().getColorType())) {
//                    return false;
//                }
//            }
//            return true;
//        }
//        if (attackerPoint.Y() == targetPoint.Y()) {
//            for (int i = min(attackerPoint.X(), targetPoint.X()) + 1; i < max(attackerPoint.X(), targetPoint.X()); i++) {
//                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(attackerPoint.Y());
//                Matcher matcher = pattern.matcher(currentUnity);
//                Matcher matcherBonuses = patternBonuses.matcher(currentUnity);
//                if ((matcher.find() || matcherBonuses.find()) && !currentUnity.contains(battleManager.getPlayer().getColorType())) {
//                    return false;
//                }
//            }
//            return true;
//        }
//        if (Math.abs(attackerPoint.X() - targetPoint.X()) == Math.abs(attackerPoint.Y() - targetPoint.Y())) { //Если лежат на одной диагонали
//            final boolean distanceY = targetPoint.Y() - attackerPoint.Y() > 0;
//            final boolean distanceX = targetPoint.X() - attackerPoint.X() > 0;
//            int pointerX = attackerPoint.X();
//            int pointerY = attackerPoint.Y();
//            int count  = 0;
//            while (pointerX != targetPoint.X() && pointerY != targetPoint.Y()) {
//                if (distanceY && distanceX) {
//                    pointerX++;
//                    pointerY++;
//                }
//                if (distanceY && !distanceX) {
//                    pointerX--;
//                    pointerY++;
//                }
//                if (!distanceY && distanceX) {
//                    pointerX++;
//                    pointerY--;
//                }
//                if (!distanceX && !distanceY){
//                    pointerX--;
//                    pointerY--;
//                }
//                count++;
//                String currentUnity = battleManager.getBattleField().getMatrix().get(pointerX).get(pointerY);
//                Matcher matcher = pattern.matcher(currentUnity);
//                Matcher matcherBonuses = patternBonuses.matcher(currentUnity);
//                if (count != 0 && count != Math.abs(targetPoint.X() - attackerPoint.X()) && (matcher.find() || matcherBonuses.find()) &&
//                        !currentUnity.contains(battleManager.getPlayer().getColorType())){
//                    return false;
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//

}
