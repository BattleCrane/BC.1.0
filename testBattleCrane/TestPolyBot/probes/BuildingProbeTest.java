package TestPolyBot.probes;

import TestPolyBot.TestSettings;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import game.players.Player;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.PolyBuildingProbe;
import polytech.polyNexus.probes.PolyDistanceProbe;
import polytech.polyNexus.probes.PolyZoneProbe;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public final class BuildingProbeTest {
    private final Logger logger = Logger.getLogger(BuildingProbeTest.class.getName());

    @Test //Worked!
    public final void probeLockTest(){
        BattleManager manager = TestInitializer.initBattleManager();
        Player playerBlue = manager.getPlayerBlue();

        manager.putUnity(playerBlue, new Point(12, 9), manager.getGenerator());
        manager.putUnity(playerBlue, new Point(12, 7), manager.getGenerator());

        PolyZoneProbe zoneProbe = new PolyZoneProbe(manager);
        zoneProbe.probe(null);

        PolyBuildingProbe probe = new PolyBuildingProbe(manager,  new Priorities()
                , zoneProbe, new PolyDistanceProbe(manager));

        int valueOfFactory = probe.probeLock(manager.getFactory(), new Point(14, 5));
        int valueOfBarracks = probe.probeLock(manager.getTurret(), new Point(11, 10));
        int valueOfTurret =  probe.probeLock(manager.getBarracks(), new Point(12, 12));

        logger.info(manager.getBattleField().toString());
        logger.info("" + valueOfFactory);
        logger.info("" + valueOfBarracks);
        logger.info("" + valueOfTurret);

        assertTrue(180 == valueOfFactory);
        assertTrue(0 == valueOfBarracks);
        assertTrue(-60 == valueOfTurret);
    }

    @Test //Worked!
    public final void probeBuildingUnitTest(){
        BattleManager manager  = TestInitializer.initBattleManager();
        createTest(manager, () -> {
            manager.putUnity(manager.getPlayerBlue(), new Point(12, 9), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(9, 12), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(7, 12), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(5, 10), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(14, 9), manager.getBarracks());
        }, new PolyBuildingProbe.Params(manager.getBarracks(), new Point(15, 8)), 345);

        BattleManager manager2 = TestInitializer.initBattleManager();
        createTest(manager2, new PolyBuildingProbe.Params(manager2.getWall(), new Point(5, 10)), 300);

        BattleManager manager3 = TestInitializer.initBattleManager();
        createTest(manager3, () -> manager3.putUnity(manager3.getOpponentPlayer(), new Point (8, 12)
                , manager3.getTank()), new PolyBuildingProbe.Params(manager3.getGenerator()
                , new Point(8,8)), -210);
    }

    private void createTest(BattleManager manager, TestSettings testSettings, PolyBuildingProbe.Params params
            , double expectResult){
        testSettings.setup();
        createTest(manager, params, expectResult);
    }

    private void createTest(BattleManager manager, PolyBuildingProbe.Params params, double expectResult){
        logger.info(manager.getBattleField().toString());
        PolyZoneProbe zoneProbe = new PolyZoneProbe(manager);
        PolyBuildingProbe probe = new PolyBuildingProbe(manager, new Priorities()
                , zoneProbe, new PolyDistanceProbe(manager));
        zoneProbe.probe(null);
        PriorityUnit priorityUnit = (PriorityUnit) probe.probe(params);
        logger.info("" + priorityUnit.getPriority());
        assertTrue(expectResult == priorityUnit.getPriority());
    }
}
