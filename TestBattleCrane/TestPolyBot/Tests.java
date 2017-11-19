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
        BattleManager battleManagerTest1 = new BattleManager(new BattleField());
        battleManagerTest1.initializeField();
        battleManagerTest1.setPlayer(battleManagerTest1.getPlayerBlue());
        Point pointSpawnTest1 = new Point(7, 7);
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), pointSpawnTest1, battleManagerTest1.getGunner());

        assertTrue(165.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest1.getPlayer(), battleManagerTest1.getBattleField().getMatrix(),
                -1, -1, pointSpawnTest1, 0));


        BattleManager battleManagerTest2 = new BattleManager(new BattleField());
        battleManagerTest2.initializeField();
        battleManagerTest2.setPlayer(battleManagerTest2.getPlayerBlue());
        Point pointSpawnTest2 = new Point(2, 2);
        battleManagerTest2.putUnity(battleManagerTest2.getPlayer(), pointSpawnTest2, battleManagerTest2.getTank());
        assertTrue(5.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest2.getPlayer(), battleManagerTest2.getBattleField().getMatrix(),
                0, 1, pointSpawnTest2, 0));
        assertTrue(280.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest2.getPlayer(), battleManagerTest2.getBattleField().getMatrix(),
                -1, -1, pointSpawnTest2, 0));
        assertTrue(5.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest2.getPlayer(), battleManagerTest2.getBattleField().getMatrix(),
                1, 0, pointSpawnTest2, 0));
        assertTrue(5.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest2.getPlayer(), battleManagerTest2.getBattleField().getMatrix(),
                1, 0, pointSpawnTest2, 0));
        assertTrue(0.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest2.getPlayer(), battleManagerTest2.getBattleField().getMatrix(),
                0, -1, pointSpawnTest2, 0));
        assertTrue(0.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest2.getPlayer(), battleManagerTest2.getBattleField().getMatrix(),
                -1, 0, pointSpawnTest2, 0));
        assertTrue(5.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest2.getPlayer(), battleManagerTest2.getBattleField().getMatrix(),
                1, 1, pointSpawnTest2, 0));
    }
}
