package Bots.Priority;

import BattleFields.Point;
import Unities.Unity;

public abstract class PriorityUnit {
    private String inputUnit;
    private Unity unity;
    private double priority;
    private int hitPoints;
    private int level;
    private boolean isActive;
    private char color;
    private char name;
    private boolean isRoot;
    private String type;
    private String typeOfAttack;
    private Point point;
    private int width;
    private int height;

    public PriorityUnit(String inputUnit){
        this.inputUnit = inputUnit;
    }

    public PriorityUnit(double priority){
        this.priority = priority;
    }

    public PriorityUnit(String inputUnit, double priority, Point point){
        this.inputUnit = inputUnit;
        this.priority = priority;
        this.point = point;
    }

    public PriorityUnit(double priority, Point point, Unity unity){
        this.priority = priority;
        this.point = point;
        this.unity = unity;
    }

    public PriorityUnit(char name, double priority, Point point) {
        this.name = name;
        this.priority = priority;
        this.point = point;
    }



    public PriorityUnit(char name, double priority, Point point, String typeOfAttack) {
        this.name = name;
        this.priority = priority;
        this.point = point;
        this.typeOfAttack = typeOfAttack;
    }

    public PriorityUnit(String inputUnit, double priority, Point point, int width, int height){
        this.inputUnit = inputUnit;
        this.hitPoints = inputUnit.charAt(0);
        this.level = inputUnit.charAt(1) == '^' ? 1 : inputUnit.charAt(1) == '<' ? 2 : 3;
        this.isActive = inputUnit.charAt(2) != '?';
        this.color = inputUnit.charAt(3);
        this.name = inputUnit.charAt(4);
        this.isRoot = inputUnit.charAt(5) != '.';
        this.priority = priority;
        this.type = "unchecked";
        this.typeOfAttack = "unchecked";
        this.point = point;
        this.width = width;
        this.height = height;
    }


    public String getInputUnit() {
        return inputUnit;
    }

    public void setInputUnit(String inputUnit) {
        this.inputUnit = inputUnit;
    }

    public double getPriority() {
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

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public String getTypeOfAttack() {
        return typeOfAttack;
    }

    public void setTypeOfAttack(String typeOfAttack) {
        this.typeOfAttack = typeOfAttack;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriorityUnit that = (PriorityUnit) o;

        if (Double.compare(that.priority, priority) != 0) return false;
        if (!unity.equals(that.unity)) return false;
        return point != null ? point.equals(that.point) : that.point == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = inputUnit != null ? inputUnit.hashCode() : 0;
        temp = Double.doubleToLongBits(priority);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + hitPoints;
        result = 31 * result + level;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (int) color;
        result = 31 * result + (int) name;
        result = 31 * result + (isRoot ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (typeOfAttack != null ? typeOfAttack.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "PriorityUnit{" +
                "unity='" + unity.getId()+ '\'' +
                ", priority=" + priority +
                ", point=" + point +
                '}';
    }

    public Unity getUnity() {
        return unity;
    }

    public void setUnity(Unity unity) {
        this.unity = unity;
    }
}
