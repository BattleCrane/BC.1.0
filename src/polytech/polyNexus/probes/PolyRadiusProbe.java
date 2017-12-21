package polytech.polyNexus.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import botInterface.probes.Probe;
import game.players.Player;
import polytech.polyNexus.probes.parametres.ParentParams;
import polytech.priority.PolyPriorityUnit;
import polytech.priority.Priorities;
import game.unities.Unity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class PolyRadiusProbe implements Probe {
    private final double DISTANCE_COEFFICIENT = 0.1;
    private final int AVERAGE_DISTANCE = 8;
    private final double ATTACK_COEFFICIENT = 0.5;
    private final int DUTY_COEFICIENT = 10;

    private final BattleManager battleManager;
    private final Priorities map;
    private final PolyZoneProbe zoneProbe;
    private final PolyDistanceProbe distanceProbe;

    public PolyRadiusProbe(BattleManager battleManager, Priorities map, PolyZoneProbe zoneProbe, PolyDistanceProbe distanceProbe) {
        this.battleManager = battleManager;
        this.map = map;
        this.zoneProbe = zoneProbe;
        this.distanceProbe = distanceProbe;
    }

    public static final class RadiusParams extends ParentParams {
        private final Unity unity;
        private final Point start;

        public RadiusParams(Unity unity, Point start) {
            this.unity = unity;
            this.start = start;
        }
    }

    @Override
    public Object probe(ParentParams params) {
        RadiusParams radiusParams = (RadiusParams) params;
        return probeRadiusUnit(radiusParams.unity, radiusParams.start);
    }

    @NotNull
    private PriorityUnit probeRadiusUnit(Unity unity, Point point) {
        double startValue = map.getPriorities().get(unity.getId().charAt(0));
        double value = startValue;
        if (zoneProbe.getDangerousZone().contains(point)) {
            value = -value;
        }
        PolyDistanceProbe.Params params = new PolyDistanceProbe.Params(unity.getWidth(), unity.getHeight(), point);
        Integer distance = (Integer) distanceProbe.probe(params);
        value += (AVERAGE_DISTANCE - distance) * DISTANCE_COEFFICIENT * startValue;
        value += collect(battleManager.getPlayer(), battleManager.getBattleField().getMatrix(), point)
                * ATTACK_COEFFICIENT;
        return new PolyPriorityUnit(value, point, unity);
    }

    public double collect(Player currentPlayer, List<List<String>> matrix, Point start) {
        double value = 0;
        int x = start.X();
        int y = start.Y();
        String current = matrix.get(x).get(y);
        int radius = getRadius(current);
        int countShift = 0; //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        for (int i = x - radius; i < x + radius + 1; i++) {
            for (int j = y - countShift; j < y + 1 + countShift; j++) {
                String otherUnit = matrix.get(j).get(i);
                boolean inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
                if (inBounds && !otherUnit.substring(1).equals("    0") &&
                        otherUnit.charAt(3) != currentPlayer.getColorType().charAt(0)) {
                    value += DUTY_COEFICIENT * map.getPriorities().get(otherUnit.charAt(4));
                }
            }
            countShift++;
            if (i >= x) {
                countShift = countShift - 2; //Перетягивание countShift--
            }
        }
        return value;
    }

    private int getRadius(String current) {
        return zoneProbe.getRadius(current);
    }
}
