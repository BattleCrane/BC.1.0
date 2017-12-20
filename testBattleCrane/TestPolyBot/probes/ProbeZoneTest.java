package TestPolyBot.probes;

import TestPolyBot.TestInitializer;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import org.junit.Test;
import polytech.polyNexus.probes.PolyZoneProbe;

import java.util.Set;
import java.util.logging.Logger;

//Worked!
public final class ProbeZoneTest {
    private final Logger logger = Logger.getLogger(ProbeZoneTest.class.getName());

    @Test
    public final void probeDangerousZone() {
        BattleManager battleManager = TestInitializer.initBattleManager();
        setUnities(battleManager);
        PolyZoneProbe zoneProbe = new PolyZoneProbe(battleManager);
        zoneProbe.probe(null);
        Set<Point> listOfDangerousZone = zoneProbe.getDangerousZone();
        markTerritory(battleManager, listOfDangerousZone);
    }

    private void setUnities(BattleManager battleManager){
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(12, 10), battleManager.getGunner());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(7, 0), battleManager.getTank());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(9, 3), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(0, 15), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(7, 8), battleManager.getTank());
    }

    private void markTerritory(BattleManager battleManager, Set<Point> listOfDangerousZone){
        for (Point point : listOfDangerousZone) {
            battleManager.getBattleField().getMatrix().get(point.Y()).set(point.X(), "XXXXXX");
        }
        logger.info(battleManager.getBattleField().toString());
    }
}
