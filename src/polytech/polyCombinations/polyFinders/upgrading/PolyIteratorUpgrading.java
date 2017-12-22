package polytech.polyCombinations.polyFinders.upgrading;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import game.unities.Unity;
import polytech.polyNexus.probes.PolyUpgradingProbe;

import java.util.*;

public class PolyIteratorUpgrading {
    private final BattleManager battleManager;
    private final PolyUpgradingProbe upgradingProbe;
    private final Map<String, Unity> map = new HashMap<>();
    private CreatingCombination best = null;

    public PolyIteratorUpgrading(BattleManager battleManager, PolyUpgradingProbe upgradingProbe) {
        this.battleManager = battleManager;
        this.upgradingProbe = upgradingProbe;
        map.put("g", battleManager.getGenerator());
        map.put("b", battleManager.getBarracks());
        map.put("f", battleManager.getFactory());
        map.put("t", battleManager.getTurret());
    }

    public CreatingCombination findCombination(){
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        List<PriorityUnit> list = new ArrayList<>();
        best = new CreatingCombination(new ArrayList<>(), 0);
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                String type = matrix.get(j).get(i).substring(4, 5);
                if (map.containsKey(type)){
                    Unity unity = map.get(type);
                    Point point = new Point(j ,i);
                    PolyUpgradingProbe.UpgradingParams params = new PolyUpgradingProbe.UpgradingParams(unity, point);
                    PriorityUnit priorityUnit = (PriorityUnit) upgradingProbe.probe(params);
                    int count = 0;
                    while (priorityUnit.getPriority() > 0){
                        battleManager.upgradeBuilding(point, battleManager.getPlayer());
                        list.add(priorityUnit);
                        priorityUnit = (PriorityUnit) upgradingProbe.probe(params);
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
