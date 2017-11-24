package TestPolyBot;

import Adjutants.AdjutantFielder;
import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Players.Player;
import PolyBot.Statistics.PolyProbe;
import Unities.Unity;
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
        Unity unityTank = battleManagerTest.getTank();
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointTest1, unityGunner);
        battleManagerTest.putUnity(battleManagerTest.getPlayerRed(), new Point(7, 8), unityTank);
        battleManagerTest.getBattleField().toString();
        PolyProbe polyProbe = new PolyProbe();
        polyProbe.setListDangerousZone(polyProbe.probeDangerousZone(battleManagerTest));
        PriorityUnit priorityGunner = polyProbe.probeBallisticUnit(battleManagerTest, unityGunner, pointTest1);
        PriorityUnit priorityTank = polyProbe.probeBallisticUnit(battleManagerTest, unityTank, new Point (7,8));
        //Рассматривается точка относительно синего игрока:
        assertTrue(-25.0 == priorityGunner.getPriority());
        assertTrue(149.0 == priorityTank.getPriority()); //Танк сам не попадает в опасную зону,  издесь всё верно
    }

    @Test
    public void probeRadiusUnitTest(){
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.initializeField();
        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.putUnity(battleManager.getPlayerBlue(), new Point (7,7), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerBlue(), new Point (4,5), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerBlue(), new Point(2,2), battleManager.getTurret());
        battleManager.getBattleField().toString();
        PolyProbe polyProbe = new PolyProbe();
        PriorityUnit priorityTurretTest1 = polyProbe.probeRadiusUnitTest(battleManager, battleManager.getTurret(), new Point(7,7));
        PriorityUnit priorityTurretTest2 = polyProbe.probeRadiusUnitTest(battleManager, battleManager.getTurret(), new Point(4, 5));
        PriorityUnit priorityTurretTest3 = polyProbe.probeRadiusUnitTest(battleManager, battleManager.getTurret(), new Point (2, 2));
        assertTrue(37.5 == priorityTurretTest1.getPriority());
        assertTrue(57.5 == priorityTurretTest2.getPriority());
        assertTrue(252.5 == priorityTurretTest3.getPriority());
    }

    @Test
    public void probeForLockTest(){
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.initializeField();
        Player playerBlue = battleManager.getPlayerBlue();
        battleManager.setPlayer(playerBlue);
        battleManager.putUnity(playerBlue, new Point(12, 9), battleManager.getGenerator());
        battleManager.putUnity(playerBlue, new Point(12, 7), battleManager.getGenerator());
        battleManager.getBattleField().toString();
        AdjutantFielder adjutantFielder = new AdjutantFielder();
        PolyProbe polyProbe = new PolyProbe();
        adjutantFielder.fillZones(battleManager);
        int valueOfFactory = polyProbe.probeForLockTest(battleManager, battleManager.getFactory(), new Point(14, 5));
        int valueOfBarracks = polyProbe.probeForLockTest(battleManager, battleManager.getBarracks(), new Point(12, 12));
        int valueOfTurret = polyProbe.probeForLockTest(battleManager, battleManager.getTurret(), new Point(11, 10));
        assertTrue(180 == valueOfFactory);
        assertTrue(-60 == valueOfBarracks);
        assertTrue(0 == valueOfTurret);
    }


    @Test
    public void probeBuildingUnitTest(){
        BattleManager battleManagerTest  = new BattleManager(new BattleField());
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.initializeField();
        battleManagerTest.putUnity(battleManagerTest.getPlayerRed(), new Point(0, 15), battleManagerTest.getTank());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(12, 9), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(9, 12), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(7, 12), battleManagerTest.getGenerator());
        battleManagerTest.getBattleField().toString();
        AdjutantFielder adjutantFielder = new AdjutantFielder();
        adjutantFielder.fillZones(battleManagerTest);
        PolyProbe polyProbe = new PolyProbe();
        polyProbe.setListDangerousZone(polyProbe.probeDangerousZone(battleManagerTest));
        PriorityUnit priorityGenerator = polyProbe.probeBuildingTest(battleManagerTest, battleManagerTest.getGenerator(), new Point(12, 9));
        PriorityUnit priorityBarracks = polyProbe.probeBuildingTest(battleManagerTest, battleManagerTest.getBarracks(), new Point (7, 14));
        PriorityUnit priorityWall = polyProbe.probeBuildingTest(battleManagerTest, battleManagerTest.getWall(), new Point(7, 14));
        assertTrue(300.0 == priorityGenerator.getPriority());
        assertTrue(30.0 == priorityBarracks.getPriority());
        assertTrue(180.0 == priorityWall.getPriority());
    }

    @Test
    public void probeUpgradeTest(){
        BattleManager battleManagerTest  = new BattleManager(new BattleField());
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.initializeField();
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(9, 9), battleManagerTest.getGenerator());
        PolyProbe polyProbe = new PolyProbe();
        PriorityUnit priorityGenerator = polyProbe.probeUpgradeTest(battleManagerTest, battleManagerTest.getGenerator(), new Point (9,9));
        PriorityUnit priorityEmpty = polyProbe.probeUpgradeTest(battleManagerTest, battleManagerTest.getGenerator(), new Point (10,10));
        assertTrue(300.0 == priorityGenerator.getPriority());
        assertTrue(0.0 == priorityEmpty.getPriority());
        battleManagerTest.getBattleField().toString();
    }
}
