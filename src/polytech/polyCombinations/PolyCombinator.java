package polytech.polyCombinations;

import game.adjutants.AdjutantFielder;
import game.battleFields.BattleManager;
import game.controllers.ControllerMatchMaking;
import game.players.Player;
import polytech.polyCombinations.Army.iteratorArmy.PolyIteratorArmy;
import polytech.polyCombinations.building.genesisBuilding.PolyGenesisBuilder;
import polytech.polyCombinations.building.iteratorBuilding.PolyIteratorBuilder;
import polytech.polyCombinations.creatingTools.CreatingCombination;
import polytech.polyCombinations.upgrading.PolyIteratorUpgrading;
import polytech.steps.AttackStep;
import polytech.polyNexus.PolyNexus;
import polytech.polyNexus.probes.PolyTargetProbe;
import polytech.polyNexus.probes.parametres.Params;

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
        PolyNexus nexus = new PolyNexus(controllerMatchMaking);
        this.nexus = nexus;
        this.iteratorBuilder = new PolyIteratorBuilder(battleManager, nexus.getBuildingProbe(), nexus.getRadiusProbe());
        this.iteratorUpgrading = new PolyIteratorUpgrading(battleManager, nexus.getUpgradingProbe());
        this.genesisBuilder  = new PolyGenesisBuilder(battleManager, nexus.getBuildingProbe(), nexus.getRadiusProbe()
                , this.iteratorUpgrading, this.iteratorBuilder);
        this.iteratorArmy = new PolyIteratorArmy(battleManager, nexus.getBallisticProbe());
    }

    //Определяет, что будет делать:
    public CreatingCombination chooseDevelopment() {
        nexus.getZoneProbe().probe(new Params());
        //Всегда проверяем постройки:
        CreatingCombination genesisBuildings = genesisBuilder.findBuildCombination(battleManager);
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

    public CreatingCombination getCombination() {
        return combination;
    }
}
