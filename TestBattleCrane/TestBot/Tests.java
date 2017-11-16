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
        battleManager.putUnity(battleManager.getPlayerRed(), new Point(10, 10), battleManager.getGunner());
        battleManager.initializeField();
        battleManager.getBattleField().toString();
        List<Point> listOfDangerousZone = probe.probeDangerousZone(battleManager);
        for (Point point: )








    }
}
