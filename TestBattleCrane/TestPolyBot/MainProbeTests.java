package TestPolyBot;

import Adjutants.AdjutantFielder;
import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Players.Player;
import polytech.polyNexus.Probes.PolyMainProbe;
import Unities.Unity;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainProbeTests {
    @Test
    public void findClosestEnemy() {
        BattleManager battleManagerTest1 = new BattleManager(new BattleField());
        battleManagerTest1.initializeField();
        battleManagerTest1.setPlayer(battleManagerTest1.getPlayerBlue());
        battleManagerTest1.setOpponentPlayer(battleManagerTest1.getPlayerRed());
        Point pointSpawnTest1 = new Point(8, 5);
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), pointSpawnTest1, battleManagerTest1.getGunner());
        assertTrue(4.0 == new PolyMainProbe().findClosestEnemyTest(battleManagerTest1, pointSpawnTest1, 1, 1));

        BattleManager battleManagerTest2 = new BattleManager(new BattleField());
        battleManagerTest2.initializeField();
        battleManagerTest2.setPlayer(battleManagerTest2.getPlayerBlue());
        battleManagerTest2.setOpponentPlayer(battleManagerTest2.getPlayerRed());
        Point pointSpawnTest2 = new Point(12, 12);
        battleManagerTest2.putUnity(battleManagerTest2.getPlayer(), pointSpawnTest2, battleManagerTest2.getTank());
        assertTrue(8 == new PolyMainProbe().findClosestEnemyTest(battleManagerTest2, pointSpawnTest2, 1, 1));
        double a = new PolyMainProbe().findClosestEnemyTest(battleManagerTest2, new Point(14, 7), 2, 1);
        System.out.println(a);
        assertTrue(10 == new PolyMainProbe().findClosestEnemyTest(battleManagerTest2, new Point(14, 7), 2, 1));
        assertTrue(10 == new PolyMainProbe().findClosestEnemyTest(battleManagerTest2, new Point(7, 14), 2, 1));
        System.out.println(new PolyMainProbe().findClosestEnemyTest(battleManagerTest2, new Point(7, 14), 2,  1 ));
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
        assertTrue(165.0 == new PolyMainProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest1));
        assertTrue(305.0 == new PolyMainProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest2));
        assertTrue(10.0 == new PolyMainProbe().collectValOfBallisticUnitTest(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest3));
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
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        polyMainProbe.setListDangerousZone(polyMainProbe.probeDangerousZone(battleManagerTest));
        PriorityUnit priorityGunner = polyMainProbe.probeBallisticUnit(battleManagerTest, unityGunner, pointTest1);
        PriorityUnit priorityTank = polyMainProbe.probeBallisticUnit(battleManagerTest, unityTank, new Point (7,8));
        //Рассматривается точка относительно синего игрока:
        assertTrue(-25.0 == priorityGunner.getPriority());
        System.out.println(priorityTank.getPriority());
        //assertTrue(149.0 == priorityTank.getPriority()); //Танк сам не попадает в опасную зону,  издесь всё верно
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
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        PriorityUnit priorityTurretTest1 = polyMainProbe.probeRadiusUnitTest(battleManager, battleManager.getTurret(), new Point(7,7));
        PriorityUnit priorityTurretTest2 = polyMainProbe.probeRadiusUnitTest(battleManager, battleManager.getTurret(), new Point(4, 5));
        PriorityUnit priorityTurretTest3 = polyMainProbe.probeRadiusUnitTest(battleManager, battleManager.getTurret(), new Point (2, 2));
        System.out.println(priorityTurretTest1.getPriority());
        System.out.println(priorityTurretTest2.getPriority());
        System.out.println(priorityTurretTest3.getPriority());

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
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        adjutantFielder.fillZones(battleManager);
        int valueOfFactory = polyMainProbe.probeForLockTest(battleManager, battleManager.getFactory(), new Point(14, 5));
        int valueOfBarracks = polyMainProbe.probeForLockTest(battleManager, battleManager.getBarracks(), new Point(12, 12));
        int valueOfTurret = polyMainProbe.probeForLockTest(battleManager, battleManager.getTurret(), new Point(11, 10));
        assertTrue(180 == valueOfFactory);
        assertTrue(-60 == valueOfBarracks);
        assertTrue(0 == valueOfTurret);
    }


    @Test
    public void probeBuildingUnitTest(){
        BattleManager battleManagerTest  = new BattleManager(new BattleField());
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.setOpponentPlayer(battleManagerTest.getPlayerRed());
        battleManagerTest.initializeField();
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(12, 9), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(9, 12), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(7, 12), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(5, 10), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(14, 9), battleManagerTest.getBarracks());
        battleManagerTest.getBattleField().toString();
        AdjutantFielder adjutantFielder = new AdjutantFielder();
        adjutantFielder.fillZones(battleManagerTest);
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        polyMainProbe.setListDangerousZone(polyMainProbe.probeDangerousZone(battleManagerTest));
//        PriorityUnit priorityGenerator = polyMainProbe.probeBuildingTest(battleManagerTest, battleManagerTest.getGenerator(), new Point(12, 9));
//        PriorityUnit priorityBarracks = new PolyMainProbe().probeBuildingTest(battleManagerTest, battleManagerTest.getBarracks(), new Point (7, 14));
//        PriorityUnit priorityWall = polyMainProbe.probeBuildingTest(battleManagerTest, battleManagerTest.getWall(), new Point(7, 14));

//        assertTrue(540.0 == priorityGenerator.getPriority());
//        assertTrue(480.0 == priorityBarracks.getPriority());
//        assertTrue(200.0 == priorityWall.getPriority());
                PriorityUnit priorityBarracks2 = polyMainProbe.probeBuildingTest(battleManagerTest, battleManagerTest.getBarracks(), new Point(15, 8));
        System.out.println(priorityBarracks2.getPriority());

    }

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

    @Test
    public void probeAccommodationOfUnisTest(){
        BattleManager battleManagerTest  = new BattleManager(new BattleField());
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.setOpponentPlayer(battleManagerTest.getPlayerRed());
        battleManagerTest.initializeField();
        new AdjutantFielder().fillZones(battleManagerTest);
        battleManagerTest.getBattleField().toString();
        battleManagerTest.setHowICanBuild(3);
        battleManagerTest.setConstructedGenerator(false);
        battleManagerTest.setHowICanBuildFactories(1);
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        List<PriorityUnit> priorityUnitsList = polyMainProbe.probeAccommodationOfUnitsTest(battleManagerTest);
        System.out.println(priorityUnitsList.size());
        System.out.println(priorityUnitsList.toString());
        battleManagerTest.getBattleField().toString();
    }


}
