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
        //Test №1:
        System.out.println("Test №1:");
        ControlBattler controlBattlerTest1 = new ControlBattler(new BattleField());
        controlBattlerTest1.setPlayer(new Player("-"));
        controlBattlerTest1.putUnity(new Player("+"), new Point(8, 10), controlBattlerTest1.getFactoryVertical());
        controlBattlerTest1.putUnity(new Player("+"), new Point(10, 12), controlBattlerTest1.getBarracksHorizontal());
        controlBattlerTest1.putUnity(new Player("+"), new Point(11, 10), controlBattlerTest1.getHeadquarters());
        controlBattlerTest1.putUnity(new Player("-"), new Point(11, 12), controlBattlerTest1.getTurret());
        Attacker.radiusAttack(controlBattlerTest1, new Point(11, 12), 2, 1);
        controlBattlerTest1.getBattleField().toString();

        //Test №2:
        System.out.println("Test №1:");
        ControlBattler controlBattlerTest2 = new ControlBattler(new BattleField());
        controlBattlerTest2.initializeField();
        controlBattlerTest1.setPlayer(new Player("+"));
        controlBattlerTest2.putUnity(new Player("+"), new Point(0, 5), controlBattlerTest2.getTurret());
        Attacker.radiusAttack(controlBattlerTest2, new Point (0, 5), 2, 1);
        controlBattlerTest2.getBattleField().toString();
    }

}
