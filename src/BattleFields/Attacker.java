package BattleFields;
import Players.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс Attacker реализует метод Attack:
 * Уменьшает здоровье юнита на damage.
 */
public class Attacker {
    @NotNull
    public static String attack(String targetUnity, int damage){
        String targetUnityId = targetUnity;
        int newHitPoints = Integer.parseInt(targetUnityId.substring(0, 1)) - damage;
        return  newHitPoints + targetUnityId.substring(1);
    }

    public static boolean canAttackEnemy(Point beginPoint, Point endPoint, BattleField battleField, Player enemyPlayer){
        Pattern pattern = Pattern.compile("[hgbfwt]");
        if (beginPoint.getX() == endPoint.getX()){
            for (int j = min(beginPoint.getY(), endPoint.getY()); j < max(beginPoint.getY(), endPoint.getY()); j++){
                Matcher matcher = pattern.matcher(battleField.getMatrix().get(beginPoint.getX()).get(j));
                if (matcher.find() && battleField.getMatrix().get(beginPoint.getX()).get(j).contains(enemyPlayer.getColorType())){
                    return false;
                }
            }
        }

        if (beginPoint.getY() == endPoint.getY()){
            for (int i = min(beginPoint.getX(), endPoint.getX()); i < max(beginPoint.getX(), endPoint.getX()); i++){
                Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(beginPoint.getY()));
                if (matcher.find() && battleField.getMatrix().get(i).get(beginPoint.getY()).contains(enemyPlayer.getColorType())){
                    return false;
                }
            }
        }

//        if (Math.abs(beginPoint.getX() - endPoint.getX()) == Math.abs(beginPoint.getY() - endPoint.getY())){
//            for (int i = min(beginPoint.getX(), endPoint.getX()); i < max(beginPoint.getX(), endPoint.getX()); i++){
//                Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(beginPoint.getY()));
//                if (matcher.find() && battleField.getMatrix().get(i).get(beginPoint.getY()).contains(enemyPlayer.getColorType())){
//                    return false;
//                }
//            }
//        }

        return true;
    }



    private static int min(int a, int b){
        if (a <= b){
            return a;
        } else {
            return b;
        }
    }


    private static int max(int a, int b){
        if (a <= b){
            return b;
        } else {
            return a;
        }
    }
}



//|| beginPoint.getY() == endPoint.getY() ||
//        Math.abs(beginPoint.getX() - endPoint.getX()) == Math.abs(beginPoint.getY() - endPoint.getY()));
