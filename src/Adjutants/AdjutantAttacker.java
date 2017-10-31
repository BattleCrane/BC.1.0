package Adjutants;

import BattleFields.BattleManager;
import BattleFields.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс AdjutantAttacker реализует методы атак:
 */
public final class AdjutantAttacker {
    @NotNull
    public static String attack(String targetUnity, int damage) {
        int newHitPoints = Integer.parseInt(targetUnity.substring(0, 1));
        if (newHitPoints - damage >= 0) {
            newHitPoints = newHitPoints - damage;
        } else {
            newHitPoints = 0;
        }
        return newHitPoints + targetUnity.substring(1);
    }

    public static void radiusAttack(BattleManager battleManager, Point middle, int radius, int damage) {
        int x = middle.X();
        int y = middle.Y();
        int countShift = 0; //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        Pattern pattern = Pattern.compile("[hgbfwtGT]");
        Pattern patternBonuses = Pattern.compile("[oHeCBE]");
        for (int i = x - radius; i < x + radius + 1; i++) {
            for (int j = y - countShift; j < y + 1 + countShift; j++) {
                boolean inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
                if (inBounds) {
                    String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                    Matcher matcher = pattern.matcher(currentUnity);
                    Matcher matcherBonus = patternBonuses.matcher(currentUnity);
                    boolean isSame = currentUnity.equals(battleManager.getBattleField().getMatrix().get(x).get(y));
                    if ((matcher.find() || matcherBonus.find()) && !isSame) {
                        for (int q = 0; q < 16; q++) {
                            for (int w = 0; w < 16; w++) {
                                String otherUnity = battleManager.getBattleField().getMatrix().get(q).get(w);
                                String otherUnityNumber = battleManager.getIdentificationField().getMatrix().get(q).get(w);
                                String currentUnityNumber = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                if (otherUnityNumber.equals(currentUnityNumber) &&
                                        !otherUnity.contains(battleManager.getPlayer().getColorType())) {
                                    battleManager.getBattleField().getMatrix().get(q).set(w, attack(otherUnity, damage));
                                }
                            }
                        }
                    }
                }
            }
            countShift++;
            if (i >= x) {
                countShift = countShift - 2; //Перетягивание countShift--
            }
        }
        battleManager.checkDestroyedUnities();
    }

    public static boolean checkTarget(BattleManager battleManager, Point attackerPoint, Point targetPoint) {
        Pattern pattern = Pattern.compile("[hgbfwt]");
        Pattern patternBonuses = Pattern.compile("[oiu]");
        if (attackerPoint.X() == targetPoint.X()) {
            for (int j = min(attackerPoint.Y(), targetPoint.Y()) + 1; j < max(attackerPoint.Y(), targetPoint.Y()); j++) {
                String currentUnity = battleManager.getBattleField().getMatrix().get(attackerPoint.X()).get(j);
                Matcher matcher = pattern.matcher(currentUnity);
                Matcher matcherBonuses = patternBonuses.matcher(currentUnity);
                if ((matcher.find() || matcherBonuses.find()) && !currentUnity.contains(battleManager.getPlayer().getColorType())) {
                    return false;
                }
            }
            return true;
        }
        if (attackerPoint.Y() == targetPoint.Y()) {
            for (int i = min(attackerPoint.X(), targetPoint.X()) + 1; i < max(attackerPoint.X(), targetPoint.X()); i++) {
                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(attackerPoint.Y());
                Matcher matcher = pattern.matcher(currentUnity);
                Matcher matcherBonuses = patternBonuses.matcher(currentUnity);
                if ((matcher.find() || matcherBonuses.find()) && !currentUnity.contains(battleManager.getPlayer().getColorType())) {
                    return false;
                }
            }
            return true;
        }
        if (Math.abs(attackerPoint.X() - targetPoint.X()) == Math.abs(attackerPoint.Y() - targetPoint.Y())) { //Если лежат на одной диагонали
            final boolean distanceY = targetPoint.Y() - attackerPoint.Y() > 0;
            final boolean distanceX = targetPoint.X() - attackerPoint.X() > 0;
            int pointerX = attackerPoint.X();
            int pointerY = attackerPoint.Y();
            int count  = 0;
            while (pointerX != targetPoint.X() && pointerY != targetPoint.Y()) {
                if (distanceY && distanceX) {
                    pointerX++;
                    pointerY++;
                }
                if (distanceY && !distanceX) {
                    pointerX--;
                    pointerY++;
                }
                if (!distanceY && distanceX) {
                    pointerX++;
                    pointerY--;
                }
                if (!distanceX && !distanceY){
                    pointerX--;
                    pointerY--;
                }
                count++;
                String currentUnity = battleManager.getBattleField().getMatrix().get(pointerX).get(pointerY);
                Matcher matcher = pattern.matcher(currentUnity);
                Matcher matcherBonuses = patternBonuses.matcher(currentUnity);
                if (count != 0 && count != Math.abs(targetPoint.X() - attackerPoint.X()) && (matcher.find() || matcherBonuses.find()) &&
                        !currentUnity.contains(battleManager.getPlayer().getColorType())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    @Contract(pure = true)
    private static int min(int a, int b) {
        if (a <= b) {
            return a;
        } else {
            return b;
        }
    }


    @Contract(pure = true)
    private static int max(int a, int b) {
        if (a <= b) {
            return b;
        } else {
            return a;
        }
    }
}


