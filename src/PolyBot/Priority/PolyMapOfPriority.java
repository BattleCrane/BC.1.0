package PolyBot.Priority;
import java.util.HashMap;
import java.util.Map;

public class PolyMapOfPriority {

    private Map<Character, Double> mapOfPriorityBasicUnits =
            Map.of( //Основные юниты:
                    'h', 400.0, //Штаб
                    'g', 300.0, //Генератор
                    'b', 150.0, //Бараки
                    'f', 250.0, //Завод
                    't', 25.0, //Турель
                    'w', 10.0, //Стена
                    'G', 50.0, //Автоматчик
                    'T', 40.0 //Танк
            );

    private Map<Character, Double> mapOfPriorityBonusUnits =
            Map.of(  //Бонусы:
                    'e', 100.0, //Энергетическая батарея
                    'H', 150.0, //Ракетчик
                    'C', 50.0, //Плазменный автоматчик
                    'B', 40.0, //БМП "Медведь"
                    'E', 70.0, //Тяжелый танк "Молот"
                    'u', 25.0, //Ракетница
                    'i', 100.0, //Форт
                    'Q', 30.0 //Танк "Буффало"
            );

    private Map<Character, Double> mapOfPriorityUnits = new HashMap<>();


    public PolyMapOfPriority() {
        mapOfPriorityUnits.putAll(mapOfPriorityBasicUnits);
        mapOfPriorityUnits.putAll(mapOfPriorityBonusUnits);

    }

    public Map<Character, Double> getMapOfPriorityUnits() {
        return mapOfPriorityUnits;
    }

}
