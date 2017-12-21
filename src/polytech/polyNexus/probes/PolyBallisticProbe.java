package polytech.polyNexus.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.probes.Probe;
import game.players.Player;
import org.jetbrains.annotations.Contract;
import polytech.polyNexus.probes.parametres.ParentParams;
import polytech.priority.Priorities;
import polytech.priority.PolyPriorityUnit;
import game.unities.Unity;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PolyBallisticProbe implements Probe {
    private final Logger logger = Logger.getLogger(PolyBallisticProbe.class.getName());
    private final ConsoleHandler consoleHandler = new ConsoleHandler();

    private final Double DISTANCE_COEFFICIENT = 0.1;
    private final Double DIRECT_ATTACK_COEFFICIENT = 0.5;
    private final Double INDIRECT_ATTACK_COEFFICIENT = 0.2;

    private final BattleManager battleManager;
    private final Priorities map;
    private final PolyZoneProbe zoneProbe;
    private final PolyDistanceProbe distanceProbe;

    public PolyBallisticProbe(BattleManager battleManager, Priorities map
            , PolyZoneProbe zoneProbe, PolyDistanceProbe distanceProbe) {
        this.battleManager = battleManager;
        this.map = map;
        this.zoneProbe = zoneProbe;
        this.distanceProbe = distanceProbe;
        consoleHandler.setLevel(Level.FINE);
    }

    public static final class Params extends ParentParams {
        private final Unity unity;
        private final Point point;

        public Params(Unity unity, Point point) {
            this.unity = unity;
            this.point = point;
        }

        @Contract(pure = true)
        public Unity getUnity() {
            return unity;
        }

        @Contract(pure = true)
        public Point getPoint() {
            return point;
        }
    }

    @Override
    public final Object probe(ParentParams params) {
        logger.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);

        Params ballisticParams = (Params) params;
        Unity unity = ballisticParams.unity;
        Point point = ballisticParams.point;

        double startValue = map.getPriorities().get(unity.getId().charAt(0));
        double value = startValue;
        if (zoneProbe.getDangerousZone().contains(point.invariant())) {
            value = -value;
        }
        PolyDistanceProbe.Params distParams = new PolyDistanceProbe.Params(unity.getWidth(), unity.getHeight(), point);
        Integer distance = (Integer) distanceProbe.probe(distParams);
        value += distance * DISTANCE_COEFFICIENT * startValue;
        Player currentPlayer = battleManager.getPlayer();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        value += collect(currentPlayer, matrix, point);
        return new PolyPriorityUnit(value, point, unity);
    }

    public double collect(Player currentPlayer, List<List<String>> matrix, Point point) {

        double value = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                boolean isStartPoint = dx == 0 && dy == 0;
                if (!isStartPoint) {
                    Point start = new Point(point.X(), point.Y());
                    Priorities priorities = new Priorities();
                    Pattern patternNotBlockedUnits = Pattern.compile("[GTQoHeCBE]");
                    Pattern patternBuildings = Pattern.compile("[hgbfwtiu]");
                    boolean isSecondaryPurpose = false;
                    while (start.X() + dx >= 0 && start.X() + dx < 16 && start.Y() + dy >= 0 && start.Y() + dy < 16) {
                        start.setX(start.X() + dx);
                        start.setY(start.Y() + dy);
                        String currentUnity = matrix.get(start.X()).get(start.Y()).substring(1);
                        Matcher matcher = patternBuildings.matcher(currentUnity.substring(3, 4));
                        Matcher matcherNotBlockedUnits = patternNotBlockedUnits.matcher(currentUnity.substring(3, 4));
                        boolean yourUnit = currentUnity.substring(2, 3).equals(currentPlayer.getColorType());
                        boolean opponentBuilding = matcher.matches() && !yourUnit;
                        boolean opponentOtherUnit = matcherNotBlockedUnits.matches() && !yourUnit;

                        if (opponentBuilding) {
                            if (!isSecondaryPurpose) {
                                logger.fine("Other building detected");
                                value += priorities.getPriorities().get(currentUnity.charAt(3))
                                        * DIRECT_ATTACK_COEFFICIENT;
                                isSecondaryPurpose = true;
                            } else {
                                value += priorities.getPriorities().get(currentUnity.charAt(3))
                                        * INDIRECT_ATTACK_COEFFICIENT;
                            }
                        }
                        if (opponentOtherUnit) {
                            logger.fine("Other unit detected");
                            if (!isSecondaryPurpose) {
                                value += priorities.getPriorities().get(currentUnity.charAt(3))
                                        * DIRECT_ATTACK_COEFFICIENT;
                            } else {
                                value += priorities.getPriorities().get(currentUnity.charAt(3))
                                        * INDIRECT_ATTACK_COEFFICIENT;
                            }
                        }
                    }
                }
            }
        }
        return value;
    }

    @Contract(pure = true)
    public PolyZoneProbe getZoneProbe() {
        return zoneProbe;
    }
}
