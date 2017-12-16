package PolyBot.Probes;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Bots.Probes.Probe;
import Players.Player;
import PolyBot.Priority.Priorities;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Probes.parametres.Params;
import Unities.Unity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyBallisticProbe implements Probe{
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
    }

    static class BallisticParams extends Params{
        private final Unity unity;
        private final Point point;

        public BallisticParams(Unity unity, Point point){
            this.unity = unity;
            this.point = point;
        }
    }

    @Override
    public Object probe(Params params) {
        BallisticParams ballisticParams = (BallisticParams) params;
        Unity unity = ballisticParams.unity;
        Point point = ballisticParams.point;

        double startValue = map.getPriorities().get(unity.getId().charAt(0));
        double value = startValue;
        if (zoneProbe.getDangerousZone().contains(point.invariant())) {
            value = -value;
        }
        value += distanceProbe.findClosestEnemy(battleManager, point, unity.getWidth(), unity.getHeight()) *
                0.1 * startValue;
        Player currentPlayer = battleManager.getPlayer();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        value += collectValOfBallisticUnit(currentPlayer, matrix, point);
        return new PolyPriorityUnit(value, point, unity);
    }


    @NotNull
    @Contract(pure = true)
    public PriorityUnit probeBallisticUnit(BattleManager battleManager, Unity unity, Point point) {
        double startValue = map.getPriorities().get(unity.getId().charAt(0));
        double value = startValue;
        if (listDangerousZone.contains(point.invariant())) {
            value = -value;
        }
        value += findClosestEnemy(battleManager, point, unity.getWidth(), unity.getHeight()) *
                0.1 * startValue;
        Player currentPlayer = battleManager.getPlayer();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        value += collectValOfBallisticUnit(currentPlayer, matrix, point);
        return new PolyPriorityUnit(value, point, unity);
    }

    public double collectValOfBallisticUnitTest(Player currentPlayer, List<List<String>> matrix, Point start) {
        return collectValOfBallisticUnit(currentPlayer, matrix, start);
    }

    private double collectValOfBallisticUnit(Player currentPlayer, List<List<String>> matrix, Point point) {
        double value = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                } else {
                    Point start = new Point(point.X(), point.Y());
                    Priorities priorities = new Priorities();
                    Pattern patternNotBlockedUnits = Pattern.compile("[GTQoHeCBE]");
                    Pattern patternBuildings = Pattern.compile("[hgbfwtiu]");
                    boolean isSecondaryPurpose = false;
                    while (start.X() + dx >= 0 && start.X() + dx < 16 && start.Y() + dy >= 0 && start.Y() + dy < 16) {
                        start.setX(start.X() + dx);
                        start.setY(start.Y() + dy);
                        String currentUnity = matrix.get(start.Y()).get(start.X()).substring(1);
                        Matcher matcher = patternBuildings.matcher(currentUnity.substring(3, 4));
                        Matcher matcherNotBlockedUnits = patternNotBlockedUnits.matcher(currentUnity.substring(3, 4));
                        boolean OpponentBuilding = matcher.matches() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType());
                        boolean OpponentOtherUnit = matcherNotBlockedUnits.matches() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType());
                        if (OpponentBuilding) {
                            if (!isSecondaryPurpose) {
                                value += priorities.getPriorities().get(currentUnity.charAt(3)) * 0.5;
                                isSecondaryPurpose = true;
                            } else {
                                value += priorities.getPriorities().get(currentUnity.charAt(3)) * 0.2;
                            }
                        }
                        if (OpponentOtherUnit) {
                            if (!isSecondaryPurpose) {
                                value += priorities.getPriorities().get(currentUnity.charAt(3)) * 0.5;
                            } else {
                                value += priorities.getPriorities().get(currentUnity.charAt(3)) * 0.2;
                            }
                        }
                    }
                }
            }
        }
        return value;
    }


}
