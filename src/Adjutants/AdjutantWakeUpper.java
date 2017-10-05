package Adjutants;

import BattleFields.BattleField;
import Players.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by мсиайнина on 05.10.2017.
 */
public class AdjutantWakeUpper {

    //Переводит в боевое состояние пехоту и танки:
    public static void wakeUpArmy(BattleField battleField, Player player) {
        Pattern pattern = Pattern.compile("[GTt]");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(j));
                List<String> list = battleField.getMatrix().get(i);
                if (matcher.find() && list.get(j).contains(player.getColorType())) {
                    String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                    battleField.getMatrix().get(i).set(j, readyUnity);
                }
            }
        }
    }

    //Переводит в боевое состояние строения:
    public static void wakeUpBuilding(BattleField battleField, Player player) {
        Pattern pattern = Pattern.compile("[bf]'");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(j));
                List<String> list = battleField.getMatrix().get(i);
                if (matcher.find() && list.get(j).contains(player.getColorType())) {
                    String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                    battleField.getMatrix().get(i).set(j, readyUnity);
                }
            }
        }
    }
}
