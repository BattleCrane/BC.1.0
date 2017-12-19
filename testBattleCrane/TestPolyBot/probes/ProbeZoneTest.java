package TestPolyBot.probes;

import game.battleFields.BattleField;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import org.junit.Test;
import polytech.polyNexus.probes.PolyZoneProbe;

import java.util.Set;

public class ProbeZoneTest {
    @Test
    public void probeDangerousZone() {
        BattleManager battleManager = new BattleManager(new BattleField());

        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(12, 10), battleManager.getGunner());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(7, 0), battleManager.getTank());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(9, 3), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(0, 15), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(7, 8), battleManager.getTank());
        battleManager.initializeField();
        PolyZoneProbe zoneProbe = new PolyZoneProbe(battleManager);
        zoneProbe.probe(null);
        Set<Point> listOfDangerousZone = zoneProbe.getDangerousZone();
        for (Point point : listOfDangerousZone) {
            battleManager.getBattleField().getMatrix().get(point.Y()).set(point.X(), "XXXXXX");
        }
        battleManager.getBattleField().toString();
    }
}
