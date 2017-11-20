package TestPolyBot;

import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import PolyBot.Statistics.PolyProbe;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Tests {
    @Test
    public void findClosestEnemy() {
        BattleManager battleManagerTest1 = new BattleManager(new BattleField());
        battleManagerTest1.initializeField();
        battleManagerTest1.setPlayer(battleManagerTest1.getPlayerBlue());
        Point pointSpawnTest1 = new Point(8, 5);
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), pointSpawnTest1, battleManagerTest1.getGunner());
        assertEquals(4, new PolyProbe().findClosestEnemyTest(battleManagerTest1, pointSpawnTest1, 1, 1));

        BattleManager battleManagerTest2 = new BattleManager(new BattleField());
        battleManagerTest2.initializeField();
        battleManagerTest2.setPlayer(battleManagerTest2.getPlayerBlue());
        Point pointSpawnTest2 = new Point(12, 12);
        battleManagerTest2.putUnity(battleManagerTest2.getPlayer(), pointSpawnTest2, battleManagerTest2.getTank());
        assertEquals(8, new PolyProbe().findClosestEnemyTest(battleManagerTest2, pointSpawnTest2, 1, 1));
    }

    @Test
    public void collectValOfBallisticUnits() {
        BattleManager battleManagerTest = new BattleManager(new BattleField());
        battleManagerTest.initializeField();
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        Point pointSpawnTest1 = new Point(7, 7);
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest1, battleManagerTest.getGunner());
        battleManagerTest.getBattleField().toString();
        assertTrue(165.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest1));

        Point pointSpawnTest2 = new Point(2, 2);
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest2, battleManagerTest.getTank());
        assertTrue(295.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest2));
    }
}
