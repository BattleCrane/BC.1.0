package TestPolyBot.combinations;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import polytech.polyCombinations.creatingTools.CreatingCombination;
import polytech.polyCombinations.upgrading.PolyIteratorUpgrading;
import org.junit.Test;
import polytech.polyNexus.probes.parametres.ParentParams;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.*;

import static org.junit.Assert.assertTrue;

public class IteratorUpgradingTest implements TestInitializer {

    private PolyIteratorUpgrading initPolyIteratorUpgrading(BattleManager battleManager){
        Priorities priorities = new Priorities();
        return new PolyIteratorUpgrading(battleManager
                , new PolyUpgradingProbe(battleManager, priorities));
    }

    @Test
    public void findCombination(){
        BattleManager battleManagerTest = initBattleManager();

        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(7,10), battleManagerTest.getGenerator());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(14, 1), battleManagerTest.getBarracks());
        battleManagerTest.putUnity(battleManagerTest.getPlayerBlue(), new Point(12, 4), battleManagerTest.getTurret());
        battleManagerTest.setHowICanBuild(3);
        PolyIteratorUpgrading polyIteratorUpgrading = initPolyIteratorUpgrading(battleManagerTest);
        CreatingCombination creatingCombination = polyIteratorUpgrading.findCombination();
        System.out.println(creatingCombination);
        assertTrue(1050.0 == creatingCombination.getSum());
    }

    @Override
    public Object createTest(BattleManager battleManager, ParentParams parentParams) {
        return null;
    }
}
