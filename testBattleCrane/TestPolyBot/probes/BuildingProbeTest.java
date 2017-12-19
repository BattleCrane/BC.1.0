package TestPolyBot.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import game.players.Player;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.PolyBuildingProbe;
import polytech.polyNexus.probes.PolyDistanceProbe;
import polytech.polyNexus.probes.PolyZoneProbe;
import polytech.polyNexus.probes.parametres.Params;

import static org.junit.Assert.assertTrue;

public class BuildingProbeTest {
    @Test
    public void probeLockTest(){
        BattleManager battleManager = TestInitializer.initBattleManager();
        Player playerBlue = battleManager.getPlayerBlue();

        battleManager.putUnity(playerBlue, new Point(12, 9), battleManager.getGenerator());
        battleManager.putUnity(playerBlue, new Point(12, 7), battleManager.getGenerator());
        battleManager.getBattleField().toString();

        PolyZoneProbe zoneProbe = new PolyZoneProbe(battleManager);
        PolyBuildingProbe probe = new PolyBuildingProbe(battleManager,  new Priorities()
                , zoneProbe, new PolyDistanceProbe(battleManager));

        zoneProbe.probe(null);

        int valueOfFactory = probe.probeLock(battleManager.getFactory(), new Point(14, 5));
        int valueOfBarracks = probe.probeLock(battleManager.getTurret(), new Point(11, 10));
        int valueOfTurret =  probe.probeLock(battleManager.getBarracks(), new Point(12, 12));

        assertTrue(180 == valueOfFactory);
        assertTrue(-60 == valueOfBarracks);
        assertTrue(0 == valueOfTurret);
    }

    @Test
    public void probeBuildingUnitTest(){
        BattleManager battleManagerTest  = TestInitializer.initBattleManager();

        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(12, 9), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(9, 12), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(7, 12), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(5, 10), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(14, 9), battleManagerTest.getBarracks());
        battleManagerTest.getBattleField().toString();

        PolyZoneProbe zoneProbe = new PolyZoneProbe(battleManagerTest);

        PolyBuildingProbe probe = new PolyBuildingProbe(battleManagerTest, new Priorities()
                , zoneProbe, new PolyDistanceProbe(battleManagerTest));
        zoneProbe.probe(null);

        Params params = new PolyBuildingProbe.BuildingParams(battleManagerTest.getBarracks(), new Point(15, 8));

        PriorityUnit priorityBarracks2 = (PriorityUnit) probe.probe(params);
        System.out.println(priorityBarracks2.getPriority());

    }
}
