package TestPolyBot.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.PolyUpgradingProbe;
import polytech.polyNexus.probes.parametres.Params;

import static org.junit.Assert.assertTrue;

public class UpgradingProbeTest {
    @Test
    public void probeUpgradeTest(){
        BattleManager battleManagerTest  = TestInitializer.initBattleManager();
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue()
                , new Point(14, 9), battleManagerTest.getGenerator());
        PolyUpgradingProbe probe = new PolyUpgradingProbe(battleManagerTest, new Priorities());

        Params params1 = new PolyUpgradingProbe.UpgradingParams(battleManagerTest.getGenerator(), new Point (14,9));
        Params params2 = new PolyUpgradingProbe.UpgradingParams(battleManagerTest.getGenerator(), new Point (10,10));

        PriorityUnit priorityGenerator = (PriorityUnit) probe.probe(params1);
        PriorityUnit priorityEmpty = (PriorityUnit) probe.probe(params2);

        assertTrue(400.0 == priorityGenerator.getPriority());
        assertTrue(-10000.0 == priorityEmpty.getPriority());
        battleManagerTest.getBattleField().toString();
    }
}
