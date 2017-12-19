package TestPolyBot;

import game.adjutants.AdjutantFielder;
import game.battleFields.BattleField;
import game.battleFields.BattleManager;

public class TestInitializer {
    public static BattleManager initBattleManager() {
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.setOpponentPlayer(battleManager.getPlayerRed());
        battleManager.initializeField();
        new AdjutantFielder().fillZones(battleManager);
        return battleManager;
    }

    public static void setArmy(BattleManager battleManagerTest, int g1, int g2, int g3,  int t1, int t2, int t3){
        battleManagerTest.setHowICanProductArmyLevel1(g1);
        battleManagerTest.setHowICanProductArmyLevel2(g2);
        battleManagerTest.setHowICanProductArmyLevel3(g3);
        battleManagerTest.setHowICanProductTanksLevel1(t1);
        battleManagerTest.setHowICanProductTanksLevel2(t2);
        battleManagerTest.setHowICanProductTanksLevel3(t3);
    }

    public static void setBuildings(BattleManager battleManagerTest, int b,  boolean g, int f){
        battleManagerTest.setHowICanBuild(b);
        battleManagerTest.setConstructedGenerator(g);
        battleManagerTest.setHowICanBuildFactories(f);
    }

}
