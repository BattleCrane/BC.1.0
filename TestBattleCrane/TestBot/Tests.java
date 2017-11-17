package TestBot;

import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Statistics.Probe;
import Bots.Statistics.TemplateProbe;
import org.junit.Test;

import java.util.List;

public class Tests {
    @Test
    public void probeDangerousZone(){
        BattleManager battleManager = new BattleManager(new BattleField());
        Probe probe = new TemplateProbe();

        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(12, 10), battleManager.getGunner());
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(7, 0), battleManager.getTank());
        battleManager.initializeField();
        List<Point> listOfDangerousZone = probe.probeDangerousZone(battleManager);
        for (Point point: listOfDangerousZone){
            battleManager.getBattleField().getMatrix().get(point.Y()).set(point.X(), "XXXXXX");
        }
        battleManager.getBattleField().toString();








    }
}
