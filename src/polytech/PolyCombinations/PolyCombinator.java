package polytech.PolyCombinations;

import Adjutants.AdjutantFielder;
import BattleFields.BattleManager;
import Controllers.ControllerMatchMaking;
import polytech.PolyCombinations.Army.IteratorArmy.PolyIteratorArmy;
import polytech.PolyCombinations.Building.GenesisBuilding.PolyGenesisBuilder;
import polytech.PolyCombinations.Building.IteratorBuilding.PolyIteratorBuilder;
import polytech.PolyCombinations.CreatingTools.CreatingCombination;
import polytech.PolyCombinations.upgrading.PolyUpgrading;
import polytech.polyNexus.PolyNexus;

public class PolyCombinator {
    private final ControllerMatchMaking controllerMatchMaking;
    private final BattleManager battleManager;
    private final PolyNexus nexus;
    private final PolyIteratorBuilder iteratorBuilder;
    private final PolyGenesisBuilder genesisBuilder;
    private final PolyIteratorArmy iteratorArmy;
    private final PolyUpgrading upgrading;

    private CreatingCombination combination;

    public PolyCombinator(ControllerMatchMaking controllerMatchMaking, PolyNexus nexus
            , PolyIteratorBuilder iteratorBuilder, PolyGenesisBuilder genesisBuilder
            , PolyIteratorArmy iteratorArmy, PolyUpgrading upgrading) {

        this.controllerMatchMaking = controllerMatchMaking;
        this.battleManager = controllerMatchMaking.getBattleManager();
        this.nexus = new PolyNexus(controllerMatchMaking);
        this.iteratorBuilder = new PolyIteratorBuilder(battleManager, nexus.getBuildingProbe(), nexus.getRadiusProbe());
        this.genesisBuilder  = new PolyGenesisBuilder(battleManager, nexus.getBuildingProbe(), nexus.getRadiusProbe());
        this.iteratorArmy = new PolyIteratorArmy(battleManager, nexus.getBallisticProbe());
        this.upgrading = new PolyUpgrading();
    }





    //Определяет, что будет делать:
    private void chooseDevelopment() {
        //Всегда проверяем постройки:
        BattleManager battleManager = controllerMatchMaking.getBattleManager();
        PolyGenesisBuilder polyGenesisBuilder = new PolyGenesisBuilder(battleManager);
        CreatingCombination buildings = polyGenesisBuilder.findBuildCombination(battleManager);
        System.out.println("Buildings: " + buildings);
        if (!controllerMatchMaking.getButtonCreateArmy().isVisible()){
            combination = buildings;
        }

        //Если всё-таки кнопочка с армией открыта -> проверяем армию
        if (controllerMatchMaking.getButtonCreateArmy().isVisible()){
            AdjutantFielder adjutantFielder = new AdjutantFielder();
            adjutantFielder.flush(battleManager);
            adjutantFielder.fillZones(battleManager);
            PolyIteratorArmy polyIteratorArmy = new PolyIteratorArmy();
            CreatingCombination army = polyIteratorArmy.findCombination(battleManager);
            // Если строения набрали больше
            if (buildings.getSum() > army.getSum()){
                combination = buildings;
                // Если армия набрала не меньше
            } else {
                combination = army;
            }
        }
    }

    private CreatingCombination findBuilding(){
        PolyIteratorBuilder polyIteratorBuilder = new PolyIteratorBuilder();
        polyIteratorBuilder.findTurretCombination(battleManager);
        turretCombinations = polyIteratorBuilder.getBestCombinationOfBuild();
    }





}
