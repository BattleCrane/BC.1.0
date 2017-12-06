package PolyBot.Turn.GenesisBuilding;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Turn.Creating.CreatingList;
import PolyBot.Turn.Creating.ConditionalUnit;
import PolyBot.Turn.PolyProbe;
import Unities.Unity;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PolyGenesisBuilder {
    public PolyGenesisBuilder() {
    }

    public PolyGenesisBuilder(BattleManager battleManager) {

    }


    private Set<CreatingList> combinations = new HashSet<>();
    private CreatingList bestCombinationOfBuild = new CreatingList(new ArrayList<>(), -10000.0);
    private CreatingList currentCombinationOfBuild = new CreatingList(new ArrayList<>(), 0.0);
    private double max = 0.0;


    public void findCombination(BattleManager battleManager, int howICanBuild) {
        int population = 20;
        createPopulation(battleManager, population);
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
                                CreatingList combination = new CreatingList(new ArrayList<>(), 0);
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

    public CreatingList merge(CreatingList creatingList, CreatingList otherCreatingList) {
        int combinationSize = creatingList.getPriorityUnitList().size();
        int otherCombinationSize = otherCreatingList.getPriorityUnitList().size();
        int size = combinationSize > otherCombinationSize ? combinationSize : otherCombinationSize;
        CreatingList mergeCombination = new CreatingList(new ArrayList<>(), 0);
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0 && combinationSize >= size) {
                PriorityUnit priorityUnit = creatingList.getPriorityUnitList().get(i);
                mergeCombination.add(new PolyPriorityUnit(priorityUnit.getPriority(), priorityUnit.getPoint(), priorityUnit.getUnity()));
            } else {
                PriorityUnit priorityUnit = otherCreatingList.getPriorityUnitList().get(i);
                mergeCombination.add(new PolyPriorityUnit(priorityUnit.getPriority(), priorityUnit.getPoint(), priorityUnit.getUnity()));
            }
        }
        return mergeCombination;
    }

    public void mergePopulation() {
        List<CreatingList> lists = new ArrayList<>();
        lists.addAll(combinations);
        int lastIndex = lists.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            CreatingList mergeCombination = merge(lists.get(i), lists.get(lastIndex - i));
            if (!combinations.contains(mergeCombination)) {
                combinations.add(mergeCombination);
            }
        }
    }

//    CreatingList mutatedCombination = null;

    public CreatingList correctBuildings(BattleManager battleManager, CreatingList creatingList) {
        CreatingList correctedCreatingList = new CreatingList(new ArrayList<>(), 0);
        boolean isConstructedGenerator = false;
        int howCanBuildFactories = battleManager.getHowCanBuildFactories();


        for (PriorityUnit p : creatingList.getPriorityUnitList()) {
            if (p.getUnity().equals(battleManager.getGenerator())) {
                if (isConstructedGenerator) {
                    correctedCreatingList.add(new PolyPriorityUnit(0.0, new Point(0, 0),
                            new Unity(0, 0, "", 0)));
                } else {
                    correctedCreatingList.add(p);
                    isConstructedGenerator = true;
                }
            } else {
                if (p.getUnity().equals(battleManager.getFactory())) {
                    if (howCanBuildFactories > 0) {
                        correctedCreatingList.add(p);
                        howCanBuildFactories--;
                    } else {
                        correctedCreatingList.add(new PolyPriorityUnit(0.0, new Point(0, 0),
                                new Unity(0, 0, "", 0)));
                    }
                } else {
                    correctedCreatingList.add(p);
                }
            }
        }
        return correctedCreatingList;
    }

    @Nullable
    public CreatingList mutate(BattleManager battleManager, int howICanBuild, CreatingList creatingList) {
        CreatingList correctedList = correctBuildings(battleManager, creatingList);

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
                        return howICanBuild <= 2 && !battleManager.isConstructedGenerator() &&
                                battleManager.canConstructBuilding(point, battleManager.getGenerator(), battleManager.getPlayer()) &&
                                battleManager.isEmptyTerritory(point, battleManager.getGenerator());
                    }
                }
        );
        conditionalUnitMap.put("", new ConditionalUnit(battleManager,
                new Unity(0, 0, "", 0),
                (s) -> {
                }, (e) -> {
        }) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return false;
            }
        });

        class RemoveGroup {
            private Point point;
            private Unity unity;
            private String color;

            private RemoveGroup(Point point, Unity unity, String color) {
                this.point = point;
                this.unity = unity;
                this.color = color;
            }
        }

        List<RemoveGroup> removeGroupList = new ArrayList<>();

        bestCombinationOfBuild = new CreatingList(new ArrayList<>(), -10000.0);

        for (PriorityUnit p : correctedList.getPriorityUnitList()) {
            //Идем по всем расположенным юнитам:
            ConditionalUnit conditionalUnit = conditionalUnitMap.get(p.getUnity().getId());
            System.out.println(p.getUnity().getId());
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(conditionalUnit);
            System.out.println(conditionalUnit.isPerformedCondition(p.getPoint()));
            if (!conditionalUnit.isPerformedCondition(p.getPoint())) { //Если условия не выполняются
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                        Point point = new Point(j, i);
                        if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                            for (ConditionalUnit unit : conditionalUnitMap.values()) { //Тогда идем по всем строениям:
                                System.out.println("KEY: " + !unit.getUnity().getId().equals(""));
                                System.out.println("ID: " +  unit.getUnity().getId());
                                if (!unit.getUnity().getId().equals("")) {//Если это не плохой ключ

                                    if (unit.isPerformedCondition(point)) {//Если территория свободна и рядом есть мои строения
                                        PriorityUnit priorityUnit = new PolyProbe().probeBuildingTest(battleManager, unit.getUnity(), point); //Исследуем приоритет на постройку




                                        if (!currentCombinationOfBuild.contains(priorityUnit) &&
                                                battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
                                            removeGroupList.add(new RemoveGroup(point, unit.getUnity(), currentUnity.substring(0, 1)));
                                            currentCombinationOfBuild.add(priorityUnit);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int combinationSize = currentCombinationOfBuild.getPriorityUnitList().size();
        CreatingList mutatedCombination = currentCombinationOfBuild;
        for (int i = 0; i < combinationSize; i++) {
            RemoveGroup g = removeGroupList.get(i);
            PriorityUnit p = currentCombinationOfBuild.getPriorityUnitList().get(i);
            mutatedCombination.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
            currentCombinationOfBuild.removeLast();
            battleManager.removeUnity(g.point, g.unity, g.color);
        }
        return mutatedCombination;
    }


    public CreatingList getBestCombinationOfBuild() {
        System.out.println("Max: " + max);
        return bestCombinationOfBuild;
    }

    public Set<CreatingList> getCombinations() {
        return combinations;
    }

}


