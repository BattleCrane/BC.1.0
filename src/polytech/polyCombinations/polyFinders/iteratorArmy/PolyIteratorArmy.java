package polytech.polyCombinations.polyFinders.iteratorArmy;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import org.jetbrains.annotations.Contract;
import polytech.polyCombinations.polyFinders.creatingTools.CreatingCombination;
import polytech.priority.PolyPriorityUnit;
import polytech.polyNexus.probes.PolyBallisticProbe;
import game.unities.Unity;

import java.util.ArrayList;
import java.util.List;

// TODO: 22.12.2017 MAKE NEXUS
public final class PolyIteratorArmy {
    private final BattleManager battleManager;
    private final PolyBallisticProbe ballisticProbe;

    public PolyIteratorArmy(BattleManager battleManager, PolyBallisticProbe ballisticProbe) {
        this.battleManager = battleManager;
        this.ballisticProbe = ballisticProbe;
    }

    // TODO: 22.12.2017 simplify
    public CreatingCombination findCombination(BattleManager battleManager) {
        CreatingCombination bestCombination = new CreatingCombination(new ArrayList<>(), 0);
        List<String> flags = new ArrayList<>();

        int howICanProductArmyLevel1 = battleManager.getHowICanProductArmyLevel1();
        int howICanProductTanksLevel1 = battleManager.getHowICanProductTanksLevel1();
        int howICanProductArmyLevel2 = battleManager.getHowICanProductArmyLevel2();
        int howICanProductTanksLevel2 = battleManager.getHowICanProductTanksLevel2();
        int howICanProductArmyLevel3 = battleManager.getHowICanProductArmyLevel3();
        int howICanProductTanksLevel3 = battleManager.getHowICanProductTanksLevel3();
        int sum = howICanProductArmyLevel1 + howICanProductArmyLevel2 + howICanProductArmyLevel3
                + howICanProductTanksLevel1 + howICanProductTanksLevel2 + howICanProductTanksLevel3;

        List<List<String>> matrix = battleManager.getBattleField().getMatrix();

        System.out.println(sum);
        for (int k = 0; k < sum; k++) {
            PriorityUnit best = new PolyPriorityUnit(0.0, new Point(0, 0), new Unity(""));
            String labelStepBallistic = "";
            String flag = "";
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    Point currentPoint = new Point(j, i);
                    String currentUnity = matrix.get(j).get(i);
                    if (currentUnity.substring(1).equals("    0")) { //Если это пустая клетка ->
                        //Если это наша пустая клетка ->
                        PolyBallisticProbe.Params params;
                        if (currentUnity.substring(0, 1).equals(battleManager.getPlayer().getColorType())) {
                            if (howICanProductTanksLevel1 > 0) { //Проверяем танки 1-го уровня:
                                params = new PolyBallisticProbe.Params(battleManager.getTank(), currentPoint);
                                PriorityUnit priorityUnit = (PriorityUnit) ballisticProbe.probe(params);
                                if (priorityUnit.getPriority() > best.getPriority()) {
                                    best = priorityUnit;
                                    labelStepBallistic = "tankLvl1";
                                    flag = "+";
                                }
                            }
                            if (howICanProductArmyLevel1 > 0) { //Проверяем автоматчиков 1-го уровня:
                                params = new PolyBallisticProbe.Params(battleManager.getGunner(), currentPoint);
                                PriorityUnit priorityUnit = (PriorityUnit) ballisticProbe.probe(params);
                                if (priorityUnit.getPriority() > best.getPriority()) {
                                    best = priorityUnit;
                                    labelStepBallistic = "gunnerLvl1";
                                    flag = "+";
                                }
                            }
                        }
                        //Если это нейральная или наша пустая клетка ->
                        if (currentUnity.substring(0, 1).equals(" ") || currentUnity.substring(0, 1)
                                .equals(battleManager.getPlayer().getColorType())) {
                            if (howICanProductTanksLevel2 > 0) { //Проверяем танки 2-го уровня:
                                params = new PolyBallisticProbe.Params(battleManager.getTank(), currentPoint);
                                PriorityUnit priorityUnit = (PriorityUnit) ballisticProbe.probe(params);
                                if (priorityUnit.getPriority() > best.getPriority()) {
                                    labelStepBallistic = "tankLvl2";
                                    best = priorityUnit;
                                    flag = " ";
                                }
                            }
                            if (howICanProductArmyLevel2 > 0) { //Проверяем автоматчиков 2-го уровня:
                                params = new PolyBallisticProbe.Params(battleManager.getGunner(), currentPoint);
                                PriorityUnit priorityUnit = (PriorityUnit) ballisticProbe.probe(params);
                                if (priorityUnit.getPriority() > best.getPriority()) {
                                    labelStepBallistic = "gunnerLvl2";
                                    best = priorityUnit;
                                    flag = " ";
                                }
                            }
                        }
                        //Если это пустая клетка ->
                        if (howICanProductTanksLevel3 > 0) { //Проверяем танки 3-го уровня:
                            params = new PolyBallisticProbe.Params(battleManager.getTank(), currentPoint);
                            PriorityUnit priorityUnit = (PriorityUnit) ballisticProbe.probe(params);
                            if (priorityUnit.getPriority() > best.getPriority()) {
                                labelStepBallistic = "tankLvl3";
                                best = priorityUnit;
                                flag = "-";
                            }
                        }
                        if (howICanProductArmyLevel3 > 0) {  //Проверяем автоматчиков 3-го уровня:
                            params = new PolyBallisticProbe.Params(battleManager.getGunner(), currentPoint);
                            PriorityUnit priorityUnit = (PriorityUnit) ballisticProbe.probe(params);
                            if (priorityUnit.getPriority() > best.getPriority()) {
                                labelStepBallistic = "gunnerLvl3";
                                best = priorityUnit;
                                flag = "-";
                            }
                        }
                    }
                }
            }
            System.out.println(labelStepBallistic);
            switch (labelStepBallistic) {
                case "tankLvl1":
                    howICanProductTanksLevel1--;
                    System.out.println("T1");
                    break;
                case "tankLvl2":
                    howICanProductTanksLevel2--;
                    System.out.println("T2");
                    break;
                case "tankLvl3":
                    howICanProductTanksLevel3--;
                    System.out.println("T3");
                    break;
                case "gunnerLvl1":
                    howICanProductArmyLevel1--;
                    System.out.println("G1");
                    break;
                case "gunnerLvl2":
                    howICanProductArmyLevel2--;
                    System.out.println("G2");
                    break;
                case "gunnerLvl3":
                    howICanProductArmyLevel3--;
                    System.out.println("G3");
                    break;
            }
            bestCombination.add(best);
            flags.add(flag);
            battleManager.putUnity(battleManager.getPlayer(), best.getPoint(), best.getUnity());
        }
        for (int i = 0; i < bestCombination.getUnits().size(); i++) {
            PriorityUnit p = bestCombination.getUnits().get(i);
            battleManager.removeUnity(p.getPoint(), p.getUnity(), flags.get(i));
        }
        return bestCombination;
    }

    @Contract(pure = true)
    public PolyBallisticProbe getBallisticProbe() {
        return ballisticProbe;
    }
}
