package TestPolyBot;

import BattleFields.BattleManager;
import BattleFields.Point;
import polytech.PolyCombinations.CreatingTools.CreatingCombination;
import polytech.PolyCombinations.upgrading.PolyIteratorUpgrading;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IteratorUpgradingTest {
    @Test
    public void findCombination(){
        BattleManager battleManagerTest = new BattleManager();
        battleManagerTest.setPlayer(battleManagerTest.getPlayerBlue());
        battleManagerTest.setOpponentPlayer(battleManagerTest.getPlayerRed());
        battleManagerTest.initializeField();
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(7,10), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(14, 1), battleManagerTest.getBarracks());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(12, 4), battleManagerTest.getTurret());
        battleManagerTest.setHowICanBuild(3);
        PolyIteratorUpgrading polyIteratorUpgrading = new PolyIteratorUpgrading(battleManagerTest);
        CreatingCombination creatingCombination = polyIteratorUpgrading.findCombination();
        System.out.println(creatingCombination);
        assertTrue(1050.0 == creatingCombination.getSum());
    }
}
