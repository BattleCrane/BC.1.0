package BattleFields;
import Players.Player;
import org.jetbrains.annotations.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс Attacker реализует метод Attack:
 * Уменьшает здоровье юнита на damage.
 */
public final class Attacker {
    @NotNull
    public static String attack(String targetUnity, int damage){
        int newHitPoints = Integer.parseInt(targetUnity.substring(0, 1));
        if (newHitPoints - damage >= 0){
            newHitPoints = newHitPoints - damage;
        }
        return  newHitPoints + targetUnity.substring(1);
    }

    public static void radiusAttack(ControlBattler controlBattler, BattleField battleField, IdentificationField identificationField, Point middle, int radius, int damage, Player opponentPlayer){
        int x = middle.getX();
        int y = middle.getY();
        int countShift = 0;
        Pattern pattern = Pattern.compile("[hgbfwtGT]");
        for (int i = x - radius; i < x + radius + 1; i++){
            for (int j = y - countShift; j < y + 1 + countShift; j++){
                Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(j));
                boolean  inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
                boolean  isThis = battleField.getMatrix().get(i).get(j).equals(battleField.getMatrix().get(x).get(y));
                if (inBounds && matcher.find() && !isThis){
                    for (int q = 0; q < 16; q++){
                        for (int w = 0; w < 16; w++){
                            if (identificationField.getMatrix().get(q).get(w).equals(identificationField.getMatrix().get(i).get(j)) &&
                                    !battleField.getMatrix().get(q).get(w).contains(controlBattler.getPlayer().getColorType())){
                                battleField.getMatrix().get(q).set(w, attack(battleField.getMatrix().get(q).get(w), damage));
                                System.out.println(identificationField.getMatrix().get(i).get(j));
                            }
                        }
                    }
                }
            }
            countShift++;
            if (i >= x){
                countShift = countShift - 2;
            }
        }
        controlBattler.checkDestroyedUnities();
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
