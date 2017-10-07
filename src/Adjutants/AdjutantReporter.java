package Adjutants;

import BattleFields.BattleManager;
import Players.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс AdjutantReporter с помощью метода getReportAboutUnities рапортует о том, какие юниты и сколько их вы можете сделать.
 */
public final class AdjutantReporter {
    public static void getReportAboutUnities(BattleManager battleManager){
        Pattern patternBarracks = Pattern.compile("[!?][+-]b'"); //Шаблон бараков
        Pattern patternGenerators = Pattern.compile("g'"); //Шаблон генераторов
        Pattern patternFactories = Pattern.compile("[!?][+-]f'"); //Шаблон заводов
        int howICanBuild = 1;
        int howICanProductArmy = 0;
        int howICanProductTanks = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                List<String> list = battleManager.getBattleField().getMatrix().get(i);
                Matcher matcherGenerators = patternGenerators.matcher(list.get(j));
                Matcher matcherBarracks = patternBarracks.matcher(list.get(j));
                Matcher matcherFactories = patternFactories.matcher(list.get(j));
                if (list.get(j).contains(battleManager.getPlayer().getColorType())){
                    if (matcherGenerators.find()) {
                        String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                        battleManager.getBattleField().getMatrix().get(i).set(j, readyUnity);
                        howICanBuild++;
                    }
                    if (matcherBarracks.find() && list.get(j).contains(battleManager.getPlayer().getColorType())) {
                        howICanProductArmy++;
                    }
                    if (matcherFactories.find() && list.get(j).contains(battleManager.getPlayer().getColorType())) {
                        howICanProductTanks++;
                    }
                }

            }
        }
        battleManager.setHowICanBuild(howICanBuild);
        battleManager.setHowICanProductArmy(howICanProductArmy);
        battleManager.setHowICanProductTanks(howICanProductTanks);
    }
}
