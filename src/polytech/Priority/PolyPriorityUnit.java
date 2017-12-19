package polytech.Priority;

import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Unities.Unity;

public class PolyPriorityUnit extends PriorityUnit {

    public PolyPriorityUnit(double priority, Point point, Unity unity){
        super(priority, point, unity);
    }

    public PolyPriorityUnit(double priority){
        super(priority);
    }

    public PolyPriorityUnit(char type, double priority, Point point){
        super(type, priority, point);
    }
//
//    public PolyPriorityUnit(char inputUnit, double priority, Point point, String typeOfAttack) {
//        super(inputUnit, priority, point, typeOfAttack);
//    }
//
//    public PolyPriorityUnit(String inputUnit){
//        super(inputUnit);
//    }
}
