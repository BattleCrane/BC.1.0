package TestPolyBot;

import Adjutants.AdjutantFielder;
import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import PolyBot.PolyCombinations.Building.IteratorBuilding.PolyIteratorBuilder;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import org.junit.Test;

public class IteratorBuildingTest {
    @Test
    public void findTurretCombination(){
        BattleManager battleManager = new BattleManager(new BattleField());
        battleManager.setPlayer(battleManager.getPlayerBlue());
        battleManager.setOpponentPlayer(battleManager.getPlayerRed());
        battleManager.initializeField();
        new AdjutantFielder().fillZones(battleManager);
        battleManager.setHowICanBuild(3);

        battleManager.putUnity(battleManager.getPlayer(), new Point(5, 5), battleManager.getTurret());
        battleManager.putUnity(battleManager.getPlayer(), new Point(12, 2), battleManager.getGenerator());
        battleManager.putUnity(battleManager.getPlayer(), new Point(7, 4), battleManager.getGenerator());
        battleManager.putUnity(battleManager.getPlayer(), new Point(3, 14), battleManager.getBarracks());

        battleManager.getBattleField().toString();

        PolyIteratorBuilder polyIteratorBuilder = new PolyIteratorBuilder();
        polyIteratorBuilder.findTurretCombination(battleManager);
        CreatingCombination turretCombination = polyIteratorBuilder.getBestCombinationOfBuild();
        System.out.println(turretCombination);
    }
}
