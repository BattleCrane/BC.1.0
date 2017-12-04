package PolyBot.Turn.Creating;

import Bots.Priority.PriorityUnit;

import java.util.List;

public class CreatingList {
        private List<PriorityUnit> priorityUnitList;
        private double sum;

        public CreatingList(List<PriorityUnit> priorityUnitList, double sum) {
            this.priorityUnitList = priorityUnitList;
            this.sum = sum;
        }

        public void add(PriorityUnit priorityUnit) {
            priorityUnitList.add(priorityUnit);
            sum += priorityUnit.getPriority();
        }

        public void removeLast(){
            if (priorityUnitList.size() > 0){
                sum -= priorityUnitList.get(priorityUnitList.size() - 1).getPriority();
                priorityUnitList.remove(priorityUnitList.size() - 1);
            }
        }

        public boolean contains(PriorityUnit priorityUnit){
            return priorityUnitList.contains(priorityUnit);
        }

        public List<PriorityUnit> getPriorityUnitList() {
            return priorityUnitList;
        }

        public double getSum() {
            return sum;
        }

    @Override
    public String toString() {


        return "\n \n sum = " + sum + "\n priorityUnitList=" + priorityUnitList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreatingList that = (CreatingList) o;

        if (Double.compare(that.sum, sum) != 0) return false;
        return priorityUnitList.equals(that.priorityUnitList);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = priorityUnitList.hashCode();
        temp = Double.doubleToLongBits(sum);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
