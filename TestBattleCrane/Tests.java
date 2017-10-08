import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantSleeper;
import BattleFields.*;
import Players.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class Tests {


    @Test
    public void levelUp(){
        //Тест на бараки:
        BattleManager battleManager = new BattleManager();
        String barracksFistLevel = "1^!+b'";
        String barracksSecondLevel = "1<!+b'";
        String barracksThirdLevel = "1>!+b'";
        assertEquals(barracksSecondLevel, battleManager.levelUp(barracksFistLevel));
        assertEquals(barracksThirdLevel, battleManager.levelUp(barracksSecondLevel));
        assertEquals("1>!+b'", battleManager.levelUp(barracksThirdLevel));
    }

    @Test
    public void sleepUnity(){
        //Тест на бараки:
        String barracksFistLevel = "1^!+b'";
        String someUnityTest1 = "3<!+z'";
        String someUnityTest2 = "5>?+r'";
        assertEquals("1^?+b'", AdjutantSleeper.sleepUnity(barracksFistLevel));
        assertEquals("3<?+z'", AdjutantSleeper.sleepUnity(someUnityTest1));
        assertEquals("5>?+r'", AdjutantSleeper.sleepUnity(someUnityTest2));
    }

    @Test
    public void radiusAttack(){
        //Test №1:
        System.out.println("Test №1:");
        BattleManager battleManagerTest1 = new BattleManager(new BattleField());
        battleManagerTest1.setPlayer(new Player("-"));
        battleManagerTest1.putUnity(new Player("+"), new Point(8, 10), battleManagerTest1.getFactoryVertical());
        battleManagerTest1.putUnity(new Player("+"), new Point(10, 12), battleManagerTest1.getBarracksHorizontal());
        battleManagerTest1.putUnity(new Player("+"), new Point(11, 10), battleManagerTest1.getHeadquarters());
        battleManagerTest1.putUnity(new Player("-"), new Point(11, 12), battleManagerTest1.getTurret());
        AdjutantAttacker.radiusAttack(battleManagerTest1, new Point(11, 12), 2, 1);
        battleManagerTest1.getBattleField().toString();

        //Test №2:
        System.out.println("Test №2:");
        BattleManager battleManagerTest2 = new BattleManager(new BattleField());
        battleManagerTest2.initializeField();
        battleManagerTest1.setPlayer(new Player("+"));
        battleManagerTest2.putUnity(new Player("+"), new Point(0, 5), battleManagerTest2.getTurret());
        AdjutantAttacker.radiusAttack(battleManagerTest2, new Point (0, 5), 2, 1);
        battleManagerTest2.getBattleField().toString();
    }

    @Test
    public void checkTarget(){
        BattleManager battleManagerTest1 = new BattleManager(new BattleField());
        battleManagerTest1.initializeField();
        battleManagerTest1.setPlayer(new Player("+"));
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point (0, 15), battleManagerTest1.getGunner());
        assertTrue(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));

        battleManagerTest1.putUnity(new Player("-"), new Point(0, 9), battleManagerTest1.getTank());
        assertTrue(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));

        battleManagerTest1.putUnity(new Player("-"), new Point(0, 7), battleManagerTest1.getTurret());
        assertFalse(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));

        battleManagerTest1.putUnity(new Player("-"), new Point(0, 5), battleManagerTest1.getGenerator());
        assertFalse(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));
        //Вывод поля боя:

        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point(15, 0), battleManagerTest1.getTank());
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point(8, 0), battleManagerTest1.getGenerator());
        assertTrue(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(15, 0), new Point(4, 0)));

        assertFalse(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(15, 0), new Point(1, 0)));
        assertFalse(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(15, 0), new Point(10, 3)));

        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point(10, 10), battleManagerTest1.getTank());
        assertTrue(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(10, 10), new Point(4, 4)));
        assertFalse(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(10, 10), new Point(1, 1)));


        battleManagerTest1.putUnity(new Player("-"), new Point(1, 14), battleManagerTest1.getGunner());
        assertTrue(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(15, 0), new Point(1, 14)));

        battleManagerTest1.putUnity(new Player("-"), new Point(4, 10), battleManagerTest1.getGenerator());
        assertFalse(AdjutantAttacker.checkTarget(battleManagerTest1, new Point(15, 0), new Point(1, 14)));
        battleManagerTest1.getBattleField().toString();

    }

}
