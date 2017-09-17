package BattleFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Класс BattleField реализует матрицу на основе ассоциативного массива.
 * Также для удобства переопределен метод toString.
 * Он вызывается в конце каждого хода и выводит на консоль состояние поля боя.
 */

public final class BattleField {

    private final List<List<String>> matrix = new ArrayList<>();

    public BattleField() {
        for (int i = 0; i < 16; i++) {
            this.matrix.add(Arrays.asList("  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0", "  0"));
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
}
