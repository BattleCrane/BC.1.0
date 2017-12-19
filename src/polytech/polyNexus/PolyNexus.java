package polytech.polyNexus;

import game.battleFields.BattleManager;
import game.controllers.ControllerMatchMaking;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.*;

public class PolyNexus {
    private final ControllerMatchMaking controllerMatchMaking;
    private final BattleManager battleManager;
    private final Priorities map = new Priorities();
    //LowLevel:
    private final PolyZoneProbe zoneProbe;
    private final PolyDistanceProbe distanceProbe;
    private final PolyTargetProbe targetProbe;
    private final PolyUpgradingProbe upgradingProbe;
    //HighLevel:
    private final PolyBallisticProbe ballisticProbe;
    private final PolyBuildingProbe buildingProbe;
    private final PolyRadiusProbe radiusProbe;

    public PolyNexus(ControllerMatchMaking controllerMatchMaking) {
        this.controllerMatchMaking = controllerMatchMaking;
        this.battleManager = controllerMatchMaking.getBattleManager();
        //Independent probes:
        zoneProbe = new PolyZoneProbe(battleManager);
        distanceProbe = new PolyDistanceProbe(battleManager);
        targetProbe = new PolyTargetProbe(controllerMatchMaking, map);
        upgradingProbe = new PolyUpgradingProbe(battleManager, map);
        //Dependent probes:
        ballisticProbe = new PolyBallisticProbe(battleManager, map, zoneProbe, distanceProbe);
        radiusProbe = new PolyRadiusProbe(battleManager, map, zoneProbe, distanceProbe);
        buildingProbe = new PolyBuildingProbe(battleManager, map, zoneProbe, distanceProbe);
    }


    public ControllerMatchMaking getControllerMatchMaking() {
        return controllerMatchMaking;
    }

    public BattleManager getBattleManager() {
        return battleManager;
    }

    public Priorities getMap() {
        return map;
    }

    public PolyZoneProbe getZoneProbe() {
        return zoneProbe;
    }

    public PolyDistanceProbe getDistanceProbe() {
        return distanceProbe;
    }

    public PolyTargetProbe getTargetProbe() {
        return targetProbe;
    }

    public PolyUpgradingProbe getUpgradingProbe() {
        return upgradingProbe;
    }

    public PolyBallisticProbe getBallisticProbe() {
        return ballisticProbe;
    }

    public PolyBuildingProbe getBuildingProbe() {
        return buildingProbe;
    }

    public PolyRadiusProbe getRadiusProbe() {
        return radiusProbe;
    }
}
