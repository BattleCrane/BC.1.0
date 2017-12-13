package PolyBot;

import BattleFields.BattleManager;
import Bots.Bot;
import Bots.Priority.PriorityUnit;
import Bots.Steps.AttackStep;
import Bots.Steps.Step;
import Bots.Steps.UnityStep;
import Controllers.ControllerMatchMaking;
import Players.Player;
import PolyBot.PolyCombinations.Army.IteratorArmy.PolyIteratorArmy;
import PolyBot.PolyCombinations.Building.GenesisBuilding.PolyGenesisBuilder;
import PolyBot.PolyCombinations.CreatingTools.CreatingCombination;
import PolyBot.Probes.PolyAttackerProbe;

import java.util.ArrayList;
import java.util.List;

public class PolytechBot implements Bot {
    private ControllerMatchMaking controllerMatchMaking;
    private CreatingCombination combination;

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
            combination = buildings;
        }

        //Если всё-таки кнопочка с армией открыта -> проверяем армию
        if (controllerMatchMaking.getButtonCreateArmy().isVisible()){
            PolyIteratorArmy polyIteratorArmy = new PolyIteratorArmy();
            CreatingCombination army = polyIteratorArmy.findCombination(battleManager);
            // Если строения набрали больше
            if (buildings.getSum() > army.getSum()){
                combination = buildings;
            // Если армия набрала не меньше
            } else {
                combination = army;
            }
        }
    }

    private List<AttackStep> chooseAttacks() {
        List<List<String>> matrix = controllerMatchMaking.getBattleManager().getBattleField().getMatrix();
        Player player = controllerMatchMaking.getBattleManager().getPlayer();

        PolyAttackerProbe polyAttackerProbe = new PolyAttackerProbe();
        return polyAttackerProbe.findAttackSteps(matrix, player);
    }



    @Override
    public int getCountOfStep() {
        return 0;
    }

    @Override
    public void setCountOfStep(int countOfStep) {

    }

    @Override
    public List<Step> loadSteps(BattleManager battleManager) {
        List<Step> steps = new ArrayList<>();
        chooseDevelopment(); //Выбор постройки
        for (PriorityUnit p: combination.getPriorityUnitList()){
            steps.add(new UnityStep(battleManager, p));
        }
        steps.addAll(chooseAttacks());

        return steps;
    }
}
