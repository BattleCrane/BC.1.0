package PolyBot;

import BattleFields.BattleManager;
import Bots.Bot;
import Bots.PriorityUnit;
import Bots.Steps.Step;
import Controllers.ControllerMatchMaking;
import Players.Player;
import PolyBot.Priority.PolyAdjutantPriorityField;
import PolyBot.Priority.PolyMapOfPriority;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Steps.*;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class PolytechBot implements Bot {

    private BattleManager battleManager;

    private boolean isGoingToBuild = false; //Буду строить;

    private boolean isGoingToDraft = false; //Буду делать армию;

    private PolyAdjutantPriorityField polyAdjutantPriorityField = new PolyAdjutantPriorityField(battleManager);

    //Конструктор:
    public PolytechBot(ControllerMatchMaking controllerMatchMaking) {
        this.battleManager = controllerMatchMaking.getBattleManager();
    }

    //Определяет, что будет делать:
    private void chooseDevelopment() {

    }


    @Override
    public List<Step> loadSteps(BattleManager battleManager) {
        //Загрузка бонусных ходов:
        List<Step> stepList = new ArrayList<>();
        for (int i = 0; i < battleManager.getPlayer().getEnergy(); i++) {
            stepList.add(new BonusStep());
        }
        //Загрузка ходов атакующих юнитов:
        List<PriorityUnit> priorityUnitList = showActiveUnits(polyAdjutantPriorityField, battleManager.getPlayer());
        for (PriorityUnit aPriorityUnitList : priorityUnitList) {
            stepList.add(new AttackStep(aPriorityUnitList));
        }
        //Выбор развития:
        chooseDevelopment();
        if (isGoingToBuild) { //Если строим ->
            for (int i = 0; i < battleManager.getHowICanBuild(); i++) {
                stepList.add(new BuildingStep());
            }
        }
        if (isGoingToDraft) { //Если делаем армию ->
            int countOfProductionTanks = battleManager.getHowICanProductTanksLevel1() + battleManager.getHowICanProductTanksLevel2() +
                    battleManager.getHowICanProductTanksLevel3();
            int countOfDraft = battleManager.getHowICanProductArmyLevel1() + battleManager.getHowICanProductArmyLevel1() +
                    battleManager.getHowICanProductArmyLevel1();
            for (int i = 0; i < countOfProductionTanks; i++) {
                stepList.add(new TankStep());
            }
            for (int i = 0; i < countOfDraft; i++) {
                stepList.add(new ArmyStep());
            }
        }
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
    @Contract(pure = true)
    private List<PriorityUnit> showActiveUnits(PolyAdjutantPriorityField polyAdjutantPriorityField, Player player) {
        List<PriorityUnit> listOfPriorityUnit = new ArrayList<>();
        List<List<PriorityUnit>> matrix = polyAdjutantPriorityField.getMatrix();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                PriorityUnit priorityUnit = matrix.get(j).get(i);
                if (priorityUnit.getColor() == player.getColorType().charAt(0) && priorityUnit.isActive()) {
                    listOfPriorityUnit.add(priorityUnit);
                }
            }
        }
        return listOfPriorityUnit;
    }

    public boolean isGoingToBuild() {
        return isGoingToBuild;
    }

    public void setGoingToBuild(boolean goingToBuild) {
        isGoingToBuild = goingToBuild;
    }
}
