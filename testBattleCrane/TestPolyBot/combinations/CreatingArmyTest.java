package TestPolyBot.combinations;

import game.battleFields.BattleManager;
import TestPolyBot.TestInitializer;
import org.jetbrains.annotations.NotNull;
import polytech.polyCombinations.Army.iteratorArmy.PolyIteratorArmy;
import polytech.polyCombinations.creatingTools.CreatingCombination;
import org.junit.Test;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.PolyBallisticProbe;
import polytech.polyNexus.probes.PolyDistanceProbe;
import polytech.polyNexus.probes.PolyZoneProbe;

public class CreatingArmyTest {

    @NotNull
    private PolyIteratorArmy initPolyIteratorArmy(BattleManager battleManagerTest, PolyZoneProbe polyZoneProbe){
        PolyBallisticProbe ballisticProbe = new PolyBallisticProbe(battleManagerTest, new Priorities()
                , polyZoneProbe, new PolyDistanceProbe(battleManagerTest));
        return new PolyIteratorArmy(battleManagerTest, ballisticProbe);
    }

    @Test
    public void findCombination(){
        BattleManager battleManagerTest = TestInitializer.initBattleManager();
        TestInitializer.setArmy(battleManagerTest, 0, 1, 1, 1, 0, 1);
        PolyZoneProbe polyZoneProbe = new PolyZoneProbe(battleManagerTest);
        polyZoneProbe.probe(null);
        PolyIteratorArmy polyIteratorArmy = initPolyIteratorArmy(battleManagerTest, polyZoneProbe);
        CreatingCombination best = polyIteratorArmy.findCombination(battleManagerTest);
        System.out.println(best);
        battleManagerTest.getBattleField().toString();
    }
}
