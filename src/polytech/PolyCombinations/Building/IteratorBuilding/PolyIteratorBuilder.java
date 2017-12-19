package polytech.PolyCombinations.Building.IteratorBuilding;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import polytech.PolyCombinations.CreatingTools.CreatingCombination;
import polytech.PolyCombinations.CreatingTools.EstimatedUnit;
import polytech.Priority.PolyPriorityUnit;
import polytech.polyNexus.Probes.PolyBuildingProbe;
import polytech.polyNexus.Probes.PolyMainProbe;
import polytech.polyNexus.Probes.PolyRadiusProbe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolyIteratorBuilder {
    private final BattleManager battleManager;
    private final PolyBuildingProbe buildingProbe;
    private final PolyRadiusProbe radiusProbe;

    public PolyIteratorBuilder(BattleManager battleManager, PolyBuildingProbe buildingProbe, PolyRadiusProbe radiusProbe) {
        this.battleManager = battleManager;
        this.buildingProbe = buildingProbe;
        this.radiusProbe = radiusProbe;
    }


    private CreatingCombination bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
    private CreatingCombination currentCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0.0);
    private double max = 0.0;

    private List<EstimatedUnit> conditionalUnitList = null;

    private void initAllBuildings(BattleManager battleManager){
        int howICanBuild = battleManager.getHowICanBuild();
        conditionalUnitList =  Arrays.asList(
                //Barracks:
                new EstimatedUnit(battleManager, battleManager.getBarracks(), (s) -> {}, (e) -> {}) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return battleManager.isEmptyTerritory(point, battleManager.getBarracks()) &&
                                battleManager.canConstructBuilding(point, battleManager.getBarracks(), battleManager.getPlayer());
                    }
                },
                //Factory:
                new EstimatedUnit(battleManager, battleManager.getFactory(),
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
                new EstimatedUnit(battleManager, battleManager.getGenerator(),
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
    }

    private void initTurret(BattleManager battleManager){
        conditionalUnitList = Arrays.asList(
                //Turret:
                new EstimatedUnit(battleManager, battleManager.getTurret(), (s) -> {}, (e) -> {}) {
                    @Override
                    public boolean isPerformedCondition(Point point) {
                        return battleManager.isEmptyTerritory(point, battleManager.getTurret()) &&
                                battleManager.canConstructBuilding(point, battleManager.getTurret(), battleManager.getPlayer());
                    }
                }
        );
    }
    public void findCombination(BattleManager battleManager){
        bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0);
        int howICanBuild = battleManager.getHowICanBuild();
        initAllBuildings(battleManager);
        findCombination(battleManager, howICanBuild);
    }

    public void findTurretCombination(BattleManager battleManager){
        bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0);
        int howICanBuild = battleManager.getHowICanBuild();
        initTurret(battleManager);
        findCombination(battleManager, howICanBuild);
    }

    private void findCombination(BattleManager battleManager, int howICanBuild) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(j);
                Point point = new Point(j, i);
                if (currentUnity.substring(1).equals("    0")) { //Если пустая клетка
                    for (EstimatedUnit unit : conditionalUnitList) {
                        if (unit.isPerformedCondition(point)) {
                            checkConditionalUnit(battleManager, unit, point, currentUnity, howICanBuild);
                        }
                    }
                }
            }
        }
    }

    private void checkConditionalUnit(BattleManager battleManager, EstimatedUnit conditionalUnit, Point point, String currentUnity, int howICanBuild) {
        if (conditionalUnit.isPerformedCondition(point)) {//Если территория свободна и рядом есть мои строения
            PriorityUnit priorityUnit;
            if (conditionalUnit.getUnity().getId().equals("t")){
                System.out.println("TRUE");
                priorityUnit = new PolyMainProbe().probeRadiusUnitTest(battleManager, conditionalUnit.getUnity(), point); //Исследуем приоритет на турель
            } else {
                priorityUnit = new PolyMainProbe().probeBuildingTest(battleManager, conditionalUnit.getUnity(), point);
            }
            if (!currentCombinationOfBuild.contains(priorityUnit) && battleManager.putUnity(battleManager.getPlayer(), point, priorityUnit.getUnity())) { //Если нет в текущем списке и построилось строение
                currentCombinationOfBuild.add(priorityUnit);
                int nextBuild = howICanBuild - 1;
                conditionalUnit.takeResources().run(battleManager);
                if (nextBuild > 0) {
                    findCombination(battleManager, nextBuild);
                } else {
                    if (currentCombinationOfBuild.getSum() > bestCombinationOfBuild.getSum()) {
                        max = currentCombinationOfBuild.getSum();
                        bestCombinationOfBuild = new CreatingCombination(new ArrayList<>(), 0);
                        for (PriorityUnit p : currentCombinationOfBuild.getUnits()) {
                            bestCombinationOfBuild.add(new PolyPriorityUnit(p.getPriority(), p.getPoint(), p.getUnity()));
                        }
                    }
                }
                currentCombinationOfBuild.removeLast();
                battleManager.removeUnity(point, conditionalUnit.getUnity(), currentUnity.substring(0, 1));
                conditionalUnit.returnResources().run(battleManager);
            }
        }
    }

    public CreatingCombination getBestCombinationOfBuild() {
        System.out.println("Max: " + max);
        return bestCombinationOfBuild;
    }

}