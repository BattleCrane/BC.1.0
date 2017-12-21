package TestPolyBot.probes;

import botInterface.probes.Probe;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.polyNexus.PolyNexus;
import polytech.polyNexus.probes.PolyDistanceProbe;
import polytech.polyNexus.probes.parametres.ParentParams;

import java.util.logging.Logger;

//Worked!
public final class DistanceProbeTest implements TestInitializer {
    private final Logger logger = Logger.getLogger(DistanceProbeTest.class.getName());

    @Test
    public final void findClosestEnemy() {
        BattleManager battleManagerTest = initBattleManager();
        createTest(battleManagerTest, new PolyDistanceProbe.Params(battleManagerTest.getGunner()
                , new Point(9, 11)), 7);

        BattleManager battleManagerTest2 = initBattleManager();
        createTest(battleManagerTest2,  new PolyDistanceProbe.Params(battleManagerTest.getTank()
                , new Point(12, 12)), 8);

        BattleManager battleManagerTest3 = initBattleManager();
        createTest(battleManagerTest3,  new PolyDistanceProbe.Params(battleManagerTest3.getTurret()
                , new Point (7, 14)), 10);

        BattleManager battleManagerTest4 = initBattleManager();
        createTest(battleManagerTest4, new PolyDistanceProbe.Params(battleManagerTest4.getGenerator()
                , new Point(8,8)), 4);
    }

    @Override
    public final Object createTest(BattleManager battleManager, ParentParams params) {
        Probe probe = PolyNexus.createDistanceProbe(battleManager);
        int distance = (Integer) probe.probe(params);
        logger.info(battleManager.getBattleField().toString());
        logger.info("" + distance);
        return distance;
    }
}
