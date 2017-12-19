package TestPolyBot.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import TestPolyBot.TestInitializer;
import game.unities.Unity;
import org.junit.Test;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.PolyBallisticProbe;
import polytech.polyNexus.probes.PolyDistanceProbe;
import polytech.polyNexus.probes.PolyZoneProbe;
import polytech.polyNexus.probes.parametres.Params;

import static org.junit.Assert.assertTrue;

public class BallisticAndRadiusProbesTest {
    @Test
    public void collect() {
        BattleManager battleManagerTest = TestInitializer.initBattleManager();

        Point pointSpawnTest1 = new Point(7, 7);
        Point pointSpawnTest3 = new Point(7,3);
        Point pointSpawnTest2 = new Point(2, 2);

        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest1, battleManagerTest.getGunner());
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest3, battleManagerTest.getGunner());
        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointSpawnTest2, battleManagerTest.getTank());
        battleManagerTest.getBattleField().toString();

        PolyBallisticProbe probe = new PolyBallisticProbe(battleManagerTest,  new Priorities()
        , new PolyZoneProbe(battleManagerTest), new PolyDistanceProbe(battleManagerTest));

        assertTrue(165.0 == probe.collect(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest1));
        assertTrue(305.0 == probe.collect(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest2));
        assertTrue(10.0 == probe.collect(battleManagerTest.getPlayer(), battleManagerTest.getBattleField().getMatrix(), pointSpawnTest3));
    }

    @Test
    public void probeBallisticUnit(){
        BattleManager battleManagerTest  = TestInitializer.initBattleManager();
        Point pointTest1 = new Point(7, 3);

        Unity unityGunner = battleManagerTest.getGunner();
        Unity unityTank = battleManagerTest.getTank();

        battleManagerTest.putUnity(battleManagerTest.getPlayer(), pointTest1, unityGunner);
        battleManagerTest.putUnity(battleManagerTest.getPlayerRed(), new Point(7, 8), unityTank);
        battleManagerTest.getBattleField().toString();

        PolyZoneProbe zoneProbe = new PolyZoneProbe(battleManagerTest);
        PolyBallisticProbe probe = new PolyBallisticProbe(battleManagerTest,  new Priorities()
                , zoneProbe, new PolyDistanceProbe(battleManagerTest));
        zoneProbe.probe(null);

        Params paramsGunner = new PolyBallisticProbe.BallisticParams(unityGunner, pointTest1);
        PriorityUnit priorityGunner = (PriorityUnit) probe.probe(paramsGunner);

        Params paramsTank = new PolyBallisticProbe.BallisticParams(unityTank, new Point (7,8));
        PriorityUnit priorityTank = (PriorityUnit) probe.probe(paramsTank);
        //Рассматривается точка относительно синего игрока:
        assertTrue(-25.0 == priorityGunner.getPriority());
        System.out.println(priorityTank.getPriority());
    }

    @Test
    public void probeRadiusUnitTest(){
        BattleManager battleManager = TestInitializer.initBattleManager();

        battleManager.putUnity(battleManager.getPlayerBlue(), new Point (7,7), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerBlue(), new Point (4,5), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerBlue(), new Point(2,2), battleManager.getTurret());
        battleManager.getBattleField().toString();

        PolyZoneProbe zoneProbe = new PolyZoneProbe(battleManager);
        PolyBallisticProbe probe = new PolyBallisticProbe(battleManager,  new Priorities()
                , zoneProbe, new PolyDistanceProbe(battleManager));
        zoneProbe.probe(null);


        Params params1 = new PolyBallisticProbe.BallisticParams(battleManager.getTurret(), new Point(7,7));

        Params params2 = new PolyBallisticProbe.BallisticParams(battleManager.getTurret(), new Point(4, 5));

        Params params3 = new PolyBallisticProbe.BallisticParams(battleManager.getTurret(), new Point (2, 2));

        PriorityUnit priorityTurretTest1 = (PriorityUnit) probe.probe(params1);
        PriorityUnit priorityTurretTest2 = (PriorityUnit) probe.probe(params2);
        PriorityUnit priorityTurretTest3 = (PriorityUnit) probe.probe(params3);
        System.out.println(priorityTurretTest1.getPriority());
        System.out.println(priorityTurretTest2.getPriority());
        System.out.println(priorityTurretTest3.getPriority());

    }
}
