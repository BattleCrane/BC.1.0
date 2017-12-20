package TestPolyBot.probes;

import TestPolyBot.TestSettings;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.polyNexus.probes.PolyDistanceProbe;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

//Worked!
public final class DistanceProbeTest {
    private final Logger logger = Logger.getLogger(DistanceProbeTest.class.getName());

    @Test
    public final void findClosestEnemy() {
        BattleManager battleManagerTest = TestInitializer.initBattleManager();
        createTest(battleManagerTest, new PolyDistanceProbe.Params(battleManagerTest.getGunner()
                , new Point(9, 11)), 7);

        BattleManager battleManagerTest2 = TestInitializer.initBattleManager();
        createTest(battleManagerTest2,  new PolyDistanceProbe.Params(battleManagerTest.getTank()
                , new Point(12, 12)), 8);

        BattleManager battleManagerTest3 = TestInitializer.initBattleManager();
        createTest(battleManagerTest3,  new PolyDistanceProbe.Params(battleManagerTest3.getTurret()
                , new Point (7, 14)), 10);

        BattleManager battleManagerTest4 = TestInitializer.initBattleManager();
        createTest(battleManagerTest4, new PolyDistanceProbe.Params(battleManagerTest4.getGenerator()
                , new Point(8,8)), 4);
    }

    private void createTest(BattleManager battleManagerTest, PolyDistanceProbe.Params params, int expectedValue) {
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), params.getPoint(), params.getUnity());
        logger.info(battleManagerTest.getBattleField().toString());
        PolyDistanceProbe probe = new PolyDistanceProbe(battleManagerTest);
        int value = (Integer) probe.probe(params);
        logger.info("" + value);

        assertTrue(expectedValue == value);
    }

    private void createTest(BattleManager battleManagerTest, PolyDistanceProbe.Params params
            , TestSettings testSettings, int expectedValue) {
        testSettings.setup();
        createTest(battleManagerTest, params,  expectedValue);
    }
}
