package Adjutants;

import BattleFields.BattleManager;
import BattleFields.Point;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by мсиайнина on 05.10.2017.
 */
public class AdjutantWakeUpper {

    public static void wakeUpUnities(BattleManager battleManager){
        Pattern patternBarracksAndFactories = Pattern.compile("[bf]'");
        Pattern patternGunnersAndTanks = Pattern.compile("[GTt]");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                List<String> list = battleManager.getBattleField().getMatrix().get(i);
                Matcher matcherBarracksAndFactories = patternBarracksAndFactories.matcher(list.get(j));
                Matcher matcherGunnersAndTanks = patternGunnersAndTanks.matcher(list.get(j));
                if ((matcherBarracksAndFactories.find() || matcherGunnersAndTanks.find()) && list.get(j).contains(battleManager.getPlayer().getColorType())) {
                    String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                   battleManager.getBattleField().getMatrix().get(i).set(j, readyUnity);
                   if (battleManager.getBattleField().getMatrix().get(i).get(j).contains("t")){
                       if (battleManager.getBattleField().getMatrix().get(i).get(j).contains("^")){
                           AdjutantAttacker.radiusAttack(battleManager, new Point(i, j), 2, 1);
                       }
                       if (battleManager.getBattleField().getMatrix().get(i).get(j).contains("<")){
                           AdjutantAttacker.radiusAttack(battleManager, new Point(i, j), 3, 1);
                       }
                   }
                }
            }
        }
    }
}
