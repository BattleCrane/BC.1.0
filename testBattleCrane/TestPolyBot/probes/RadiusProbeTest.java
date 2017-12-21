package TestPolyBot.probes;

import TestPolyBot.TestInitializer;
import botInterface.priority.PriorityUnit;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import org.junit.Test;
import polytech.polyNexus.PolyNexus;
import polytech.polyNexus.probes.PolyBallisticProbe;
import polytech.polyNexus.probes.PolyDistanceProbe;
import polytech.polyNexus.probes.PolyZoneProbe;
import polytech.polyNexus.probes.parametres.ParentParams;
import polytech.priority.Priorities;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class RadiusProbeTest implements TestInitializer{
    private final Logger logger = Logger.getLogger(RadiusProbeTest.class.getName());
    ConsoleHandler handler = new ConsoleHandler();

    @Test
    public void collect(){

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
        logger.info("Result_1: " + result1);
        logger.info("Result_2: " + result2);
        logger.info("Result_3: " + result3);

        assertTrue(275.0 == result1);
        assertTrue(150.0 == result2);
        assertTrue(725.0 == result3);
    }

    @Test
    public void probeRadiusUnitTest() {
        BattleManager battleManager = initBattleManager();
        createTest(battleManager, new PolyBallisticProbe.Params(battleManager.getTurret(), new Point(7, 7))
                , () -> {
                    battleManager.putUnity(battleManager.getPlayerBlue(), new Point(7, 7), battleManager.getTurret());
                    battleManager.putUnity(battleManager.getPlayerBlue(), new Point(4, 5), battleManager.getTurret());
                    battleManager.putUnity(battleManager.getPlayerBlue(), new Point(2, 2), battleManager.getTurret());
                }, 0);


        battleManager.getBattleField().toString();

        PolyZoneProbe zoneProbe = new PolyZoneProbe(battleManager);
        PolyBallisticProbe probe = new PolyBallisticProbe(battleManager, new Priorities()
                , zoneProbe, new PolyDistanceProbe(battleManager));
        zoneProbe.probe(null);


        ParentParams params1 = new PolyBallisticProbe.Params(battleManager.getTurret(), new Point(7, 7));

        ParentParams params2 = new PolyBallisticProbe.Params(battleManager.getTurret(), new Point(4, 5));

        ParentParams params3 = new PolyBallisticProbe.Params(battleManager.getTurret(), new Point(2, 2));

        PriorityUnit priorityTurretTest1 = (PriorityUnit) probe.probe(params1);
        PriorityUnit priorityTurretTest2 = (PriorityUnit) probe.probe(params2);
        PriorityUnit priorityTurretTest3 = (PriorityUnit) probe.probe(params3);
        System.out.println(priorityTurretTest1.getPriority());
        System.out.println(priorityTurretTest2.getPriority());
        System.out.println(priorityTurretTest3.getPriority());

    }


    @Override
    public final Object createTest(BattleManager battleManager, ParentParams parentParams) {
        return null;
    }
}
