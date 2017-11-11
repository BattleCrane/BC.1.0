package PolyBot;

import BattleFields.BattleManager;
import Bots.Bot;
import Bots.Steps.Step;
import PolyBot.Priority.PolyMapOfPriority;
import PolyBot.Priority.PolyPriorityUnit;
import PolyBot.Steps.ArmyStep;
import PolyBot.Steps.BonusStep;
import PolyBot.Steps.BuildingStep;
import PolyBot.Steps.TankStep;

import java.util.ArrayList;
import java.util.List;

public class PolytechBot implements Bot{

//    private List<PolyPriorityUnit> showActiveUnits(PolyMapOfPriority polyMapOfPriority){
//
//    }

    private boolean isGoingToBuild(){
        //
        //
        return false;
    }

    @Override
    public List<Step> loadSteps(BattleManager battleManager) {
        List<Step> stepList = new ArrayList<>();
        for (int i = 0; i < battleManager.getPlayer().getEnergy(); i++){
            stepList.add(new BonusStep());
        }
//        for (int i = 0: i < showActiveUnits)




        if (isGoingToBuild()){
            for (int i = 0; i < battleManager.getHowICanBuild(); i++){
                stepList.add(new BuildingStep());
            }
        } else {
            int countOfProductionTanks = battleManager.getHowICanProductTanksLevel1() + battleManager.getHowICanProductTanksLevel2() +
                    battleManager.getHowICanProductTanksLevel3();
            int countOfDraft = battleManager.getHowICanProductArmyLevel1() + battleManager.getHowICanProductArmyLevel1() +
                    battleManager.getHowICanProductArmyLevel1();
            for (int i = 0; i < countOfProductionTanks; i++){
                stepList.add(new TankStep());
            }
            for (int i = 0; i < countOfDraft; i++){
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
}
