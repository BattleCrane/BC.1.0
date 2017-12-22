package TestPolyBot.combinations;

import botInterface.probes.Probe;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import org.jetbrains.annotations.NotNull;
import polytech.polyCombinations.polyFinders.building.genesisBuilding.PolyGenesisBuilder;
import polytech.polyCombinations.polyFinders.building.iteratorBuilding.PolyIteratorBuilder;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import polytech.polyCombinations.polyFinders.upgrading.PolyIteratorUpgrading;
import polytech.priority.PolyPriorityUnit;
import game.unities.Unity;
import org.junit.Test;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.*;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenesisBuildingTests implements TestInitializer{
    private final Logger logger = Logger.getLogger(GenesisBuildingTests.class.getName());

    @NotNull
    private PolyGenesisBuilder initGenesisBuilder(BattleManager battleManager) {
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

    @Test
    public void randomCombination() {
        BattleManager battleManager = initBattleManager();
        setArmy(battleManager, 0, 0, 0, 0, 0 , 0);
        setBuildings(battleManager, 1, false, 2);
        battleManager.getBattleField().toString();

        PolyGenesisBuilder polyGenesisBuilder = initGenesisBuilder(battleManager);

        polyGenesisBuilder.createPopulation(battleManager, 5);
        battleManager.getBattleField().toString();
        System.out.println(polyGenesisBuilder.getCombinations().toString());
    }

    @Test
    public void merge() {
        CreatingCombination creatingCombination = new CreatingCombination(new ArrayList<>(), 0);
        creatingCombination.add(new PolyPriorityUnit(10, new Point(0, 0), new Unity("test")));
        creatingCombination.add(new PolyPriorityUnit(100, new Point(0, 0), new Unity("test")));
        creatingCombination.add(new PolyPriorityUnit(1000, new Point(0, 0), new Unity("test")));
        CreatingCombination other = new CreatingCombination(new ArrayList<>(), 0);
        other.add(new PolyPriorityUnit(20, new Point(7, 7), new Unity("!")));
        other.add(new PolyPriorityUnit(200, new Point(7, 7), new Unity("!")));
        other.add(new PolyPriorityUnit(2000, new Point(7, 7), new Unity("!")));

        PolyGenesisBuilder polyGenesisBuilder = initGenesisBuilder(new BattleManager());
        CreatingCombination merged = polyGenesisBuilder.merge(creatingCombination, other);
        assertTrue(1210.0 == merged.getSum());
    }

    @Test
    public void mutate() {
        BattleManager battleManager = initBattleManager();

        CreatingCombination creatingCombination = new CreatingCombination(new ArrayList<>(), 0);
        creatingCombination.add(new PolyPriorityUnit(20, new Point(8, 8), battleManager.getBarracks()));
        PolyGenesisBuilder polyGenesisBuilder = initGenesisBuilder(battleManager);
        CreatingCombination mutated = polyGenesisBuilder.mutate(battleManager, creatingCombination);

        System.out.println(mutated);
        battleManager.getBattleField().toString();
        assertEquals(new PolyPriorityUnit(600.0, new Point(14, 9), battleManager.getGenerator()),
                mutated.getUnits().get(0));
    }

    @Test
    public void findCombination() {
        BattleManager battleManager = initBattleManager();
        setBuildings(battleManager, 2, false, 2);
        setArmy(battleManager, 0,0,0,0,0, 0);

        battleManager.putUnity(battleManager.getPlayer(), new Point(5, 5), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayer(), new Point(12, 2), battleManager.getGenerator());
        battleManager.putUnity(battleManager.getPlayer(), new Point(7, 4), battleManager.getGenerator());
        battleManager.putUnity(battleManager.getPlayer(), new Point(3, 14), battleManager.getBarracks());

        battleManager.getBattleField().toString();

        PolyGenesisBuilder polyGenesisBuilder = initGenesisBuilder(battleManager);
        CreatingCombination best = polyGenesisBuilder.findBuildCombination(battleManager);
        System.out.println(best);
        battleManager.getBattleField().toString();
    }


    @Override
    public Object createTest(BattleManager battleManager, Probe.Params parentParams) {
        return null;
    }
}
