package PolyBot.Priority;

import BattleFields.Point;
import Bots.Priority.PriorityUnit;

public class PolyPriorityUnit extends PriorityUnit {


    public PolyPriorityUnit(double priority){
        super(priority);
    }

    public PolyPriorityUnit(char type, double priority, Point point){
        super(type, priority, point);
    }

    public PolyPriorityUnit(char inputUnit, double priority, Point point, String typeOfAttack) {
        super(inputUnit, priority, point, typeOfAttack);
    }

    public PolyPriorityUnit(String inputUnit){
        super(inputUnit);
    }
}
