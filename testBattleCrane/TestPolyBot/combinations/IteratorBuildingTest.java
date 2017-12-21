package TestPolyBot.combinations;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import TestPolyBot.TestInitializer;
import polytech.polyCombinations.building.iteratorBuilding.PolyIteratorBuilder;
import polytech.polyCombinations.creatingTools.CreatingCombination;
import org.junit.Test;
import polytech.polyNexus.probes.parametres.ParentParams;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.*;

public class IteratorBuildingTest implements TestInitializer{

    private PolyIteratorBuilder initPolyIteratorBuilder(BattleManager battleManager){
        Priorities priorities = new Priorities();
        PolyDistanceProbe polyDistanceProbe = new PolyDistanceProbe(battleManager);
        PolyZoneProbe polyZoneProbe = new PolyZoneProbe(battleManager);

        PolyBuildingProbe polyBuildingProbe = new PolyBuildingProbe(battleManager, priorities
                , polyZoneProbe, polyDistanceProbe);

        PolyRadiusProbe polyRadiusProbe = new PolyRadiusProbe(battleManager, priorities
                , polyZoneProbe, polyDistanceProbe);

        return new PolyIteratorBuilder(battleManager, polyBuildingProbe, polyRadiusProbe);
    }

    @Test
    public void findTurretCombination(){
        BattleManager battleManager = initBattleManager();
        battleManager.setHowICanBuild(3);

        battleManager.putUnity(battleManager.getPlayer(), new Point(5, 5), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayer(), new Point(12, 2), battleManager.getGenerator());
        battleManager.putUnity(battleManager.getPlayer(), new Point(7, 4), battleManager.getGenerator());
        battleManager.putUnity(battleManager.getPlayer(), new Point(3, 14), battleManager.getBarracks());

        battleManager.getBattleField().toString();

        PolyIteratorBuilder polyIteratorBuilder = initPolyIteratorBuilder(battleManager);
        polyIteratorBuilder.findTurretCombination();
        CreatingCombination turretCombination = polyIteratorBuilder.getBest();
        System.out.println(turretCombination);
    }

    @Override
    public Object createTest(BattleManager battleManager, ParentParams parentParams) {
        return null;
    }
}
