package PolyBot.PolyCombinations.upgrading;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import PolyBot.Probes.PolyMainProbe;
import Unities.Unity;

import java.util.*;

public class PolyUpgrading {
    private BattleManager battleManager;
    private Map<String, Unity> map = new HashMap<>();

    public PolyUpgrading(BattleManager battleManager) {
        this.battleManager = battleManager;
        map.put("g", battleManager.getGenerator());
        map.put("b", battleManager.getBarracks());
        map.put("f", battleManager.getFactory());
        map.put("t", battleManager.getTurret());
    }

    public CreatingCombination findCombination(){
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        List<PriorityUnit> list = new ArrayList<>();
        CreatingCombination best = new CreatingCombination(new ArrayList<>(), 0);
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                String type = matrix.get(j).get(i).substring(4, 5);
                if (map.containsKey(type)){
                    Unity unity = map.get(type);
                    Point point = new Point(j ,i);
                    PriorityUnit priorityUnit = polyMainProbe.probeUpgradeTest(battleManager, unity, point);
                    int count = 0;
                    while (priorityUnit.getPriority() > 0){
                        battleManager.upgradeBuilding(point, battleManager.getPlayer());
                        list.add(priorityUnit);
                        priorityUnit = polyMainProbe.probeUpgradeTest(battleManager, unity, point);
                        count++;
                    }
                    for (int c = 0; c < count; c++){
                        battleManager.aggravateUnit(point, unity);
                    }
                }
            }
        }
        list.sort((o1, o2) -> {
            int p1 = (int) o1.getPriority();
            int p2 = (int) o2.getPriority();
            return p1 - p2;
        });
        for (int i = 0; i < battleManager.getHowICanBuild(); i++){
            best.add(list.get(i));
        }
        return best;
    }
}
