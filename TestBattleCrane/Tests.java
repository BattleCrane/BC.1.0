import BattleFields.BattleField;
import BattleFields.ControlBattler;
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
        assertEquals(null, controlBattler.levelUp(barracksThirdLevel));
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
}
