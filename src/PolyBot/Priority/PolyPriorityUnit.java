package PolyBot.Priority;

import BattleFields.Point;
import Bots.PriorityUnit;

public class PolyPriorityUnit extends PriorityUnit {


    public PolyPriorityUnit(double priority){
        super(priority);
    }

    public PolyPriorityUnit(char type, double priority, Point point){
        super(type, priority, point);
    }

    public PolyPriorityUnit(String inputUnit, double priority, Point point, String typeOfAttack) {
        super(inputUnit, priority, point);
    }
}
