package PolyBot.Probes;

import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantFielder;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Bots.Steps.AttackStep;
import Players.Player;
import PolyBot.Priority.PolyMapOfPriority;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyAttackerProbe {
    PolyMapOfPriority polyMapOfPriority = new PolyMapOfPriority();

    public List<AttackStep> findAttackSteps(BattleManager battleManager, Player player) {
        List<AttackStep> attacks = new ArrayList<>();

        Pattern patternGunnersAndTanks = Pattern.compile("[GTt]");
        Pattern patternBonuses = Pattern.compile("[AHCBEQ]");

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String unity = battleManager.getBattleField().getMatrix().get(j).get(i);
                String type = unity.substring(4, 5);
                Matcher matcherBasic = patternGunnersAndTanks.matcher(type);
                Matcher matcherBonus = patternBonuses.matcher(type);
                if (unity.substring(3, 4).equals(player.getColorType()) && (matcherBonus.find() || matcherBasic.find())) {
                    Point pointCheck = new Point(j, i);
                    attacks.add(new AttackStep(findBestShot(battleManager, pointCheck), pointCheck));
                }
            }
        }
        return attacks;
    }

    private Point findBestShot(BattleManager battleManager, Point point){

        class Target {
            private Point point;
            private double priority;

            private Target(Point point, double priority) {
                this.point = point;
                this.priority = priority;
            }
        }

        Target best = new Target(null, 0);

        AdjutantAttacker adjutantAttacker = new AdjutantAttacker();

        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16;  j++){
                Point currentPoint = new Point(j, i);
                if (adjutantAttacker.checkTarget(battleManager, point, currentPoint)){
                    String target = battleManager.getBattleField().getMatrix().get(j).get(i);
                    Target currentTarget = new Target(currentPoint, polyMapOfPriority.getMapOfPriorityUnits().get(target.charAt(0)));
                    if (currentTarget.priority > best.priority){
                        best = currentTarget;
                    }
                }
            }
        }
        return best.point;
    }
}
