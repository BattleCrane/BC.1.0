package TestPolyBot.probes;

import TestPolyBot.TestInitializer;
import TestPolyBot.TestSettings;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import polytech.polyNexus.PolyNexus;
import polytech.polyNexus.probes.PolyZoneProbe;
import polytech.polyNexus.probes.parametres.ParentParams;

import java.util.Set;
import java.util.logging.Logger;

//Worked!
public final class ProbeZoneTest implements TestInitializer {
    private final Logger logger = Logger.getLogger(ProbeZoneTest.class.getName());

    @Test
    public final void probeDangerousZone() {
        BattleManager battleManager = initBattleManager();
        createTest(battleManager,null, () -> setUnities(battleManager), "");
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

    @NotNull
    @Override
    public final Object createTest(BattleManager battleManager, ParentParams parentParams) {
        PolyZoneProbe zoneProbe = PolyNexus.createZoneProbe(battleManager);
        zoneProbe.probe(null);
        Set<Point> listOfDangerousZone = zoneProbe.getDangerousZone();
        markTerritory(battleManager, listOfDangerousZone);
        return "";
    }
}
