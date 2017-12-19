package game.battleFields;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс IdentificationField является дополнительным полем 16х16.
 * При создании нового юнита на поле боя происходит запись его номера в матрицу.
 * Работает вместо координат юнита.
 */
// TODO: 16.12.2017 merge this with BattleField & by means of unity param ID
public final class IdentificationField {

    private final List<List<String>> matrix = new ArrayList<>();
    private int numberUnity = 1;

    IdentificationField() {
        for (int i = 0; i < 16; i++) {
            this.matrix.add(Arrays.asList("  0", "  0", "  0", "  0", "  0", "  0", "  0",
                    "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0"));
        }
    }

    @NotNull
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

    @Contract(pure = true)
    public final List<List<String>> getMatrix() {
        return matrix;
    }

    @Contract(pure = true)
    int getNumberUnity() {
        return numberUnity;
    }

    void setNumberUnity(int numberUnity) {
        this.numberUnity = numberUnity;
    }

}
