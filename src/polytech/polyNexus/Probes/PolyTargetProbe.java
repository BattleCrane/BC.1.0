package polytech.polyNexus.Probes;

import Adjutants.AdjutantAttacker;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Probes.Probe;
import polytech.Steps.AttackStep;
import Controllers.ControllerMatchMaking;
import Players.Player;
import polytech.Priority.Priorities;
import polytech.polyNexus.Probes.parametres.Params;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PolyTargetProbe implements Probe {
    private final Logger log = Logger.getLogger(this.getClass().toString());

    private final ControllerMatchMaking controllerMatchMaking;
    private final BattleManager battleManager;
    private final Priorities map;

    public PolyTargetProbe(ControllerMatchMaking controllerMatchMaking, Priorities map) {
        this.controllerMatchMaking = controllerMatchMaking;
        this.battleManager = controllerMatchMaking.getBattleManager();
        this.map = map;
    }

    static class TargetParams extends Params {
        private final Player player;

        public TargetParams(Player player) {
            this.player = player;
        }
    }

    @Override
    public Object probe(Params params) {
        TargetParams targetParams = (TargetParams) params;
        return findAttackSteps(targetParams.player);
    }

    public List<AttackStep> findAttackSteps(Player player) {
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
                    attacks.add(new AttackStep(controllerMatchMaking, map, pointCheck));
                }
            }
        }
        return attacks;
    }

    public Point findBestShot(Point point) {

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

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Point currentPoint = new Point(i, j);
                String unit = battleManager.getBattleField().getMatrix().get(i).get(j);
                String target = unit.substring(4, 5);
                if (adjutantAttacker.checkTarget(battleManager, point, currentPoint) && !target.equals(" ") &&
                        !target.equals("X") && !unit.substring(3, 4).equals(battleManager.getPlayer().getColorType())) {
                    System.out.println("Searching:             " + target);
                    Target currentTarget = new Target(currentPoint, map.getPriorities().get(target.charAt(0)));
                    if (currentTarget.priority > best.priority) {
                        best = currentTarget;
                    }
                }
            }
        }

        System.out.println("Target: " + best.point);
        log.info("Target: " + best.point);
        return best.point;
    }
}
