package BattleFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс IdentificationField является дополнительным полем 16х16.
 * При создании нового юнита на поле боя происходит запись его номера в матрицу.
 * Работает вместо координат юнита.
 */
public final class IdentificationField implements Field {

    private final List<List<String>> matrix = new ArrayList<>();
    private int numberUnity = 1;

    IdentificationField() {
        for (int i = 0; i < 16; i++) {
            this.matrix.add(Arrays.asList("  0", "  0", "  0", "  0", "  0", "  0", "  0",
                    "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0"));
        }
    }

    @Override
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                stringBuilder.append(matrix.get(i).get(j));
                if (j < 15) {
                    stringBuilder.append(" ");
                } else {
                    stringBuilder.append("\n");
                }
            }
        }
        System.out.print(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public final List<List<String>> getMatrix() {
        return matrix;
    }

    int getNumberUnity() {
        return numberUnity;
    }

    void setNumberUnity(int numberUnity) {
        this.numberUnity = numberUnity;
    }

}
