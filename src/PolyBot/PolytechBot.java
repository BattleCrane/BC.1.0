package PolyBot;

import BattleFields.BattleManager;
import Bots.Bot;
import Bots.Probes.Probe;
import Bots.Steps.Step;
import Controllers.ControllerMatchMaking;
import PolyBot.PolyCombinations.Army.IteratorArmy.PolyIteratorArmy;
import PolyBot.PolyCombinations.Building.GenesisBuilding.PolyGenesisBuilder;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import PolyBot.Probes.PolyMainProbe;

import java.util.ArrayList;
import java.util.List;

public class PolytechBot implements Bot {


    private ControllerMatchMaking controllerMatchMaking;

    private boolean isGoingToBuild = false; //Буду строить;

    private boolean isGoingToDraft = false; //Буду делать армию;

    private Probe probe = new PolyMainProbe(controllerMatchMaking); //Зонд

//    private PolyAdjutantPriorityField polyAdjutantPriorityField = new PolyAdjutantPriorityField(controllerMatchMaking.getBattleManager());

    //Конструктор:
    public PolytechBot(ControllerMatchMaking controllerMatchMaking) {
        this.controllerMatchMaking = controllerMatchMaking;
    }

    //Определяет, что будет делать:
    private void chooseDevelopment() {
        //Всегда проверяем постройки:
        BattleManager battleManager = controllerMatchMaking.getBattleManager();
        PolyGenesisBuilder polyGenesisBuilder = new PolyGenesisBuilder(battleManager);
        CreatingCombination buildings = polyGenesisBuilder.findBuildCombination(battleManager);
        if (!controllerMatchMaking.getButtonCreateArmy().isVisible()){
            isGoingToBuild = true;
            isGoingToDraft = false;
        }

        //Если всё-таки кнопочка с армией открыта -> проверяем армию
        if (controllerMatchMaking.getButtonCreateArmy().isVisible()){
            PolyIteratorArmy polyIteratorArmy = new PolyIteratorArmy();
            CreatingCombination army = polyIteratorArmy.findCombination(battleManager);
            // Если строения набрали больше
            if (buildings.getSum() > army.getSum()){
                isGoingToBuild = true;
                isGoingToDraft = false;
            // Если армия набрала не меньше
            } else {
                isGoingToBuild = false;
                isGoingToDraft = true;
            }
        }

    }


    @Override
    public List<Step> loadSteps(BattleManager battleManager) {
        //Загрузка бонусных ходов:
        List<Step> stepList = new ArrayList<>();
//        for (int i = 0; i < battleManager.getPlayer().getEnergy(); i++) {
//            stepList.add(new BonusStep());
//        }
//        //Загрузка ходов атакующих юнитов:
//        List<PriorityUnit> priorityUnitList = probe.showActiveUnits(polyAdjutantPriorityField.getMatrix(), battleManager.getPlayer());
//        for (PriorityUnit aPriorityUnitList : priorityUnitList) {
//            stepList.add(new AttackStep(aPriorityUnitList));
//        }
//        //Выбор развития:
//        chooseDevelopment();
//        if (isGoingToBuild) { //Если строим ->
//            for (int i = 0; i < battleManager.getHowICanBuild(); i++) {
//                stepList.add(new ProductionStep());
//            }
//        }
//        if (isGoingToDraft) { //Если делаем армию ->
//            int countOfProductionTanks = battleManager.getHowICanProductTanksLevel1() + battleManager.getHowICanProductTanksLevel2() +
//                    battleManager.getHowICanProductTanksLevel3();
//            int countOfDraft = battleManager.getHowICanProductArmyLevel1() + battleManager.getHowICanProductArmyLevel1() +
//                    battleManager.getHowICanProductArmyLevel1();
//            for (int i = 0; i < countOfProductionTanks; i++) {
//                stepList.add(new TankStep());
//            }
//            for (int i = 0; i < countOfDraft; i++) {
//                stepList.add(new ArmyStep());
//            }
//        }
        return stepList;
    }

    @Override
    public int getCountOfStep() {
        return 0;
    }

    @Override
    public void setCountOfStep(int countOfStep) {

    }


    @Override
    public void setStep(int step) {

    }

    //Показать активных юнитов:


    public boolean isGoingToBuild() {
        return isGoingToBuild;
    }

    public void setGoingToBuild(boolean goingToBuild) {
        isGoingToBuild = goingToBuild;
    }
}
