package Adjutants;

import BattleFields.BattleManager;
import BattleFields.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс AdjutantAttacker реализует методы Атак:
 */
public final class AdjutantAttacker {
    @NotNull
    public static String attack(String targetUnity, int damage) {
        int newHitPoints = Integer.parseInt(targetUnity.substring(0, 1));
        if (newHitPoints - damage >= 0) {
            newHitPoints = newHitPoints - damage;
        }
        return newHitPoints + targetUnity.substring(1);
    }

    public static void radiusAttack(BattleManager battleManager, Point middle, int radius, int damage) {
        int x = middle.X();
        int y = middle.Y();
        int countShift = 0; //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        Pattern pattern = Pattern.compile("[hgbfwtGT]");
        for (int i = x - radius; i < x + radius + 1; i++) {
            for (int j = y - countShift; j < y + 1 + countShift; j++) {
                boolean inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
                if (inBounds) {
                    String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                    Matcher matcher = pattern.matcher(currentUnity);
                    boolean isSame = currentUnity.equals(battleManager.getBattleField().getMatrix().get(x).get(y));
                    if (matcher.find() && !isSame) {
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