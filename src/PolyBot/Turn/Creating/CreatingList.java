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
}
