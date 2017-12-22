package TestPolyBot.combinations;

import botInterface.probes.Probe;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import org.jetbrains.annotations.NotNull;
import polytech.polyCombinations.PolyCombinator;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import polytech.polyCombinations.polyFinders.upgrading.PolyIteratorUpgrading;
import org.junit.Test;

import java.util.logging.Logger;

//Worked:
public final class IteratorUpgradingTest implements TestInitializer {
    private final Logger logger = Logger.getLogger(IteratorUpgradingTest.class.getName());

    @Test
    public final void findCombination() {
        BattleManager manager = initBattleManager();
        createTest(manager, null, () -> {
            manager.putUnity(manager.getPlayerBlue(), new Point(7, 10), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(14, 1), manager.getBarracks());
            manager.putUnity(manager.getPlayerBlue(), new Point(12, 4), manager.getTurret());
            manager.setHowICanBuild(3);
        }, 630.0);
    }

    @NotNull
    @Override
    public final Object createTest(BattleManager manager, Probe.Params parentParams) {
        PolyIteratorUpgrading polyIteratorUpgrading = PolyCombinator.createIteratorUpgrading(manager);
        CreatingCombination combination = polyIteratorUpgrading.findCombination();
        logger.info(combination.toString());
        return combination.getSum();
    }
}
