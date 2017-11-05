package Adjutants;

import BattleFields.BattleManager;
import BattleFields.Point;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс AdjutantWakeUpper с помощью метода wakeUpUnities активирует ваших юнитов в начале хода.
 */
public final class AdjutantWakeUpper {
    public void wakeUpUnities(BattleManager battleManager){
        Pattern patternBarracksAndFactories = Pattern.compile("[bfi]'");
        Pattern patternGunnersAndTanks = Pattern.compile("[GTt]");
        Pattern patternBonuses = Pattern.compile("[AHCBEQ]");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                List<String> list = battleManager.getBattleField().getMatrix().get(i);
                Matcher matcherBarracksAndFactories = patternBarracksAndFactories.matcher(list.get(j));
                Matcher matcherGunnersAndTanks = patternGunnersAndTanks.matcher(list.get(j));
                Matcher matcherBonuses = patternBonuses.matcher(list.get(j));
                if ((matcherBarracksAndFactories.find() || matcherGunnersAndTanks.find() || matcherBonuses.find()) && list.get(j).
                        contains(battleManager.getPlayer().getColorType())) {
                    String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                   battleManager.getBattleField().getMatrix().get(i).set(j, readyUnity);
                   if (battleManager.getBattleField().getMatrix().get(i).get(j).contains("t")){
                       if (battleManager.getBattleField().getMatrix().get(i).get(j).contains("^")){
                           battleManager.getAdjutantAttacker().radiusAttack(battleManager, new Point(i, j), 2, 1);
                       }
                       if (battleManager.getBattleField().getMatrix().get(i).get(j).contains("<")){
                           battleManager.getAdjutantAttacker().radiusAttack(battleManager, new Point(i, j), 3, 1);
                       }
                   }
                }
            }
        }
    }

    public void wakeUpExactly(BattleManager battleManager, int x, int y){
        battleManager.getBattleField().getMatrix().get(x).set(y,
                battleManager.getBattleField().getMatrix().get(x).get(y).substring(0, 2) + "!" +
        battleManager.getBattleField().getMatrix().get(x).get(y).substring(3));
    }

    public void wakeUpHeadquarters(BattleManager battleManager){
        if (battleManager.getPlayer().getColorType().equals("+")) {
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 14, 14);
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 14, 15);
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 15, 14);
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 15, 15);
        }
        if (battleManager.getPlayer().getColorType().equals("-")) {
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 1, 1);
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 1, 0);
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 0, 1);
            battleManager.getAdjutantWakeUpper().wakeUpExactly(battleManager, 0, 0);
        }
    }

    public void sleepHeadquarters(BattleManager battleManager){
        battleManager.getBattleField().getMatrix().get(0).set(0,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(0).get(0)));
        battleManager.getBattleField().getMatrix().get(0).set(1,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(0).get(1)));
        battleManager.getBattleField().getMatrix().get(1).set(0,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(1).get(0)));
        battleManager.getBattleField().getMatrix().get(1).set(1,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(1).get(1)));
        battleManager.getBattleField().getMatrix().get(14).set(14,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(14).get(14)));
        battleManager.getBattleField().getMatrix().get(14).set(15,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(14).get(15)));
        battleManager.getBattleField().getMatrix().get(15).set(14,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(15).get(14)));
        battleManager.getBattleField().getMatrix().get(15).set(15,
                battleManager.getAdjutantWakeUpper().sleepUnity(battleManager.getBattleField().getMatrix().get(15).get(15)));
    }


    @NotNull
    public String sleepUnity(String string) {
        return string.substring(0, 2) + "?" + string.substring(3);
    }
}
