package TestPolyBot.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.probes.Probe;
import TestPolyBot.TestInitializer;
import org.junit.Test;
import polytech.polyNexus.probes.PolyDistanceProbe;
import polytech.polyNexus.probes.parametres.Params;

import static org.junit.Assert.assertTrue;

public class DistanceProbeTest {
    @Test
    public void findClosestEnemy() {
        BattleManager battleManagerTest1 = TestInitializer.initBattleManager();
        Point pointSpawnTest1 = new Point(8, 5);
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), pointSpawnTest1, battleManagerTest1.getGunner());
        PolyDistanceProbe probe = new PolyDistanceProbe(battleManagerTest1);
        Params distanceParams = new PolyDistanceProbe
                .DistanceParams(1, 1, pointSpawnTest1);

        assertTrue(4.0 == (Double) probe.probe(distanceParams));

        BattleManager battleManagerTest2 = TestInitializer.initBattleManager();
        Point pointSpawnTest2 = new Point(12, 12);
        battleManagerTest2.putUnity(battleManagerTest2.getPlayer(), pointSpawnTest2, battleManagerTest2.getTank());
        Probe probe2 = new PolyDistanceProbe(battleManagerTest2);

        Params distanceParams2 = new PolyDistanceProbe.DistanceParams(1, 1,pointSpawnTest2);
        Params distanceParams3 = new PolyDistanceProbe.DistanceParams(2, 1, new Point(14, 7));
        assertTrue(8 == (Double) probe2.probe(distanceParams2));
        System.out.println(probe2.probe(distanceParams3));
    }
}
