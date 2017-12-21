package TestPolyBot;

import game.adjutants.AdjutantFielder;
import game.battleFields.BattleField;
import game.battleFields.BattleManager;
import polytech.polyNexus.probes.parametres.ParentParams;
import static org.junit.Assert.assertTrue;

// TODO: 20.12.17 make injectable
public interface TestInitializer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    Object createTest(BattleManager battleManager, ParentParams parentParams);

    default void createTest(BattleManager manager, ParentParams params, Object expectedObject){
        Object o = createTest(manager, params);
        assertTrue(expectedObject.equals(o));
    }

    default void createTest(BattleManager manager, ParentParams params, TestSettings testSettings, Object expectedObject){
        testSettings.setup();
        createTest(manager, params, expectedObject);
    }

    default BattleManager initBattleManager() {
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.setOpponentPlayer(battleManager.getPlayerRed());
        battleManager.initializeField();
        new AdjutantFielder().fillZones(battleManager);
        return battleManager;
    }

    default void setArmy(BattleManager battleManagerTest, int g1, int g2, int g3,  int t1, int t2, int t3){
        battleManagerTest.setHowICanProductArmyLevel1(g1);
        battleManagerTest.setHowICanProductArmyLevel2(g2);
        battleManagerTest.setHowICanProductArmyLevel3(g3);
        battleManagerTest.setHowICanProductTanksLevel1(t1);
        battleManagerTest.setHowICanProductTanksLevel2(t2);
        battleManagerTest.setHowICanProductTanksLevel3(t3);
    }

    default void setBuildings(BattleManager battleManagerTest, int b,  boolean g, int f){
        battleManagerTest.setHowICanBuild(b);
        battleManagerTest.setConstructedGenerator(g);
        battleManagerTest.setHowICanBuildFactories(f);
    }
}
