package TestPolyBot.combinations;

import botInterface.probes.Probe;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import polytech.polyCombinations.PolyCombinator;
import polytech.polyCombinations.polyFinders.building.genesisBuilding.PolyGenesisBuilder;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import polytech.priority.PolyPriorityUnit;
import game.unities.Unity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class GenesisBuildingTests implements TestInitializer{
    private final Logger logger = Logger.getLogger(GenesisBuildingTests.class.getName());

    @Test
    public final void randomCombination() {
        BattleManager battleManager = initBattleManager();
        setArmy(battleManager, 0, 0, 0, 0, 0 , 0);
        setBuildings(battleManager, 1, false, 2);
        battleManager.getBattleField().toString();

        PolyGenesisBuilder polyGenesisBuilder = PolyCombinator.createGenesisBuilder(battleManager);
        polyGenesisBuilder.getBuildingProbe().getZoneProbe().probe(null);

        polyGenesisBuilder.createPopulation(battleManager, 5);
        logger.info(battleManager.getBattleField().toString());
        logger.info(polyGenesisBuilder.getCombinations().toString());
    }

    @Test
    public final void merge() {
        CreatingCombination creatingCombination = new CreatingCombination(new ArrayList<>(), 0){{
            add(new PolyPriorityUnit(10, new Point(0, 0), new Unity("test")));
            add(new PolyPriorityUnit(100, new Point(0, 0), new Unity("test")));
            add(new PolyPriorityUnit(1000, new Point(0, 0), new Unity("test")));
        }};
        CreatingCombination other = new CreatingCombination(new ArrayList<>(), 0){{
            add(new PolyPriorityUnit(20, new Point(7, 7), new Unity("!")));
            add(new PolyPriorityUnit(200, new Point(7, 7), new Unity("!")));
            add(new PolyPriorityUnit(2000, new Point(7, 7), new Unity("!")));
        }};
        BattleManager battleManager = initBattleManager();
        PolyGenesisBuilder polyGenesisBuilder = PolyCombinator.createGenesisBuilder(battleManager);
        CreatingCombination merged = polyGenesisBuilder.merge(creatingCombination, other);

        logger.info("Sum: " + merged.getSum());
        assertTrue(1210.0 == merged.getSum());
    }

    @Test
    public final void mutate() {
        BattleManager battleManager = initBattleManager();

        CreatingCombination creatingCombination = new CreatingCombination(new ArrayList<>(), 0);
        creatingCombination.add(new PolyPriorityUnit(20, new Point(8, 8), battleManager.getBarracks()));
        PolyGenesisBuilder polyGenesisBuilder = PolyCombinator.createGenesisBuilder(battleManager);
        polyGenesisBuilder.getBuildingProbe().getZoneProbe().probe(null);
        CreatingCombination mutated = polyGenesisBuilder.mutate(battleManager, creatingCombination);

        logger.info(mutated.toString());
        logger.info(battleManager.getBattleField().toString());
        assertEquals(new PolyPriorityUnit(600.0, new Point(14, 9), battleManager.getGenerator()),
                mutated.getUnits().get(0));
    }

    @Test
    public final void findCombinationTest1() {
        BattleManager manager = initBattleManager();
        createTest(manager, null, () -> {
            setBuildings(manager, 2, false, 2);
            setArmy(manager, 0,0,0,0,0, 0);
            manager.putUnity(manager.getPlayer(), new Point(5, 5), manager.getTurret());
            manager.putUnity(manager.getPlayer(), new Point(12, 2), manager.getGenerator());
            manager.putUnity(manager.getPlayer(), new Point(7, 4), manager.getGenerator());
            manager.putUnity(manager.getPlayer(), new Point(3, 14), manager.getBarracks());
        }, -1);
    }

    @Test
    public final void findCombinationTest2() {
        BattleManager manager1 = initBattleManager();
        createTest(manager1, null, () -> {
            setBuildings(manager1, 2, false, 2);
            setArmy(manager1, 0,0,0,0,0, 0);
        }, -1);
    }

    @Override
    public Object createTest(BattleManager manager, Probe.Params parentParams) {
        logger.info(manager.getBattleField().toString());

        PolyGenesisBuilder polyGenesisBuilder = PolyCombinator.createGenesisBuilder(manager);
        polyGenesisBuilder.getBuildingProbe().getZoneProbe().probe(null);
        CreatingCombination best = polyGenesisBuilder.findBuildCombination();
        logger.info(best.toString());
        logger.info(manager.getBattleField().toString());
        return -1;
    }
}
