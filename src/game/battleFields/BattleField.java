package game.battleFields;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Класс BattleField реализует матрицу на основе ассоциативного массива.
 * Также для удобства переопределен метод toString.
 * Он вызывается в конце каждого хода и выводит на консоль состояние поля боя.
 */

// TODO: 16.12.2017 make loading field from file & change matrix type to List<List<Unity>>
public final class BattleField {
    private List<List<String>> matrix = new ArrayList<>();

    public BattleField() {
        for (int i = 0; i < 16; i++) {
            this.matrix.add(Arrays.asList("     0", "     0", "     0", "     0", "     0", "     0", "     0",
                    "     0", "     0", "     0", "     0", "     0", "     0", "     0", "     0", "     0"));
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

    public void setMatrix(List<List<String>> matrix) {
        this.matrix = matrix;
    }
}
