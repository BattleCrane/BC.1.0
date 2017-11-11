package PolyBot.Priority;
import java.util.HashMap;
import java.util.Map;

public class PolyMapOfPriority {
    private Map<Character, Integer> mapOfPriorityBasicUnits =
            Map.of( //Основные юниты:
                    'h', 100, //Штаб
                    'g', 300, //Генератор
                    'b', 150, //Бараки
                    'f', 250, //Завод
                    't', 25, //Турель
                    'w', 10, //Стена
                    'G', 50, //Автоматчик
                    'T', 40 //Танк
            );
    private Map<Character, Integer> mapOfPriorityBonusUnits =
            Map.of(  //Бонусы:
                    'e', 100, //Энергетическая батарея
                    'H', 150, //Ракетчик
                    'C', 50, //Плазменный автоматчик
                    'B', 40, //БМП "Медведь"
                    'E', 70, //Тяжелый танк "Молот"
                    'u', 25, //Ракетница
                    'i', 100, //Форт
                    'Q', 30 //Танк "Буффало"
            );

    private Map<Character, Integer> mapOfPriorityUnits = new HashMap<>();


    PolyMapOfPriority() {
        mapOfPriorityUnits.putAll(mapOfPriorityBasicUnits);
        mapOfPriorityUnits.putAll(mapOfPriorityBonusUnits);

    }

    Map<Character, Integer> getMapOfPriorityUnits() {
        return mapOfPriorityUnits;
    }

}
