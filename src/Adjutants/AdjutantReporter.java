package Adjutants;

import BattleFields.BattleManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс AdjutantReporter с помощью метода getReportAboutUnities рапортует о том, какие юниты и сколько их вы можете сделать.
 */

// TODO: 16.12.2017 make AdjutantReporter injectable
public final class AdjutantReporter {
    public void getReportAboutUnities(BattleManager battleManager){
        Pattern patternBarracks = Pattern.compile("[!?][+-]b'"); //Шаблон бараков
        Pattern patternGenerators = Pattern.compile("g'"); //Шаблон генераторов
        Pattern patternFactories = Pattern.compile("[!?][+-]f'"); //Шаблон заводов
        int howICanBuild = 1;
        int howICanProductArmyLvl1 = 0;
        int howICanProductArmyLvl2 = 0;
        int howICanProductArmyLvl3 = 0;
        int howICanProductTanks1 = 0;
        int howICanProductTanks2 = 0;
        int howICanProductTanks3 = 0;
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
                        if (list.get(j).contains("^")){
                            howICanProductArmyLvl1++;
                        }
                        if (list.get(j).contains("<")){
                            howICanProductArmyLvl2++;
                        }
                        if (list.get(j).contains(">")){
                            howICanProductArmyLvl3++;
                        }
                    }
                    if (matcherFactories.find() && list.get(j).contains(battleManager.getPlayer().getColorType())) {
                        if (list.get(j).contains("^")){
                            howICanProductTanks1++;
                        }
                        if (list.get(j).contains("<")){
                            howICanProductTanks2++;
                        }
                        if (list.get(j).contains(">")){
                            howICanProductTanks3++;
                        }

                    }
                }

            }
        }
        battleManager.setHowICanBuild(howICanBuild);
        battleManager.setHowICanProductArmyLevel1(howICanProductArmyLvl1);
        battleManager.setHowICanProductArmyLevel2(howICanProductArmyLvl2);
        battleManager.setHowICanProductArmyLevel3(howICanProductArmyLvl3);
        battleManager.setHowICanProductTanksLevel1(howICanProductTanks1);
        battleManager.setHowICanProductTanksLevel2(howICanProductTanks2);
        battleManager.setHowICanProductTanksLevel3(howICanProductTanks3);
    }
}
