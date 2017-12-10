package PolyBot.Turn.GenesisBuilding;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Turn.Creating.CreatingCombination;
import PolyBot.Turn.Creating.ConditionalUnit;
import PolyBot.Turn.PolyProbe;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PolyGenesisBuilder {
    public PolyGenesisBuilder() {
    }

    private Set<CreatingCombination> combinations = new HashSet<>();
    private CreatingCombination bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
    private CreatingCombination currentCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
    private double max = 0.0;


    public CreatingCombination findCombination(BattleManager battleManager) {
        int population = 20;
        int selection = 5;
        createPopulation(battleManager, population);
        for (int i = 0; i < selection; i++){
            mergeAndMutatePopulation(battleManager);
            arrangeTournament();
        }
        return chooseTheBest();
    }

    public void createPopulation(BattleManager battleManager, int population) {
        for (int i = 0; i < population; i++) {
            randomCombination(battleManager, battleManager.getHowICanBuild());
        }

    }

    private void randomCombination(BattleManager battleManager, int howICanBuild) {
        List<ConditionalUnit> conditionalUnitList = Arrays.asList(
                //Barracks:
                new ConditionalUnit(battleManager, battleManager.getBarracks(), (s) -> {
                }, (e) -> {
                }) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                                battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer());
                    }
                },
                //Factory:
                new ConditionalUnit(battleManager, battleManager.getFactory(),
                        (s) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() - 1),
                        (e) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() + 1)) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return battleManager.getHowCanBuildFactories() > 0 && battleManager.canConstructBuilding(point,
                                battleManager.getFactory(), battleManager.getPlayer()) &&
                                battleManager.isEmptyTerritory(point, battleManager.getFactory());
                    }
                },
                //Generator:
                new ConditionalUnit(battleManager, battleManager.getGenerator(),
                        (s) -> battleManager.setConstructedGenerator(true),
                        (e) -> battleManager.setConstructedGenerator(false)) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return howICanBuild <= 2 && !battleManager.isConstructedGenerator() &&
                                battleManager.canConstructBuilding(point, battleManager.getGenerator(), battleManager.getPlayer()) &&
                                battleManager.isEmptyTerritory(point, battleManager.getGenerator());
                    }
                }
        );

        boolean isConstructed = false;
        while (!isConstructed) {
            int x = new Random().nextInt(16);
            int y = new Random().nextInt(16);
            if (x == 16) {
                x = 0;
            }
            if (y == 16) {
                y = 0;
            }
            String currentUnity = battleManager.getBattleField().getMatrix().get(x).get(y);
            Point point = new Point(y, x);
            if (currentUnity.substring(1).equals("    0")) {
                int i = new Random().nextInt(3);
                if (i == 3) {
                    i = 0;
                }
                ConditionalUnit conditionalUnit = conditionalUnitList.get(i);
                if (conditionalUnit.isPerformedCondition(point)) {
                    PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, conditionalUnit.getUnity(), point);
                    if (!currentCombinationOfBuild.contains(priorityUnit) && battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
                        isConstructed = true;
                        currentCombinationOfBuild.add(priorityUnit);
                        int nextBuild = howICanBuild - 1;
                        conditionalUnit.getStartPredicate().run(battleManager);
                        if (nextBuild > 0) {
                            randomCombination(battleManager, nextBuild);
                        } else {
                            if (!combinations.contains(currentCombinationOfBuild)) {
                                CreatingCombination combination = new CreatingCombination(new ArrayList<>(), 0);
                                for (PriorityUnit p : currentCombinationOfBuild.getPriorityUnitList()) {
                                    combination.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
                                }
                                combinations.add(combination);
                            }
                        }
                        currentCombinationOfBuild.removeLast();
                        battleManager.removeUnity(point, conditionalUnit.getUnity(), currentUnity.substring(0, 1));
                        conditionalUnit.getEndPredicate().run(battleManager);
                    }
                }
            }
        }
    }

    public CreatingCombination merge(CreatingCombination creatingCombination, CreatingCombination otherCreatingCombination) {
        int combinationSize = creatingCombination.getPriorityUnitList().size();
        int otherCombinationSize = otherCreatingCombination.getPriorityUnitList().size();
        int size = combinationSize > otherCombinationSize ? combinationSize : otherCombinationSize;
        CreatingCombination mergedCombination = new CreatingCombination(new ArrayList<>(), 0);
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0 && combinationSize >= size) {
                PriorityUnit priorityUnit = creatingCombination.getPriorityUnitList().get(i);
                mergedCombination.add(new PolyPriorityUnit(priorityUnit.getPriority(), priorityUnit.getPoint(), priorityUnit.getUnity()));
            } else {
                PriorityUnit priorityUnit = otherCreatingCombination.getPriorityUnitList().get(i);
                mergedCombination.add(new PolyPriorityUnit(priorityUnit.getPriority(), priorityUnit.getPoint(), priorityUnit.getUnity()));
            }
        }
        return mergedCombination;
    }

    public void mergeAndMutatePopulation(BattleManager battleManager) {
        List<CreatingCombination> lists = new ArrayList<>();
        lists.addAll(combinations);
        int lastIndex = lists.size() - 1;
        for (int i = 0; i <= lastIndex / 2; i++) {
            CreatingCombination mergedCombination = merge(lists.get(i), lists.get(lastIndex - i));
            CreatingCombination mutatedCombination = mutate(battleManager, mergedCombination);
            if (!combinations.contains(mutatedCombination)) {
                combinations.add(mutatedCombination);
            }
        }
    }

    public CreatingCombination mutate(BattleManager battleManager, CreatingCombination mergedList) {

        bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);

        currentCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0);

        int step = 0;

        nextMutate(battleManager, step, mergedList);

        return bestCombinationOfBuild;
    }

    @Nullable
    private void nextMutate(BattleManager battleManager, int step, CreatingCombination mergedList) {

        Map<String, ConditionalUnit> conditionalUnitMap = new HashMap<>();

        conditionalUnitMap.put("b", new ConditionalUnit(battleManager, battleManager.getBarracks(), (s) -> {
        }, (e) -> {
        }) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                        battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer());
            }
        });

        conditionalUnitMap.put("f", new ConditionalUnit(battleManager, battleManager.getFactory(),
                (s) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() - 1),
                (e) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() + 1)) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return battleManager.getHowCanBuildFactories() > 0 && battleManager.canConstructBuilding(point,
                        battleManager.getFactory(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getFactory());
            }
        });
        conditionalUnitMap.put("g", new ConditionalUnit(battleManager, battleManager.getGenerator(),
                (s) -> battleManager.setConstructedGenerator(true),
                (e) -> battleManager.setConstructedGenerator(false)) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return step <= 2 && !battleManager.isConstructedGenerator() &&
                        battleManager.canConstructBuilding(point, battleManager.getGenerator(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getGenerator());
            }
        });


        if (step < mergedList.getPriorityUnitList().size()) {
            //Идем по всем расположенным юнитам:
            PriorityUnit p = mergedList.getPriorityUnitList().get(step);
            ConditionalUnit conditionalUnit = conditionalUnitMap.get(p.getUnity().getId());

            if (!conditionalUnit.isPerformedCondition(p.getPoint())) { //Если условия не выполняются
                String flag = "";
                for (int i = 0; i < 16; i++) { //Ищем сами лучший вариант
                    for (int j = 0; j < 16; j++) {
                        String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                        Point point = new Point(j, i);
                        if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                            for (ConditionalUnit unit : conditionalUnitMap.values()) { //Тогда перебираем все строения:

                                if (unit.isPerformedCondition(point)) {//Если территория свободна и рядом есть мои строения
                                    PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, unit.getUnity(), point); //Исследуем приоритет на постройку
                                    if (!currentCombinationOfBuild.contains(priorityUnit) &&
                                            battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
                                        currentCombinationOfBuild.add(priorityUnit);

                                        unit.getStartPredicate().run(battleManager);

                                        if (currentCombinationOfBuild.getSum() > bestCombinationOfBuild.getSum()) {
                                            bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0);
                                            for (PriorityUnit e : currentCombinationOfBuild.getPriorityUnitList()) {
                                                bestCombinationOfBuild.add(new PolyPriorityUnit(e.getPriority(), e.getPoint(), e.getUnity()));
                                            }
                                            flag = currentUnity.substring(0, 1);
                                        }

                                        unit.getEndPredicate().run(battleManager);

                                        currentCombinationOfBuild.removeLast();
                                        battleManager.removeUnity(point, unit.getUnity(), currentUnity.substring(0, 1));
                                    }
                                }

                            }
                        }
                    }
                }


//                if (bestCombinationOfBuild.getPriorityUnitList().size() > 0){
                    PriorityUnit newPriorityUnit = bestCombinationOfBuild.getPriorityUnitList().get(
                            bestCombinationOfBuild.getPriorityUnitList().size() - 1
                    );

                    currentCombinationOfBuild.add(new PolyPriorityUnit(newPriorityUnit.getPriority(), newPriorityUnit.getPoint(),
                            newPriorityUnit.getUnity()));

                    battleManager.putUnity(battleManager.getPlayer(), newPriorityUnit.getPoint(), newPriorityUnit.getUnity());
                    ConditionalUnit newConditionalUnit = conditionalUnitMap.get(newPriorityUnit.getUnity().getId());
                    newConditionalUnit.getStartPredicate().run(battleManager);
                    int nextStep = step + 1;
                    nextMutate(battleManager, nextStep, mergedList);
                    newConditionalUnit.getEndPredicate().run(battleManager);
                    battleManager.removeUnity(newPriorityUnit.getPoint(), newPriorityUnit.getUnity(), flag);
//                }

            } else {
                bestCombinationOfBuild.add(p);
                currentCombinationOfBuild.add(p);
                String flag = battleManager.getBattleField().getMatrix().get(p.getPoint().X()).get(p.getPoint().Y()).substring(0, 1);
                battleManager.putUnity(battleManager.getPlayer(), p.getPoint(), p.getUnity());
                conditionalUnit.getStartPredicate().run(battleManager);
                int nextStep = step + 1;
                nextMutate(battleManager, nextStep, mergedList);
                conditionalUnit.getEndPredicate().run(battleManager);
                battleManager.removeUnity(p.getPoint(), p.getUnity(), flag);
            }
        }
    }

    public void arrangeTournament() {
        List<CreatingCombination> combinationList = new ArrayList<>();
        combinationList.addAll(combinations);
        int lastIndex = combinationList.size() - 1;
        for (int i = 0; i <= lastIndex / 2; i++) {
            if (combinationList.get(i).getSum() > combinationList.get(lastIndex - i).getSum()) {
                combinationList.remove(lastIndex - i);
            } else {
                combinationList.remove(i);
            }
        }
    }

    public CreatingCombination chooseTheBest(){
        CreatingCombination best = new CreatingCombination(new ArrayList<>(), 0);
        for (CreatingCombination combination: combinations){
            if (combination.getSum() > best.getSum()){
                best = combination;
            }
        }
        return best;
    }

    public CreatingCombination getBestCombinationOfBuild() {
        System.out.println("Max: " + max);
        return bestCombinationOfBuild;
    }

    public Set<CreatingCombination> getCombinations() {
        return combinations;
    }

}


