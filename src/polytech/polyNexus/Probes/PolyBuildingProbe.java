package polytech.polyNexus.Probes;

import Adjutants.AdjutantFielder;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Bots.Probes.Probe;
import polytech.Priority.PolyPriorityUnit;
import polytech.Priority.Priorities;
import polytech.polyNexus.Probes.parametres.Params;
import Unities.Unity;
import org.jetbrains.annotations.NotNull;

public final class PolyBuildingProbe implements Probe {
    private final int WALL_COEFFICIENT = 10;
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

    public static final class BuildingParams extends Params{
        private final Unity unity;
        private final Point point;

        public BuildingParams(Unity unity, Point point) {
            this.unity = unity;
            this.point = point;
        }
    }

    @Override
    public Object probe(Params params) {
        BuildingParams buildingParams = (BuildingParams) params;
        return probeBuilding(buildingParams.unity, buildingParams.point);
    }

    //Прямая точка:
    @NotNull
    private PriorityUnit probeBuilding(Unity unity, Point point) {
        double startValue = map.getPriorities().get(unity.getId().charAt(0));
        double value = startValue;
        for (int i = point.X(); i < point.X() + unity.getWidth(); i++) {
            for (int j = point.Y(); j < point.Y() + unity.getHeight(); j++) {
                if (zoneProbe.getDangerousZone().contains(new Point(j, i))) {
                    value = -value;
                    break;
                }
            }
        }
        value += probeLock(unity, point);
        PolyDistanceProbe.DistanceParams distanceParams = new PolyDistanceProbe
                .DistanceParams(unity.getWidth(), unity.getHeight(), point);
        Integer distance = (Integer) distanceProbe.probe(distanceParams);
        if (unity.getId().equals("w") && zoneProbe.getDangerousZone().contains(new Point(point.Y() + 1, point.X()))) {

            value += (WALL_COEFFICIENT - distance) * DISTANCE_COEFFICIENT * startValue;
        } else {
            value += distance * DISTANCE_COEFFICIENT * startValue;
        }
        return new PolyPriorityUnit(value, point, unity);
    }


    private int probeLock(Unity unity, Point point) {
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
}
