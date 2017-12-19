package polytech.PolyCombinations.upgrading;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import polytech.PolyCombinations.CreatingTools.CreatingCombination;
import polytech.polyNexus.Probes.PolyMainProbe;
import Unities.Unity;

import java.util.*;

public class PolyUpgrading {
    private final BattleManager battleManager;
    private final PolyUpgrading upgrading;
    private final Map<String, Unity> map = new HashMap<>();
    private CreatingCombination best = null;

    public PolyUpgrading(BattleManager battleManager, PolyUpgrading upgrading) {
        this.battleManager = battleManager;
        this.upgrading = upgrading;
        map.put("g", battleManager.getGenerator());
        map.put("b", battleManager.getBarracks());
        map.put("f", battleManager.getFactory());
        map.put("t", battleManager.getTurret());
    }

    public CreatingCombination findCombination(){
        PolyMainProbe polyMainProbe = new PolyMainProbe();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        List<PriorityUnit> list = new ArrayList<>();
        best = new CreatingCombination(new ArrayList<>(), 0);
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
            return p2 - p1;
        });
        int size = battleManager.getHowICanBuild() > list.size() ? list.size() : battleManager.getHowICanBuild();
        System.out.println("Size " + size);
        for (int i = 0; i < size; i++){
            best.add(list.get(i));
        }
        System.out.println("List " + best);
        return best;
    }

    public CreatingCombination getBest() {
        return best;
    }
}
