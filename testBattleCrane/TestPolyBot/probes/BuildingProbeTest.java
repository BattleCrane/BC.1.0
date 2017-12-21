package TestPolyBot.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import game.players.Player;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.polyNexus.PolyNexus;
import polytech.polyNexus.probes.parametres.ParentParams;
import polytech.polyNexus.probes.PolyBuildingProbe;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public final class BuildingProbeTest implements TestInitializer{
    private final Logger logger = Logger.getLogger(BuildingProbeTest.class.getName());

    @Test //Worked!
    public final void probeLockTest(){
        BattleManager manager = initBattleManager();
        Player playerBlue = manager.getPlayerBlue();

        manager.putUnity(playerBlue, new Point(12, 9), manager.getGenerator());
        manager.putUnity(playerBlue, new Point(12, 7), manager.getGenerator());

        PolyBuildingProbe probe = PolyNexus.createBuildingProbe(manager);
        probe.getZoneProbe().probe(null);

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
        BattleManager manager  = initBattleManager();
        createTest(manager, new PolyBuildingProbe.Params(manager.getBarracks(), new Point(15, 8)), () -> {
            manager.putUnity(manager.getPlayerBlue(), new Point(12, 9), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(9, 12), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(7, 12), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(5, 10), manager.getGenerator());
            manager.putUnity(manager.getPlayerBlue(), new Point(14, 9), manager.getBarracks());
        }, 345.0);

        BattleManager manager2 = initBattleManager();
        createTest(manager2, new PolyBuildingProbe.Params(manager2.getWall(), new Point(5, 10)), 300.0);

        BattleManager manager3 = initBattleManager();
        createTest(manager3, new PolyBuildingProbe.Params(manager3.getGenerator()
                , new Point(8,8)),() -> manager3.putUnity(manager3.getOpponentPlayer(), new Point (8, 12)
                , manager3.getTank()),  -210.0);
    }

    @Override
    public Object createTest(BattleManager manager, ParentParams params) {
        PolyBuildingProbe probe = PolyNexus.createBuildingProbe(manager);
        probe.getZoneProbe().probe(null);
        PriorityUnit priorityUnit = (PriorityUnit) probe.probe(params);
        double result = priorityUnit.getPriority();
        logger.info(manager.getBattleField().toString());
        logger.info("" + result);
        return result;
    }
}
