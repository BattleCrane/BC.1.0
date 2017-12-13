package PolyBot.PolyCombinations.Building.GenesisBuilding;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import PolyBot.PolyCombinations.CreatingTools.EstimatedUnit;
import PolyBot.Probes.PolyMainProbe;

import java.util.*;

/**
 * PolyGenesisBuilder - класс, реализующий выбор наилучшей комбинации построек на основе генетического алгоритма.
 * Содержит в себе поля, которые используются в методах.
 * 1. estimatedUnitMap - карта построек.
 * 2. combinations - различные комбинации на данном этапе отбора
 * 3. bestCombinationOfBuild - лучшая комбинация из прямого перебора. Используется при мутации
 * 4. currentCombinationOfBuild - текущая комбинация из прямого перебора. Используется при мутации.
 */

public class PolyGenesisBuilder {

    private final Map<String, EstimatedUnit> estimatedUnitMap = new HashMap<>();
    private Set<CreatingCombination> combinations = new HashSet<>();
    private CreatingCombination bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
    private CreatingCombination currentCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);

    public PolyGenesisBuilder(BattleManager battleManager) {
        initUnitMap(battleManager);
    }

    //Инициализация строений:
    private void initUnitMap(BattleManager battleManager) {
        //Barracks:
        estimatedUnitMap.put("b", new EstimatedUnit(battleManager, battleManager.getBarracks(), (s) -> {
        }, (e) -> {
        }) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                        battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer());
            }
        });
        //Factory:
        estimatedUnitMap.put("f", new EstimatedUnit(battleManager, battleManager.getFactory(),
                (s) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() - 1),
                (e) -> battleManager.setHowICanBuildFactories(battleManager.getHowCanBuildFactories() + 1)) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return battleManager.getHowCanBuildFactories() > 0 && battleManager.canConstructBuilding(point,
                        battleManager.getFactory(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getFactory());
            }
        });
        //Generator
        estimatedUnitMap.put("g", new EstimatedUnit(battleManager, battleManager.getGenerator(),
                (s) -> battleManager.setConstructedGenerator(true),
                (e) -> battleManager.setConstructedGenerator(false)) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return battleManager.getHowICanBuild() <= 2 && !battleManager.isConstructedGenerator() &&
                        battleManager.canConstructBuilding(point, battleManager.getGenerator(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getGenerator());
            }
        });
        //Wall:
        estimatedUnitMap.put("w", new EstimatedUnit(battleManager, battleManager.getWall(),
                (s) -> {},
                (e) -> {}) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return  battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getBarracks());
            }
        });
        //Turret:
        estimatedUnitMap.put("t", new EstimatedUnit(battleManager, battleManager.getTurret(),
                (s) -> {},
                (e) -> {}) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return  battleManager.canConstructBuilding(point, battleManager.getTurret(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getTurret());
            }
        });
    }

    //Найти лучшую комбинацию:
    public CreatingCombination findBuildCombination(BattleManager battleManager) {

        combinations = new HashSet<>();
        bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
        currentCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);

        int population = 200; //Всего комбинаций:
        int selection = 5; //Всего уровней отборов:
        createPopulation(battleManager, population);
        for (int i = 0; i < selection; i++) {
            mergeAndMutatePopulation(battleManager);
            arrangeTournament();
        }
        return chooseTheBest();
    }

    //Создание популяции:
    public void createPopulation(BattleManager battleManager, int population) {
        for (int i = 0; i < population; i++) {
            randomCombination(battleManager);
        }
    }

    //Создание популяции:
    private void randomCombination(BattleManager battleManager) {

        boolean isConstructed = false;
        while (!isConstructed) { //Пока все строения не построятся
            int x = new Random().nextInt(16); //Любой ряд
            int y = new Random().nextInt(16); //Любая колонка
            if (x == 16) { //Поле 16х16 (0-15)
                x = 0;
            }
            if (y == 16) {
                y = 0;
            }
            //Значение клетки ввиде строки, в которой хранится информация о сущности:
            String currentUnity = battleManager.getBattleField().getMatrix().get(x).get(y);
            Point point = new Point(y, x);
            if (currentUnity.substring(1).equals("    0")) { //Если клетка пустая ->
                int i = new Random().nextInt(estimatedUnitMap.size()); //Берем любое строение
                if (i == 3) {
                    i = 0;
                }
                List<EstimatedUnit> estimatedUnits = new ArrayList<>(); //Создаем лист, чтобы обратится к индексу постройки
                estimatedUnits.addAll(estimatedUnitMap.values());
                EstimatedUnit estimatedUnit = estimatedUnits.get(i);
                if (estimatedUnit.isPerformedCondition(point)) { //Если это строение можно построить
                    PriorityUnit priorityUnit = new PolyMainProbe().probeBuildingTest(battleManager, estimatedUnit.getUnity(), point); //Исследуем значение приспосабливаемости
                    if (!currentCombinationOfBuild.contains(priorityUnit) && battleManager.putUnity(battleManager.getPlayer(), point,
                            priorityUnit.getUnity())) { //Если такого юнита в комбинации нет, и построилось строение
                        isConstructed = true; //Этот цикл завершен
                        currentCombinationOfBuild.add(priorityUnit);
                        battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1); //Теперь можно строить на одно строение меньше
                        estimatedUnit.takeResources().run(battleManager); //Забираем ресурсы
                        if (battleManager.getHowICanBuild() > 0) { //Если еще можно строить
                            randomCombination(battleManager);
                        } else {
                            if (!combinations.contains(currentCombinationOfBuild)) { //Если такой комбинации ещё не было
                                CreatingCombination combination = new CreatingCombination(new ArrayList<>(), 0);
                                for (PriorityUnit p : currentCombinationOfBuild.getPriorityUnitList()) {
                                    combination.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
                                }
                                combinations.add(combination);
                            }
                        }
                        battleManager.setHowICanBuild(battleManager.getHowICanBuild() + 1); //Вернули постройку
                        currentCombinationOfBuild.removeLast(); //Очистили последнее строение
                        battleManager.removeUnity(point, estimatedUnit.getUnity(), currentUnity.substring(0, 1)); // Убрали последнее строение с поля
                        estimatedUnit.returnResources().run(battleManager); //Вернули ресурсы
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

    private void mergeAndMutatePopulation(BattleManager battleManager) {
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

    private void nextMutate(BattleManager battleManager, int step, CreatingCombination mergedList) {

        if (step < mergedList.getPriorityUnitList().size()) {
            //Идем по всем расположенным юнитам:
            PriorityUnit p = mergedList.getPriorityUnitList().get(step);
            EstimatedUnit estimatedUnit = estimatedUnitMap.get(p.getUnity().getId());

            if (!estimatedUnit.isPerformedCondition(p.getPoint())) { //Если условия не выполняются
                String flag = ""; //Определитель территории
                for (int i = 0; i < 16; i++) { //Ищем сами лучший вариант
                    for (int j = 0; j < 16; j++) {
                        String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                        Point point = new Point(j, i);
                        if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                            for (EstimatedUnit unit : estimatedUnitMap.values()) { //Тогда перебираем все строения:

                                if (unit.isPerformedCondition(point)) {//Если территория свободна и рядом есть мои строения
                                    PriorityUnit priorityUnit = new PolyMainProbe().probeBuildingTest(battleManager, unit.getUnity(), point); //Исследуем приоритет на постройку
                                    if (!currentCombinationOfBuild.contains(priorityUnit) &&
                                            battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке, и построилось строение
                                        currentCombinationOfBuild.add(priorityUnit);
                                        unit.takeResources().run(battleManager);
                                        if (currentCombinationOfBuild.getSum() > bestCombinationOfBuild.getSum()) {
                                            bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0);
                                            for (PriorityUnit e : currentCombinationOfBuild.getPriorityUnitList()) {
                                                bestCombinationOfBuild.add(new PolyPriorityUnit(e.getPriority(), e.getPoint(), e.getUnity()));
                                            }
                                            flag = currentUnity.substring(0, 1);
                                        }
                                        unit.returnResources().run(battleManager);
                                        currentCombinationOfBuild.removeLast();
                                        battleManager.removeUnity(point, unit.getUnity(), currentUnity.substring(0, 1));
                                    }
                                }
                            }
                        }
                    }
                }

                PriorityUnit newPriorityUnit = bestCombinationOfBuild.getPriorityUnitList().get(
                        bestCombinationOfBuild.getPriorityUnitList().size() - 1
                );

                currentCombinationOfBuild.add(new PolyPriorityUnit(newPriorityUnit.getPriority(), newPriorityUnit.getPoint(),
                        newPriorityUnit.getUnity()));

                battleManager.putUnity(battleManager.getPlayer(), newPriorityUnit.getPoint(), newPriorityUnit.getUnity());
                EstimatedUnit newEstimatedUnit = estimatedUnitMap.get(newPriorityUnit.getUnity().getId());
                newEstimatedUnit.takeResources().run(battleManager);
                int nextStep = step + 1;
                nextMutate(battleManager, nextStep, mergedList);
                newEstimatedUnit.returnResources().run(battleManager);
                battleManager.removeUnity(newPriorityUnit.getPoint(), newPriorityUnit.getUnity(), flag);

            } else {
                bestCombinationOfBuild.add(p);
                currentCombinationOfBuild.add(p);
                String flag = battleManager.getBattleField().getMatrix().get(p.getPoint().X()).get(p.getPoint().Y()).substring(0, 1);
                battleManager.putUnity(battleManager.getPlayer(), p.getPoint(), p.getUnity());
                estimatedUnit.takeResources().run(battleManager);
                int nextStep = step + 1;
                nextMutate(battleManager, nextStep, mergedList);
                estimatedUnit.returnResources().run(battleManager);
                battleManager.removeUnity(p.getPoint(), p.getUnity(), flag);
            }
        }
    }

    private void arrangeTournament() {
        List<CreatingCombination> combinationList = new ArrayList<>();
        combinationList.addAll(combinations);
        int lastIndex = combinationList.size() - 1;
        for (int i = 0; i <= lastIndex / 2; i++) {
            if (combinationList.get(i).getSum() > combinationList.get(lastIndex - i).getSum()) {
                combinations.remove(combinationList.get(lastIndex - i));
            } else {
                combinations.remove(combinationList.get(i));
            }
        }
    }

    private CreatingCombination chooseTheBest() {
        CreatingCombination best = new CreatingCombination(new ArrayList<>(), 0);
        for (CreatingCombination combination : combinations) {
            if (combination.getSum() > best.getSum()) {
                best = combination;
            }
        }
        return best;
    }

    public Set<CreatingCombination> getCombinations() {
        return combinations;
    }
}


