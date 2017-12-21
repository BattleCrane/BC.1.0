package polytech.polyNexus;

import game.battleFields.BattleManager;
import game.controllers.ControllerMatchMaking;
import org.jetbrains.annotations.NotNull;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.*;


// TODO: 21.12.17 make Factories
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


    /**
     * Класс PoyNexus реализует фабрику Probe.
     * Это используется в большей мере для удобства тестирования, чем
     * используется в реальной механики игры.
     * В данном случае фабрика упрощает конструкторы для сложных Probes.
     */

    @NotNull
    public static PolyZoneProbe createZoneProbe(BattleManager battleManager){
        return new PolyZoneProbe(battleManager);
    }

    @NotNull
    public static PolyDistanceProbe createDistanceProbe(BattleManager battleManager){
        return new PolyDistanceProbe(battleManager);
    }

    @NotNull
    public static PolyTargetProbe createTargetProbe(ControllerMatchMaking controllerMatchMaking){
        return new PolyTargetProbe(controllerMatchMaking, new Priorities());
    }

    @NotNull
    public static PolyUpgradingProbe createUpgradingProbe(BattleManager battleManager){
        return new PolyUpgradingProbe(battleManager, new Priorities());
    }

    @NotNull
    public static PolyBallisticProbe createBallisticProbe(BattleManager manager){
        return new PolyBallisticProbe(manager, new Priorities()
                , new PolyZoneProbe(manager), new PolyDistanceProbe(manager));
    }

    @NotNull
    public static PolyRadiusProbe createRadiusProbe(BattleManager manager){
        return new PolyRadiusProbe(manager, new Priorities()
                , new PolyZoneProbe(manager), new PolyDistanceProbe(manager));
    }

    @NotNull
    public static PolyBuildingProbe createBuildingProbe(BattleManager manager){
        return new PolyBuildingProbe(manager, new Priorities()
                , new PolyZoneProbe(manager), new PolyDistanceProbe(manager));
    }

    //Getters:
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
