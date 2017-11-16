package TestBattleCrane;

import Adjutants.AdjutantFielder;
import BattleFields.*;
import Players.Player;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        BattleManager battleManager = new BattleManager();
        String barracksFistLevel = "1^!+b'";
        String someUnityTest1 = "3<!+z'";
        String someUnityTest2 = "5>?+r'";
        assertEquals("1^?+b'", battleManager.getAdjutantWakeUpper().sleepUnity(barracksFistLevel));
        assertEquals("3<?+z'", battleManager.getAdjutantWakeUpper().sleepUnity(someUnityTest1));
        assertEquals("5>?+r'", battleManager.getAdjutantWakeUpper().sleepUnity(someUnityTest2));
    }

    @Test
    public void radiusAttack(){
        //Test №1:
        System.out.println("Test №1:");
        BattleManager battleManagerTest1 = new BattleManager(new BattleField());
        battleManagerTest1.setPlayer(new Player("-"));
        battleManagerTest1.putUnity(new Player("+"), new Point(8, 10), battleManagerTest1.getFactory());
        battleManagerTest1.putUnity(new Player("+"), new Point(10, 12), battleManagerTest1.getBarracks());
        battleManagerTest1.putUnity(new Player("+"), new Point(11, 10), battleManagerTest1.getHeadquarters());
        battleManagerTest1.putUnity(new Player("-"), new Point(11, 12), battleManagerTest1.getTurret());
        battleManagerTest1.getAdjutantAttacker().radiusAttack(battleManagerTest1, new Point(11, 12), 2, 1);
        battleManagerTest1.getBattleField().toString();

        //Test №2:
        System.out.println("Test №2:");
        BattleManager battleManagerTest2 = new BattleManager(new BattleField());
        battleManagerTest2.initializeField();
        battleManagerTest1.setPlayer(new Player("+"));
        battleManagerTest2.putUnity(new Player("+"), new Point(0, 5), battleManagerTest2.getTurret());
        battleManagerTest2.getAdjutantAttacker().radiusAttack(battleManagerTest2, new Point (0, 5), 2, 1);
        battleManagerTest2.getBattleField().toString();
    }

    @Test
    public void checkTarget(){
        BattleManager battleManagerTest1 = new BattleManager(new BattleField());
        battleManagerTest1.initializeField();
        battleManagerTest1.setPlayer(new Player("+"));
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point (0, 15), battleManagerTest1.getGunner());
        assertTrue(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));

        battleManagerTest1.putUnity(new Player("-"), new Point(0, 9), battleManagerTest1.getTank());
        assertTrue(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));

        battleManagerTest1.putUnity(new Player("-"), new Point(0, 7), battleManagerTest1.getTurret());
        assertFalse(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));

        battleManagerTest1.putUnity(new Player("-"), new Point(0, 5), battleManagerTest1.getGenerator());
        assertFalse(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(0, 4), new Point(0, 15)));
        //Вывод поля боя:

        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point(15, 0), battleManagerTest1.getTank());
        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point(8, 0), battleManagerTest1.getGenerator());
        assertTrue(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(15, 0), new Point(4, 0)));

        assertFalse(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(15, 0), new Point(1, 0)));
        assertFalse(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(15, 0), new Point(10, 3)));

        battleManagerTest1.putUnity(battleManagerTest1.getPlayer(), new Point(10, 10), battleManagerTest1.getTank());
        assertTrue(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(10, 10), new Point(4, 4)));
        assertFalse(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(10, 10), new Point(1, 1)));


        battleManagerTest1.putUnity(new Player("-"), new Point(1, 14), battleManagerTest1.getGunner());
        assertTrue(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(15, 0), new Point(1, 14)));

        battleManagerTest1.putUnity(new Player("-"), new Point(4, 10), battleManagerTest1.getGenerator());
        assertFalse(battleManagerTest1.getAdjutantAttacker().checkTarget(battleManagerTest1, new Point(15, 0), new Point(1, 14)));
        battleManagerTest1.getBattleField().toString();
    }

    @Test
    public void fillZones(){
        BattleManager battleManager = new BattleManager();
        battleManager.initializeField();
        Player blue =  battleManager.getPlayerBlue();
        battleManager.getBattleField().getMatrix().get(7).set(15, "XXXXXX");
        battleManager.putUnity(blue, new Point(9, 13), battleManager.getGenerator());
        battleManager.putUnity(blue, new Point(7, 13), battleManager.getGenerator());
        battleManager.putUnity(blue, new Point(8, 11), battleManager.getGenerator());
        battleManager.putUnity(blue, new Point(8, 9), battleManager.getGenerator());
        battleManager.putUnity(blue, new Point(11, 9), battleManager.getGenerator());
        AdjutantFielder adjutantFielder = new AdjutantFielder();
        adjutantFielder.fillZones(battleManager);
        battleManager.getBattleField().toString();

    }

}
