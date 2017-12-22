package TestPolyBot.combinations;

import botInterface.probes.Probe;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import polytech.polyCombinations.PolyCombinator;
import polytech.polyCombinations.polyFinders.building.iteratorBuilding.PolyIteratorBuilder;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import org.junit.Test;

import java.util.logging.Logger;

public class IteratorBuildingTest implements TestInitializer {
    private final Logger logger = Logger.getLogger(IteratorBuildingTest.class.getName());

    @Test
    public void findTurretCombination() {
        BattleManager manager = initBattleManager();
        createTest(manager, null, () -> {
            manager.putUnity(manager.getPlayer(), new Point(5, 5), manager.getTurret());
            manager.putUnity(manager.getPlayer(), new Point(12, 2), manager.getGenerator());
            manager.putUnity(manager.getPlayer(), new Point(7, 4), manager.getGenerator());
            manager.putUnity(manager.getPlayer(), new Point(3, 14), manager.getBarracks());
            manager.setHowICanBuild(3);
        }, -1);
    }

    @Override
    public Object createTest(BattleManager battleManager, Probe.Params parentParams) {
        PolyIteratorBuilder iteratorBuilder = PolyCombinator.createIteratorBuilder(battleManager);
        iteratorBuilder.getBuildingProbe().getZoneProbe().probe(null);
        iteratorBuilder.findTurretCombination();
        CreatingCombination turretCombination = iteratorBuilder.getBest();

        logger.info(battleManager.getBattleField().toString());
        logger.info(turretCombination.toString());
        return -1;
    }
}
