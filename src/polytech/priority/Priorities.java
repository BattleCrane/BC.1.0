package polytech.priority;

import java.util.HashMap;
import java.util.Map;

//TODO Make this class Enum
public class Priorities {
    private Map<Character, Double> priorities = new HashMap<>();

    public Priorities() {
        Map<Character, Double> mapOfPriorityBasicUnits = new HashMap<>();
        mapOfPriorityBasicUnits.put('h', 500.0);//Штаб
        mapOfPriorityBasicUnits.put('g', 300.0); //Генератор
        mapOfPriorityBasicUnits.put('b', 150.0); //Бараки
        mapOfPriorityBasicUnits.put('f', 250.0); //Завод
        mapOfPriorityBasicUnits.put('t', 150.0); //Турель
        mapOfPriorityBasicUnits.put('w', 150.0); //Стена
        mapOfPriorityBasicUnits.put('G', 100.0); //Автоматчик
        mapOfPriorityBasicUnits.put('T', 100.0); //Танк

        Map<Character, Double> mapOfPriorityBonusUnits = new HashMap<>();
        mapOfPriorityBonusUnits.put('e', 100.0); //Энергетическая батарея
        mapOfPriorityBonusUnits.put('H', 150.0); //Ракетчик
        mapOfPriorityBonusUnits.put('C', 50.0); //Плазменный автоматчик
        mapOfPriorityBonusUnits.put('B', 40.0); //БМП "Медведь"
        mapOfPriorityBonusUnits.put('E', 70.0); //Тяжелый танк "Молот"
        mapOfPriorityBonusUnits.put('u', 25.0); //Ракетница
        mapOfPriorityBonusUnits.put('i', 100.0); //Форт
        mapOfPriorityBonusUnits.put('Q', 30.0); //Танк "Буффало"

        priorities.putAll(mapOfPriorityBasicUnits);
        priorities.putAll(mapOfPriorityBonusUnits);
    }

    public Map<Character, Double> getPriorities() {
        return priorities;
    }
}
