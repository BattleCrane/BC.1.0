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
}
