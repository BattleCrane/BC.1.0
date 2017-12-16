package PolyBot.PolyCombinations.Building.GenesisBuilding;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.PolyCombinations.Building.IteratorBuilding.PolyIteratorBuilder;
import PolyBot.PolyCombinations.upgrading.PolyUpgrading;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import PolyBot.PolyCombinations.CreatingTools.EstimatedUnit;
import PolyBot.Probes.PolyMainProbe;
import Unities.Unity;

import java.util.*;
import java.util.function.Function;

/**
 * PolyGenesisBuilder - класс, реализующий выбор наилучшей комбинации построек на основе генетического алгоритма.
 * Содержит в себе поля, которые используются в методах.
 * 1. estimatedUnitMap - карта построек.
 * 2. combinations - различные комбинации на данном этапе отбора
 * 3. bestCombinationOfBuild - лучшая комбинация из прямого перебора. Используется при мутации
 * 4. currentCombinationOfBuild - текущая комбинация из прямого перебора. Используется при мутации.
 */

public class PolyGenesisBuilder {

    private interface Predicate{
        void run(PriorityUnit p);
    }

    private final Map<String, EstimatedUnit> estimatedUnitMap = new HashMap<>();
    private Set<CreatingCombination> combinations = new HashSet<>();
    private CreatingCombination bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
    private CreatingCombination currentCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
    private CreatingCombination bestUpgradeCombination;
    private List<EstimatedUnit> estimatedUnits = null;
    private CreatingCombination turretCombinations;

    public PolyGenesisBuilder(BattleManager battleManager) {
        initUnitMap(battleManager);
        PolyUpgrading polyUpgrading = new PolyUpgrading(battleManager);
        bestUpgradeCombination = polyUpgrading.findCombination();

    }

    private int controllerBuilding;

    //Инициализация строений:
    private void initUnitMap(BattleManager battleManager) {
        controllerBuilding = battleManager.getHowICanBuild();
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
                return controllerBuilding <= 2 && !battleManager.isConstructedGenerator() &&
                        battleManager.canConstructBuilding(point, battleManager.getGenerator(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getGenerator());
            }
        });
        //Wall:
        estimatedUnitMap.put("w", new EstimatedUnit(battleManager, battleManager.getWall(),
                (s) -> {
                },
                (e) -> {
                }) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getBarracks());
            }
        });
        //Turret:
        estimatedUnitMap.put("t", new EstimatedUnit(battleManager, battleManager.getTurret(),
                (s) -> {
                },
                (e) -> {
                }) {
            @Override
            public boolean isPerformedCondition(Point point) {
                return battleManager.canConstructBuilding(point, battleManager.getTurret(), battleManager.getPlayer()) &&
                        battleManager.isEmptyTerritory(point, battleManager.getTurret());
            }
        });
        estimatedUnits = Arrays.asList(estimatedUnitMap.get("g"), estimatedUnitMap.get("b"), estimatedUnitMap.get("f"));
    }

    //Найти лучшую комбинацию:
    public CreatingCombination findBuildCombination(BattleManager battleManager) {
        PolyIteratorBuilder polyIteratorBuilder = new PolyIteratorBuilder();
        polyIteratorBuilder.findTurretCombination(battleManager);
        turretCombinations = polyIteratorBuilder.getBestCombinationOfBuild();

        combinations = new HashSet<>();
        bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
        currentCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);

        int population = 100; //Всего комбинаций:
        int selection = 5; //Всего уровней отборов:
        createPopulation(battleManager, population);
//        System.out.println("population " + combinations);
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
                int i = new Random().nextInt(estimatedUnitMap.size() - 2); //Берем любое строение, кроме турели и стены
                EstimatedUnit estimatedUnit = estimatedUnits.get(i);

                if (estimatedUnit.isPerformedCondition(point)) { //Если это строение можно построить
                    PriorityUnit priorityUnit;

                    switch (estimatedUnit.getUnity().getId()) {
                        //Исследуем значение приспосабливаемости:
                        case "t":
                            priorityUnit = new PolyMainProbe().probeRadiusUnitTest(battleManager, battleManager.getTurret(), point);
                            break;
                        case "w":
                            priorityUnit = new PolyMainProbe().probeBuildingTest(battleManager, estimatedUnit.getUnity(), point);
                            break;
                        default:
                            priorityUnit = new PolyMainProbe().probeBuildingTest(battleManager, estimatedUnit.getUnity(), point);
                            break;
                    }

                    class WallChecker{
                        private boolean check(){
                            if (estimatedUnit.getUnity().getId().equals("w")){
                                return battleManager.putDoubleWall(battleManager.getPlayer(), point, battleManager.getWall());
                            } else {
                                return battleManager.putUnity(battleManager.getPlayer(), point,
                                        estimatedUnit.getUnity());
                            }
                        }
                    }

                    WallChecker wallChecker = new WallChecker();

                    if (!currentCombinationOfBuild.contains(priorityUnit) && wallChecker.check()) { //Если такого юнита в комбинации нет, и построилось строение
                        isConstructed = true; //Этот цикл завершен
                        currentCombinationOfBuild.add(priorityUnit);
                        battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1); //Теперь можно строить на одно строение меньше
                        estimatedUnit.takeResources().run(battleManager); //Забираем ресурсы
                        if (battleManager.getHowICanBuild() > 0) { //Если еще можно строить
                            randomCombination(battleManager);
                        } else {
                            if (!combinations.contains(currentCombinationOfBuild)) { //Если такой комбинации ещё не было
                                CreatingCombination combination = new CreatingCombination(new ArrayList<>(), 0);
                                for (PriorityUnit p : currentCombinationOfBuild.getUnits()) {
                                    combination.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
                                }
                                combinations.add(combination);
                            }
                        }
                        battleManager.setHowICanBuild(battleManager.getHowICanBuild() + 1); //Вернули постройку
                        currentCombinationOfBuild.removeLast(); //Очистили последнее строение
                        if (estimatedUnit.getUnity().getId().equals("w")){
                            battleManager.removeUnity(point, battleManager.getBarracks(), currentUnity.substring(0, 1));
                        } else {
                            battleManager.removeUnity(point, estimatedUnit.getUnity(), currentUnity.substring(0, 1));
                        }
                        // Убрали последнее строение с поля
                        estimatedUnit.returnResources().run(battleManager); //Вернули ресурсы
                    }
                }
            }
        }
    }

    public CreatingCombination merge(CreatingCombination creatingCombination, CreatingCombination otherCreatingCombination) {
        int combinationSize = creatingCombination.getUnits().size();
        int otherCombinationSize = otherCreatingCombination.getUnits().size();
        int size = combinationSize > otherCombinationSize ? combinationSize : otherCombinationSize;
        CreatingCombination mergedCombination = new CreatingCombination(new ArrayList<>(), 0);
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0 && combinationSize >= size) {
                PriorityUnit priorityUnit = creatingCombination.getUnits().get(i);
                mergedCombination.add(new PolyPriorityUnit(priorityUnit.getPriority(), priorityUnit.getPoint(), priorityUnit.getUnity()));
            } else {
                PriorityUnit priorityUnit = otherCreatingCombination.getUnits().get(i);
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

        if (step < mergedList.getUnits().size()) {
            //Идем по всем расположенным юнитам:
            PriorityUnit p = mergedList.getUnits().get(step);
            EstimatedUnit estimatedUnit = estimatedUnitMap.get(p.getUnity().getId());

            if (!estimatedUnit.isPerformedCondition(p.getPoint())) { //Если условия не выполняются, тогда рассматриваем альтернативный вариант строения и улучшения

                //Проверка на лучшее улучшение и добавление комбинации

                String flag = ""; //Определитель территории
                for (int i = 0; i < 16; i++) { //Ищем сами лучший вариант
                    for (int j = 0; j < 16; j++) {
                        String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                        Point point = new Point(j, i);
                        if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                            for (EstimatedUnit unit : estimatedUnitMap.values()) { //Тогда перебираем все строения:

                                if (unit.isPerformedCondition(point)) {//Если территория свободна и рядом есть мои строения
                                    PriorityUnit priorityUnit;
                                    switch (unit.getUnity().getId()) {
                                        //Исследуем значение приспосабливаемости:
                                        case "t":
                                            priorityUnit = new PolyMainProbe().probeRadiusUnitTest(battleManager, battleManager.getTurret(), point);
                                            break;
                                        case "w":
                                            priorityUnit = new PolyMainProbe().probeBuildingTest(battleManager, battleManager.getWall(), point);
                                            break;
                                        default:
                                            priorityUnit = new PolyMainProbe().probeBuildingTest(battleManager, unit.getUnity(), point);
                                            break;
                                    }

                                    class WallChecker{
                                        private boolean check(){
                                            if (unit.getUnity().getId().equals("w")){
                                                return battleManager.putDoubleWall(battleManager.getPlayer(), point, battleManager.getWall());
                                            } else {
                                                return battleManager.putUnity(battleManager.getPlayer(), point, unit.getUnity());
                                            }
                                        }
                                    }

                                    WallChecker wallChecker = new WallChecker();

                                    if (!currentCombinationOfBuild.contains(priorityUnit) && wallChecker.check()) { //Если нет в текущем списке, и построилось строение
                                        currentCombinationOfBuild.add(priorityUnit);
                                        unit.takeResources().run(battleManager);
                                        if (currentCombinationOfBuild.getSum() > bestCombinationOfBuild.getSum()) {
                                            bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0);
                                            for (PriorityUnit e : currentCombinationOfBuild.getUnits()) {
                                                bestCombinationOfBuild.add(new PolyPriorityUnit(e.getPriority(), e.getPoint(), e.getUnity()));
                                            }
                                            flag = currentUnity.substring(0, 1);
                                        }
                                        unit.returnResources().run(battleManager);
                                        currentCombinationOfBuild.removeLast();
                                        if (unit.getUnity().getId().equals("w")){
                                            battleManager.removeUnity(point, battleManager.getBarracks(), currentUnity.substring(0, 1));
                                        } else {
                                            battleManager.removeUnity(point, unit.getUnity(), currentUnity.substring(0, 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                PriorityUnit newPriorityUnit;
                Predicate predicateStart;
                Predicate predicateEnd;
                PriorityUnit lastCreated = bestCombinationOfBuild.getUnits()
                        .get(bestCombinationOfBuild.getUnits().size() - 1);
                PriorityUnit currentUpgraded;
                if (bestUpgradeCombination.getUnits().size() - 1 > 0){
                    currentUpgraded = bestUpgradeCombination.getUnits().get(0);
                } else {
                    currentUpgraded = new PolyPriorityUnit(-10.0);
                }
                if (lastCreated.getPriority() > currentUpgraded.getPriority()){
                    newPriorityUnit = lastCreated;
                    predicateStart = o -> {
                        if (o.getUnity().getId().equals("w")){
                            battleManager.putDoubleWall(battleManager.getPlayer(), o.getPoint(), battleManager.getWall());
                        } else {
                            battleManager.putUnity(battleManager.getPlayer(), o.getPoint(),
                                    o.getUnity());
                        }
                    };
                    String f = flag;
                    predicateEnd = o -> {
                        if (o.getUnity().getId().equals("w")){
                            battleManager.removeUnity(o.getPoint(), battleManager.getBarracks(), f);

                        } else {
                            battleManager.removeUnity(o.getPoint(), o.getUnity(), f);
                        }
                    };
                } else {
                    newPriorityUnit = currentUpgraded;
                    predicateStart = o -> battleManager.upgradeBuilding(o.getPoint(), battleManager.getPlayer());
                    predicateEnd = o -> battleManager.aggravateUnit(o.getPoint(), o.getUnity());
                }
                currentCombinationOfBuild.add(new PolyPriorityUnit(newPriorityUnit.getPriority(), newPriorityUnit.getPoint(),
                        newPriorityUnit.getUnity()));
                predicateStart.run(newPriorityUnit);
                EstimatedUnit newEstimatedUnit = estimatedUnitMap.get(newPriorityUnit.getUnity().getId());
                newEstimatedUnit.takeResources().run(battleManager);
                int nextStep = step + 1;
                nextMutate(battleManager, nextStep, mergedList);
                newEstimatedUnit.returnResources().run(battleManager);
                predicateEnd.run(newPriorityUnit);
            } else {
                bestCombinationOfBuild.add(p);
                currentCombinationOfBuild.add(p);
                String flag = battleManager.getBattleField().getMatrix().get(p.getPoint().X()).get(p.getPoint().Y()).substring(0, 1);

                class WallChecker{
                    private void putUnity(){
                        if (p.getUnity().getId().equals("w")){
                            battleManager.putDoubleWall(battleManager.getPlayer(), p.getPoint(), battleManager.getWall());
                        } else {
                            battleManager.putUnity(battleManager.getPlayer(), p.getPoint(), p.getUnity());
                        }
                    }
                }

                WallChecker wallChecker = new WallChecker();

                wallChecker.putUnity();
                estimatedUnit.takeResources().run(battleManager);
                int nextStep = step + 1;
                nextMutate(battleManager, nextStep, mergedList);
                estimatedUnit.returnResources().run(battleManager);

                if (p.getUnity().getId().equals("w")){
                    battleManager.removeUnity(p.getPoint(), battleManager.getBarracks(), flag);
                } else {
                    battleManager.removeUnity(p.getPoint(), p.getUnity(), flag);
                }
            }
        }
    }

    private void arrangeTournament() {
        List<CreatingCombination> combinationList = new ArrayList<>();
        combinationList.addAll(combinations);
        int lastIndex = combinationList.size() - 1;
        for (int i = 0; i <= lastIndex / 2; i++) {
            if (combinations.size() == 1) {
                break;
            }
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
        CreatingCombination combination;
        if (best.getSum() > bestUpgradeCombination.getSum()){
            combination = best;
        } else {
            combination = bestUpgradeCombination;
        }
        if (combination.getSum() > turretCombinations.getSum()){
            return combination;
        } else {
            return turretCombinations;
        }


    }

    public Set<CreatingCombination> getCombinations() {
        return combinations;
    }
}


