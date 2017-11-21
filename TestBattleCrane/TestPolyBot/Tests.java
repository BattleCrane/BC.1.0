package TestPolyBot;

import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Bots.Priority.TemplatePriorityUnit;
import PolyBot.Statistics.PolyProbe;
import Unities.Unity;
import org.junit.Test;

import java.util.List;

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
        Point pointSpawnTest3 = new Point(7,3);
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest1, battleManagerTest.getGunner());
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest3, battleManagerTest.getGunner());
        Point pointSpawnTest2 = new Point(2, 2);

        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest2, battleManagerTest.getTank());
        battleManagerTest.getBattleField().toString();
        assertTrue(165.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest1));


        assertTrue(305.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest2));
        assertTrue(10.0 == new PolyProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest3));
    }

    @Test
    public void probeBallisticUnit(){
        BattleManager battleManagerTest  = new BattleManager(new BattleField());
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.initializeField();
        Point pointTest1 = new Point(7, 3);
        Unity unityGunner = battleManagerTest.getGunner();
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointTest1, unityGunner);
        battleManagerTest.putUnity(battleManagerTest.getPlayerRed(), new Point(7, 8), battleManagerTest.getTank());
        battleManagerTest.getBattleField().toString();
        PolyProbe polyProbe = new PolyProbe();
        polyProbe.setListDangerousZone(polyProbe.probeDangerousZone(battleManagerTest));
        PriorityUnit priorityGunner = polyProbe.probeBallisticUnit(battleManagerTest, unityGunner, pointTest1);

        System.out.println(priorityGunner.getPriority());


    }
}
