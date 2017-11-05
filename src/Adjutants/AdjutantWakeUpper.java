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

    @NotNull
    public String sleepUnity(String string) {
        return string.substring(0, 2) + "?" + string.substring(3);
    }
}
