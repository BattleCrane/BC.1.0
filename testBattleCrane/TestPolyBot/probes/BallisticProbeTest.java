package TestPolyBot.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import TestPolyBot.TestInitializer;
import game.unities.Unity;
import org.junit.Test;
import polytech.polyNexus.PolyNexus;
import polytech.polyNexus.probes.PolyBallisticProbe;
import polytech.polyNexus.probes.parametres.ParentParams;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class BallisticProbeTest implements TestInitializer {
    private final Logger logger = Logger.getLogger(BallisticProbeTest.class.getName());

    @Test
    public void collect() {
        BattleManager battleManagerTest = initBattleManager();

        Point pointSpawnTest1 = new Point(7, 7);
        Point pointSpawnTest2 = new Point(7, 3);
        Point pointSpawnTest3 = new Point(2, 2);

        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest1, battleManagerTest.getGunner());
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest2, battleManagerTest.getGunner());
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest3, battleManagerTest.getTank());

        PolyBallisticProbe probe = PolyNexus.createBallisticProbe(battleManagerTest);

        double result1 = probe.collect(battleManagerTest.getPlayer()
                , battleManagerTest.getBattleField().getMatrix(), pointSpawnTest1);
        double result2 = probe.collect(battleManagerTest.getPlayer()
                , battleManagerTest.getBattleField().getMatrix(), pointSpawnTest2);
        double result3 = probe.collect(battleManagerTest.getPlayer()
                , battleManagerTest.getBattleField().getMatrix(), pointSpawnTest3);

        logger.info(battleManagerTest.getBattleField().toString());
        logger.info(ANSI_CYAN + "Result_1: " + result1 );
        logger.info("Result_2: " + result2);
        logger.info("Result_3: " + result3);
        assertTrue(275.0 == result1);
        assertTrue(150.0 == result2);
        assertTrue(725.0 == result3);
    }

    @Test
    public void probeBallisticUnit() {
        BattleManager manager = initBattleManager();

        Unity unityGunner = manager.getGunner();
        Unity unityTank = manager.getTank();

        Point point1 = new Point(7, 3);
        createTest(manager, new PolyBallisticProbe.Params(unityGunner, point1), () -> {
            manager.putUnity(manager.getPlayer(), point1, unityGunner);
            manager.putUnity(manager.getPlayerRed(), new Point(7, 8), unityTank);
            manager.putUnity(manager.getPlayer(),  new Point(0, 7), unityGunner);
        }, 130.0);
    }

    @Override
    public Object createTest(BattleManager manager, ParentParams params) {
        PolyBallisticProbe probe = PolyNexus.createBallisticProbe(manager);
        probe.getZoneProbe().probe(null);
        PriorityUnit priorityUnit = (PriorityUnit) probe.probe(params);
        double result = priorityUnit.getPriority();
        logger.info(manager.getBattleField().toString());
        logger.info("" + result);
        return result;
    }
}
