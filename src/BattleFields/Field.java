package BattleFields;

import java.util.List;

/**
 * Интерфейс Field реализует класс поле.
 */
public interface Field {
    public List<List<String>> getMatrix();

    public String toString();
}
