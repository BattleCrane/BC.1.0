import BattleFields.Attacker;
import BattleFields.BattleField;
import BattleFields.ControlBattler;
import BattleFields.Point;
import Players.Player;
import Unities.Unity;
import org.junit.Test;
import static org.junit.Assert.*;

public class Tests {
    ControlBattler controlBattler = new ControlBattler();

    @Test
    public void levelUp(){
        //Тест на бараки:
        String barracksFistLevel = "1^!+b'";
        String barracksSecondLevel = "1<!+b'";
        String barracksThirdLevel = "1>!+b'";
        assertEquals(barracksSecondLevel, controlBattler.levelUp(barracksFistLevel));
        assertEquals(barracksThirdLevel, controlBattler.levelUp(barracksSecondLevel));
        assertEquals("1>!+b'", controlBattler.levelUp(barracksThirdLevel));
    }

    @Test
    public void sleepUnity(){
        //Тест на бараки:
        String barracksFistLevel = "1^!+b'";
        String someUnityTest1 = "3<!+z'";
        String someUnityTest2 = "5>?+r'";
        assertEquals("1^?+b'", controlBattler.sleepUnity(barracksFistLevel));
        assertEquals("3<?+z'", controlBattler.sleepUnity(someUnityTest1));
        assertEquals("5>?+r'", controlBattler.sleepUnity(someUnityTest2));
    }
    @Test
    public void radiusAttack(){
        ControlBattler controlBattler = new ControlBattler(new BattleField());
        controlBattler.setPlayer(new Player("-"));
        controlBattler.putUnity(new Player("+"), new Point(8, 10), controlBattler.getFactoryVertical());
        controlBattler.putUnity(new Player("+"), new Point(10, 12), controlBattler.getBarracksHorizontal());
        controlBattler.putUnity(new Player("+"), new Point(11, 10), controlBattler.getHeadquarters());
        controlBattler.putUnity(new Player("-"), new Point(11, 12), controlBattler.getTurret());
        controlBattler.getBattleField().toString();
        try {
            Attacker.radiusAttack(controlBattler, controlBattler.getBattleField(), controlBattler.getIdentificationField(), new Point(11, 12), 2, 1, controlBattler.getOpponentPlayer());
        } catch (Exception ignored){
        }
        controlBattler.getBattleField().toString();
        controlBattler.getIdentificationField().toString();
    }

}
