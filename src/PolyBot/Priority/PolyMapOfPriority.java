package PolyBot.Priority;

import java.util.HashMap;
import java.util.Map;

public class PolyMapOfPriority {

    private Map<Character, Double> mapOfPriorityBasicUnits = new HashMap<>();


    private Map<Character, Double> mapOfPriorityBonusUnits = new HashMap<>();

    private Map<Character, Double> mapOfPriorityUnits = new HashMap<>();


    public PolyMapOfPriority() {

        mapOfPriorityBasicUnits.put('h', 400.0);//Штаб
        mapOfPriorityBasicUnits.put('g', 300.0); //Генератор
        mapOfPriorityBasicUnits.put('b', 150.0); //Бараки
        mapOfPriorityBasicUnits.put('f', 250.0); //Завод
        mapOfPriorityBasicUnits.put('t', 25.0); //Турель
        mapOfPriorityBasicUnits.put('w', 10.0); //Стена
        mapOfPriorityBasicUnits.put('G', 50.0); //Автоматчик
        mapOfPriorityBasicUnits.put('T', 40.0); //Танк

        mapOfPriorityBonusUnits.put('e', 100.0); //Энергетическая батарея
        mapOfPriorityBonusUnits.put('H', 150.0); //Ракетчик
        mapOfPriorityBonusUnits.put('C', 50.0); //Плазменный автоматчик
        mapOfPriorityBonusUnits.put('B', 40.0); //БМП "Медведь"
        mapOfPriorityBonusUnits.put('E', 70.0); //Тяжелый танк "Молот"
        mapOfPriorityBonusUnits.put('u', 25.0); //Ракетница
        mapOfPriorityBonusUnits.put('i', 100.0); //Форт
        mapOfPriorityBonusUnits.put('Q', 30.0); //Танк "Буффало"


        mapOfPriorityUnits.putAll(mapOfPriorityBasicUnits);
        mapOfPriorityUnits.putAll(mapOfPriorityBonusUnits);

    }

    public Map<Character, Double> getMapOfPriorityUnits() {
        return mapOfPriorityUnits;
    }

}
