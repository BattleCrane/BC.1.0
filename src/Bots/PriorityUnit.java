package Bots;

import BattleFields.Point;

public abstract class PriorityUnit {
    private String inputUnit;
    private int priority;
    private int hitPoints;
    private int level;
    private boolean isActive;
    private char color;
    private char name;
    private boolean isRoot;
    private String type;
    private Point point;

    protected PriorityUnit(String inputUnit, int priority, Point point){
        this.inputUnit = inputUnit;
        this.hitPoints = inputUnit.charAt(0);
        this.level = inputUnit.charAt(1) == '^' ? 1 : inputUnit.charAt(1) == '<' ? 2 : 3;
        this.isActive = inputUnit.charAt(2) != '?';
        this.color = inputUnit.charAt(3);
        this.name = inputUnit.charAt(4);
        this.isRoot = inputUnit.charAt(5) != '.';
        this.priority = priority;
        this.type = "unchecked";
        this.point = point;
    }


    public String getInputUnit() {
        return inputUnit;
    }

    public void setInputUnit(String inputUnit) {
        this.inputUnit = inputUnit;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
