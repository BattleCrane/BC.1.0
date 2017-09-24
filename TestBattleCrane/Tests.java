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
        Unity barracksFistLevel = new Unity("1^!+b'");
        Unity barracksSecondLevel = new Unity("1<!+b'");
        Unity barracksThirdLevel = new Unity("1>!+b'");
        assertTrue(barracksSecondLevel.getId(), controlBattler.levelUp(barracksFistLevel));
        assertTrue(barracksThirdLevel.getId(), controlBattler.levelUp(barracksSecondLevel));
        assertFalse(barracksThirdLevel.getId(), controlBattler.levelUp(barracksThirdLevel));

//        //Тест на генераторы:
//        Unity generatorFistLevel = new Unity("1^!-g'");
//        Unity generatorSecondLevel = new Unity("1<!-g'");
//        Unity generatorThirdLevel = new Unity("1>!-g'");
//        assertTrue(barracksSecondLevel.getId(), controlBattler.levelUp(barracksFistLevel));
//        assertTrue(barracksThirdLevel.getId(), controlBattler.levelUp(barracksSecondLevel));
//        assertFalse(barracksThirdLevel.getId(), controlBattler.levelUp(barracksThirdLevel));

    }
}
