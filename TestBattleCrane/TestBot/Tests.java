package TestBot;

import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Probes.Probe;
import Bots.Probes.TemplateProbe;
import org.junit.Test;
import java.util.List;

public class Tests {
    @Test
    public void probeDangerousZone() {
        BattleManager battleManager = new BattleManager(new BattleField());
        Probe probe = new TemplateProbe();

//        battleManager.setPlayer(battleManager.getPlayerBlue());
//        battleManager.putUnity(battleManager.getPlayerRed(), new Point(12, 10), battleManager.getGunner());
//        battleManager.putUnity(battleManager.getPlayerRed(), new Point(7, 0), battleManager.getTank());
//        battleManager.putUnity(battleManager.getPlayerRed(), new Point(9, 3), battleManager.getTurret());
//        battleManager.putUnity(battleManager.getPlayerRed(), new Point(0, 15), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(7, 8), battleManager.getTank());
        battleManager.initializeField();
        List<Point> listOfDangerousZone = probe.probeDangerousZone(battleManager);
        for (Point point : listOfDangerousZone) {
            battleManager.getBattleField().getMatrix().get(point.Y()).set(point.X(), "XXXXXX");
        }
        battleManager.getBattleField().toString();
    }
}
