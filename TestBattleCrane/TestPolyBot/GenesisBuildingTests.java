package TestPolyBot;

import Adjutants.AdjutantFielder;
import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import PolyBot.PolyCombinations.Building.GenesisBuilding.PolyGenesisBuilder;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import PolyBot.Priority.PolyPriorityUnit;
import Unities.Unity;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenesisBuildingTests {
    @Test
    public void randomCombination(){
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.setOpponentPlayer(battleManager.getPlayerRed());
        battleManager.initializeField();
        new AdjutantFielder().fillZones(battleManager);
        battleManager.getBattleField().toString();
        battleManager.setHowICanBuild(1);
        battleManager.setConstructedGenerator(false);
        battleManager.setHowICanBuildFactories(2);
        battleManager.setHowICanProductArmyLevel1(0);
        battleManager.setHowICanProductArmyLevel2(0);
        battleManager.setHowICanProductArmyLevel3(0);
        battleManager.setHowICanProductTanksLevel1(0);
        battleManager.setHowICanProductTanksLevel1(0);
        battleManager.setHowICanProductTanksLevel1(0);

        PolyGenesisBuilder polyGenesisBuilder = new PolyGenesisBuilder(battleManager);
        polyGenesisBuilder.createPopulation(battleManager, 5);
        battleManager.getBattleField().toString();

//        assertTrue(polyGenesisBuilder.getCombinations().size() == 2);
        //Вывод популяции:
        System.out.println(polyGenesisBuilder.getCombinations().toString());
    }

    @Test
    public void merge(){
        CreatingCombination creatingCombination = new CreatingCombination(new ArrayList<>(), 0);
        creatingCombination.add(new PolyPriorityUnit(10, new Point(0, 0), new Unity("test")));
        creatingCombination.add(new PolyPriorityUnit(100, new Point(0, 0), new Unity("test")));
        creatingCombination.add(new PolyPriorityUnit(1000, new Point(0, 0), new Unity("test")));
        CreatingCombination other = new CreatingCombination(new ArrayList<>(),  0);
        other.add(new PolyPriorityUnit(20, new Point(7, 7), new Unity("!")));
        other.add(new PolyPriorityUnit(200, new Point(7, 7), new Unity("!")));
        other.add(new PolyPriorityUnit(2000, new Point(7, 7), new Unity("!")));
        CreatingCombination merged = new PolyGenesisBuilder(new BattleManager()).merge(creatingCombination, other);
        assertTrue(1210.0 == merged.getSum());
    }

    @Test
    public void mutate(){
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.setOpponentPlayer(battleManager.getPlayerRed());
        battleManager.initializeField();
        new AdjutantFielder().fillZones(battleManager);

        CreatingCombination creatingCombination = new CreatingCombination(new ArrayList<>(), 0);
        creatingCombination.add(new PolyPriorityUnit(20, new Point(8, 8), battleManager.getBarracks()));
        PolyGenesisBuilder polyGenesisBuilder = new PolyGenesisBuilder(battleManager);
        CreatingCombination mutated = polyGenesisBuilder.mutate(battleManager, creatingCombination);

        System.out.println(mutated);
        battleManager.getBattleField().toString();
        assertEquals(new PolyPriorityUnit(600.0, new Point(14, 9), battleManager.getGenerator()),
                mutated.getPriorityUnitList().get(0));
    }

    @Test
    public void findCombination(){
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.setOpponentPlayer(battleManager.getPlayerRed());
        battleManager.initializeField();
        new AdjutantFielder().fillZones(battleManager);
        battleManager.getBattleField().toString();
        battleManager.setHowICanBuild(2);
        battleManager.setConstructedGenerator(false);
        battleManager.setHowICanBuildFactories(2);
        battleManager.setHowICanProductArmyLevel1(0);
        battleManager.setHowICanProductArmyLevel2(0);
        battleManager.setHowICanProductArmyLevel3(0);
        battleManager.setHowICanProductTanksLevel1(0);
        battleManager.setHowICanProductTanksLevel1(0);
        battleManager.setHowICanProductTanksLevel1(0);

        PolyGenesisBuilder polyGenesisBuilder = new PolyGenesisBuilder(battleManager);
        CreatingCombination best = polyGenesisBuilder.findBuildCombination(battleManager);
        System.out.println(best);
        battleManager.getBattleField().toString();
//        assertTrue(best.getSum() > 1000.0);
    }
}
