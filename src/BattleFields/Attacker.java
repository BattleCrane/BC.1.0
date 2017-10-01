package BattleFields;
import org.jetbrains.annotations.NotNull;

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
}
