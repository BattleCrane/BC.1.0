package TestPolyBot.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.PolyUpgradingProbe;
import polytech.polyNexus.probes.parametres.ParentParams;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

//Worked!
public final class UpgradingProbeTest {
    private final Logger logger = Logger.getLogger(UpgradingProbeTest.class.getName());

    @Test
    public final void probeUpgradeTest(){
        BattleManager battleManagerTest  = TestInitializer.initBattleManager();
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue()
                , new Point(14, 9), battleManagerTest.getGenerator());
        PolyUpgradingProbe probe = new PolyUpgradingProbe(battleManagerTest, new Priorities());

        ParentParams params1 = new PolyUpgradingProbe.UpgradingParams(battleManagerTest.getGenerator(), new Point (14,9));
        ParentParams params2 = new PolyUpgradingProbe.UpgradingParams(battleManagerTest.getGenerator(), new Point (10,10));

        PriorityUnit priorityGenerator = (PriorityUnit) probe.probe(params1);
        PriorityUnit priorityEmpty = (PriorityUnit) probe.probe(params2);

        logger.info(battleManagerTest.getBattleField().toString());
        logger.info("" + priorityGenerator.getPriority());
        logger.info("" + priorityEmpty.getPriority());

        assertTrue(240.0 == priorityGenerator.getPriority());
        assertTrue(0.0 == priorityEmpty.getPriority());
    }
}
