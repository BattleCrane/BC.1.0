package polytech.polyNexus.probes;

import game.adjutants.AdjutantFielder;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import botInterface.probes.Probe;
import org.jetbrains.annotations.Contract;
import polytech.polyNexus.probes.parametres.ParentParams;
import polytech.priority.PolyPriorityUnit;
import polytech.priority.Priorities;
import game.unities.Unity;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class PolyBuildingProbe implements Probe {
    private final Logger logger = Logger.getLogger(PolyBuildingProbe.class.getName());

    private final int WALL_COEFFICIENT = 16;
    private final double DISTANCE_COEFFICIENT = 0.1;

    private final AdjutantFielder adjutantFielder = new AdjutantFielder();

    private final BattleManager battleManager;
    private final Priorities map;
    private final PolyZoneProbe zoneProbe;
    private final PolyDistanceProbe distanceProbe;

    public PolyBuildingProbe(BattleManager battleManager, Priorities map, PolyZoneProbe zoneProbe, PolyDistanceProbe distanceProbe) {
        this.battleManager = battleManager;
        this.map = map;
        this.zoneProbe = zoneProbe;
        this.distanceProbe = distanceProbe;
    }

    public static final class Params extends ParentParams {
        private final Unity unity;
        private final Point point;

        public Params(Unity unity, Point point) {
            this.unity = unity;
            this.point = point;
        }
    }

    @NotNull
    @Override
    public Object probe(ParentParams params) {
        Params buildingParams = (Params) params;
        return probeBuilding(buildingParams.unity, buildingParams.point);
    }

    //Прямая точка:
    @NotNull
    private PriorityUnit probeBuilding(Unity unity, Point point) {
        double startValue = map.getPriorities().get(unity.getId().charAt(0));
        double value = startValue;
        for (int i = point.X(); i < point.X() + unity.getWidth(); i++) {
            for (int j = point.Y(); j < point.Y() + unity.getHeight(); j++) {
                if (zoneProbe.getDangerousZone().contains(new Point(j, i)) && !unity.getId().equals("w")) {
                    value = -value;
                    break;
                }
            }
        }
        value += probeLock(unity, point);
        PolyDistanceProbe.Params params = new PolyDistanceProbe.Params(unity.getWidth(), unity.getHeight(), point);
        Integer distance = (Integer) distanceProbe.probe(params);
        logger.info("Distance: " + distance);
        if (unity.getId().equals("w")) {
            value += (WALL_COEFFICIENT - distance) * DISTANCE_COEFFICIENT * startValue;
        } else {
            value += distance * DISTANCE_COEFFICIENT * startValue;
        }
        return new PolyPriorityUnit(value, point, unity);
    }


    public int probeLock(Unity unity, Point point) {
        int currentPoints = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(j).get(i).charAt(0) ==
                        battleManager.getPlayer().getColorType().charAt(0)) {
                    currentPoints++;
                }
            }
        }
        int futurePoints = 0;
        String field = battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y());
        if (unity.getId().equals("w")) {
            battleManager.putDoubleWall(battleManager.getPlayer(), point, unity);
        } else {
            battleManager.putUnity(battleManager.getPlayer(), point, unity);
        }

        adjutantFielder.fillZones(battleManager);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(j).get(i).charAt(0) ==
                        battleManager.getPlayer().getColorType().charAt(0)) {
                    futurePoints++;
                }
            }
        }
        int QuantityBuildings = 1; //Существуют постройки, которые строятся по 2
        if (unity.getId().equals("w")) {//Пример стена
            QuantityBuildings *= 2;
        }
        for (int i = point.X(); i < point.X() + unity.getWidth(); i++) {
            for (int j = point.Y(); j < point.Y() + unity.getHeight() * QuantityBuildings; j++) {
                battleManager.getIdentificationField().getMatrix().get(i).set(j, "  0");
                battleManager.getBattleField().getMatrix().get(i).set(j, field);
            }
        }
        adjutantFielder.flush(battleManager);
        adjutantFielder.fillZones(battleManager);
        return (futurePoints - currentPoints) * 30;
    }

    @Contract(pure = true)
    public PolyZoneProbe getZoneProbe() {
        return zoneProbe;
    }
}
