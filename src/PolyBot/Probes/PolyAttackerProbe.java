package PolyBot.Probes;

import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantFielder;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Bots.Steps.AttackStep;
import Controllers.ControllerMatchMaking;
import Players.Player;
import PolyBot.Priority.PolyMapOfPriority;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyAttackerProbe {
    PolyMapOfPriority polyMapOfPriority = new PolyMapOfPriority();

    public PolyAttackerProbe(ControllerMatchMaking controllerMatchMaking) {
        this.controllerMatchMaking = controllerMatchMaking;
    }

    private ControllerMatchMaking controllerMatchMaking;

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
                    attacks.add(new AttackStep(pointCheck, controllerMatchMaking));
                }
            }
        }
        return attacks;
    }

    public Point findBestShot(BattleManager battleManager, Point point){

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
                Point currentPoint = new Point(i, j);
                String unit = battleManager.getBattleField().getMatrix().get(i).get(j);
                String target = unit.substring(4, 5);
                if (adjutantAttacker.checkTarget(battleManager, point, currentPoint) && !target.equals(" ") &&
                        !target.equals("X") && !unit.substring(3, 4).equals(battleManager.getPlayer().getColorType())){
                    System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR:             " + target);
                    Target currentTarget = new Target(currentPoint, polyMapOfPriority.getMapOfPriorityUnits().get(target.charAt(0)));
                    if (currentTarget.priority > best.priority){
                        best = currentTarget;
                    }
                }
            }
        }
        System.out.println("Target: " + best.point);
        return best.point;
    }
}
