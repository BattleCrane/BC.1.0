package polytech.polyCombinations;

import game.adjutants.AdjutantFielder;
import game.battleFields.BattleManager;
import game.controllers.ControllerMatchMaking;
import game.players.Player;
import org.jetbrains.annotations.NotNull;
import polytech.polyCombinations.polyFinders.iteratorArmy.PolyIteratorArmy;
import polytech.polyCombinations.polyFinders.building.genesisBuilding.PolyGenesisBuilder;
import polytech.polyCombinations.polyFinders.building.iteratorBuilding.PolyIteratorBuilder;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import polytech.polyCombinations.polyFinders.upgrading.PolyIteratorUpgrading;
import polytech.polyNexus.probes.*;
import polytech.priority.Priorities;
import polytech.steps.AttackStep;
import polytech.polyNexus.PolyNexus;

import java.util.List;
import java.util.logging.Logger;

public class PolyCombinator {
    private final Logger logger = Logger.getLogger(PolyCombinator.class.getName());

    private final ControllerMatchMaking controllerMatchMaking;
    private final BattleManager battleManager;
    private final PolyNexus nexus;
    private final PolyIteratorBuilder iteratorBuilder;
    private final PolyGenesisBuilder genesisBuilder;
    private final PolyIteratorArmy iteratorArmy;
    private final PolyIteratorUpgrading iteratorUpgrading;

    private CreatingCombination combination;

    public PolyCombinator(ControllerMatchMaking controllerMatchMaking) {
        this.controllerMatchMaking = controllerMatchMaking;
        this.battleManager = controllerMatchMaking.getBattleManager();
        this.nexus = new PolyNexus(controllerMatchMaking);
        this.iteratorBuilder = new PolyIteratorBuilder(battleManager, nexus.getBuildingProbe(), nexus.getRadiusProbe());
        this.iteratorUpgrading = new PolyIteratorUpgrading(battleManager, nexus.getUpgradingProbe());
        this.genesisBuilder  = new PolyGenesisBuilder(battleManager, nexus.getBuildingProbe(), nexus.getRadiusProbe()
                , this.iteratorUpgrading, this.iteratorBuilder);
        this.iteratorArmy = new PolyIteratorArmy(battleManager, nexus.getBallisticProbe());
    }

    //Определяет, что будет делать:
    public CreatingCombination chooseDevelopment() {
        nexus.getZoneProbe().probe(null);
        //Всегда проверяем постройки:
        CreatingCombination genesisBuildings = genesisBuilder.findBuildCombination();
        System.out.println("Buildings: " + genesisBuildings);
        logger.info("Buildings: " + genesisBuildings);
        if (!controllerMatchMaking.getButtonCreateArmy().isVisible()){
            combination = genesisBuildings;
        }
        //Если всё-таки кнопочка с армией открыта -> проверяем армию
        if (controllerMatchMaking.getButtonCreateArmy().isVisible()){
            AdjutantFielder adjutantFielder = new AdjutantFielder();
            adjutantFielder.flush(battleManager);
            adjutantFielder.fillZones(battleManager);
            CreatingCombination army = iteratorArmy.findCombination(battleManager);
            // Если строения набрали больше
            if (genesisBuildings.getSum() > army.getSum()){ // Если армия набрала не меньше
                combination = genesisBuildings;
                return genesisBuildings;
            } else {
                combination = army;
                return army;
            }
        }
        return genesisBuildings;
    }

    public List<AttackStep> chooseAttacks() {
        Player player = battleManager.getPlayer();
        PolyTargetProbe targetProbe = new PolyTargetProbe(controllerMatchMaking, nexus.getMap());
        return targetProbe.findAttackSteps(player);
    }


    //Factories:
    @NotNull
    public static PolyIteratorArmy createIteratorArmy(BattleManager manager){
        return new PolyIteratorArmy(manager,  PolyNexus.createBallisticProbe(manager));
    }

    @NotNull
    public static PolyIteratorBuilder createIteratorBuilder(BattleManager battleManager){
        Priorities priorities = new Priorities();
        PolyDistanceProbe polyDistanceProbe = new PolyDistanceProbe(battleManager);
        PolyZoneProbe polyZoneProbe = new PolyZoneProbe(battleManager);

        PolyBuildingProbe polyBuildingProbe = new PolyBuildingProbe(battleManager, priorities
                , polyZoneProbe, polyDistanceProbe);

        PolyRadiusProbe polyRadiusProbe = new PolyRadiusProbe(battleManager, priorities
                , polyZoneProbe, polyDistanceProbe);

        return new PolyIteratorBuilder(battleManager, polyBuildingProbe, polyRadiusProbe);
    }

    @NotNull
    public static PolyIteratorUpgrading createIteratorUpgrading(BattleManager battleManager){
        return new PolyIteratorUpgrading(battleManager, PolyNexus.createUpgradingProbe(battleManager));
    }

    @NotNull
    public static PolyGenesisBuilder createGenesisBuilder(BattleManager battleManager) {
        Priorities priorities = new Priorities();
        PolyDistanceProbe polyDistanceProbe = new PolyDistanceProbe(battleManager);
        PolyZoneProbe polyZoneProbe = new PolyZoneProbe(battleManager);

        PolyBuildingProbe polyBuildingProbe = new PolyBuildingProbe(battleManager, priorities
                , polyZoneProbe, polyDistanceProbe);

        PolyRadiusProbe polyRadiusProbe = new PolyRadiusProbe(battleManager, priorities
                , polyZoneProbe, polyDistanceProbe);

        PolyIteratorUpgrading polyIteratorUpgrading = new PolyIteratorUpgrading(battleManager
                , new PolyUpgradingProbe(battleManager, priorities));

        PolyIteratorBuilder polyIteratorBuilder = new PolyIteratorBuilder(battleManager, polyBuildingProbe
                , polyRadiusProbe);

        return new PolyGenesisBuilder(battleManager, polyBuildingProbe
                , polyRadiusProbe, polyIteratorUpgrading, polyIteratorBuilder);
    }

    //Getters:
    public CreatingCombination getCombination() {
        return combination;
    }
}
