package BattleFields;

import Unities.Unity;

/**
 * Класс Attacker реализует метод Attack:
 * Уменьшает здоровье юнита на damage.
 */
public class Attacker {
    public static String attack(String targetUnity, int damage){
        String targetUnityId = targetUnity;
        int newHitPoints = Integer.parseInt(targetUnityId.substring(0, 1)) - damage;
        return  newHitPoints + targetUnityId.substring(1);
    }
}
