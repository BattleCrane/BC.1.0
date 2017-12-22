package TestPolyBot.combinations;

import botInterface.probes.Probe;
import game.battleFields.BattleManager;
import TestPolyBot.TestInitializer;
import polytech.polyCombinations.PolyCombinator;
import polytech.polyCombinations.polyFinders.iteratorArmy.PolyIteratorArmy;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import org.junit.Test;

import java.util.logging.Logger;

//Worked:
public final class CreatingArmyTest implements TestInitializer {
    private final Logger logger = Logger.getLogger(CreatingArmyTest.class.getName());

    @Test
    public final void findCombination() {
        BattleManager manager = initBattleManager();
        createTest(manager, null, () -> {
            setArmy(manager, 0, 1, 1, 1, 0, 1);
        }, -1);
    }

    @Override
    public final Object createTest(BattleManager manager, Probe.Params params) {
        PolyIteratorArmy polyIteratorArmy = PolyCombinator.createIteratorArmy(manager);
        polyIteratorArmy.getBallisticProbe().getZoneProbe().probe(null);
        CreatingCombination best = polyIteratorArmy.findCombination(manager);
        logger.info("Best: " + best);
        logger.info(manager.getBattleField().toString());
        return -1;
    }
}
