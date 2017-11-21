package Bots.Priority;

import BattleFields.Point;
public class TemplatePriorityUnit extends PriorityUnit {
    public TemplatePriorityUnit(double priority) {
        super(priority);
    }

    protected TemplatePriorityUnit(char name, double priority, Point point) {
        super(name, priority, point);
    }

    protected TemplatePriorityUnit(char name, double priority, Point point, String typeOfAttack) {
        super(name, priority, point, typeOfAttack);
    }

    protected TemplatePriorityUnit(String inputUnit, double priority, Point point, int width, int height) {
        super(inputUnit, priority, point, width, height);
    }

    public TemplatePriorityUnit(String inputUnit) {
        super(inputUnit);
    }
}
