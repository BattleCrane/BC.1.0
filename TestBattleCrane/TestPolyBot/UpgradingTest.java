package TestPolyBot;

import BattleFields.BattleManager;
import BattleFields.Point;
import polytech.PolyCombinations.CreatingTools.CreatingCombination;
import polytech.PolyCombinations.upgrading.PolyUpgrading;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UpgradingTest {
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
        PolyUpgrading polyUpgrading = new PolyUpgrading(battleManagerTest);
        CreatingCombination creatingCombination = polyUpgrading.findCombination();
        System.out.println(creatingCombination);
        assertTrue(1050.0 == creatingCombination.getSum());
    }
}
