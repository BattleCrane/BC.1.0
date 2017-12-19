package TestPolyBot;

import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UpgradingProbeTest {
    @Test
    public void probeUpgradeTest(){
        BattleManager battleManagerTest  = new BattleManager(new BattleField());
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.initializeField();
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(14, 9), battleManagerTest.getGenerator());
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        PriorityUnit priorityGenerator = polyMainProbe.probeUpgradeTest(battleManagerTest, battleManagerTest.getGenerator(), new Point (14,9));
        PriorityUnit priorityEmpty = polyMainProbe.probeUpgradeTest(battleManagerTest, battleManagerTest.getGenerator(), new Point (10,10));
        assertTrue(400.0 == priorityGenerator.getPriority());
        assertTrue(-10000.0 == priorityEmpty.getPriority());
        battleManagerTest.getBattleField().toString();
    }
}
