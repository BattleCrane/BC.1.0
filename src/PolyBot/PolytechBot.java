package PolyBot;

import Adjutants.AdjutantFielder;
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

    public static int step = 0;

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
        System.out.println("Buildings: " + buildings);
        if (!controllerMatchMaking.getButtonCreateArmy().isVisible()){
            combination = buildings;
        }

        //Если всё-таки кнопочка с армией открыта -> проверяем армию
        if (controllerMatchMaking.getButtonCreateArmy().isVisible()){
            AdjutantFielder adjutantFielder = new AdjutantFielder();
            adjutantFielder.flush(battleManager);
            adjutantFielder.fillZones(battleManager);
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
        BattleManager battleManager = controllerMatchMaking.getBattleManager();
        Player player = controllerMatchMaking.getBattleManager().getPlayer();

        PolyAttackerProbe polyAttackerProbe = new PolyAttackerProbe();
        return polyAttackerProbe.findAttackSteps(battleManager, player);
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
        System.out.println(combination);
        for (PriorityUnit p: combination.getPriorityUnitList()){
            steps.add(new UnityStep(battleManager, p));
        }
//        steps.addAll(chooseAttacks());
        System.out.println("!");
        return steps;
    }
}
