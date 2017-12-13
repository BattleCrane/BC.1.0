package TestPolyBot;

import Adjutants.AdjutantFielder;
import BattleFields.BattleField;
import BattleFields.BattleManager;
import PolyBot.PolyCombinations.Army.IteratorArmy.PolyIteratorArmy;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import org.junit.Test;

public class CreatingArmyTest {

    @Test
    public void findCombination(){
        BattleManager battleManagerTest = new BattleManager(new BattleField());
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.setOpponentPlayer(battleManagerTest.getPlayerRed());
        battleManagerTest.initializeField();

        battleManagerTest.setHowICanProductArmyLevel1(0);
        battleManagerTest.setHowICanProductArmyLevel2(1);
        battleManagerTest.setHowICanProductArmyLevel3(1);
        battleManagerTest.setHowICanProductTanksLevel1(1);
        battleManagerTest.setHowICanProductTanksLevel2(0);
        battleManagerTest.setHowICanProductTanksLevel3(1);

        new AdjutantFielder().fillZones(battleManagerTest);
        PolyIteratorArmy polyIteratorArmy = new PolyIteratorArmy();
        CreatingCombination best = polyIteratorArmy.findCombination(battleManagerTest);
        System.out.println(best);
        battleManagerTest.getBattleField().toString();
    }


}
