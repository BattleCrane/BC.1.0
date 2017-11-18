package PolyBot.Priority;

import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
*
*/

public final class PolyAdjutantPriorityField {
    private final List<List<PriorityUnit>> priorityMatrix = new ArrayList<>();

    public PolyAdjutantPriorityField(BattleManager battleManager) {
        BattleField battleField = battleManager.getBattleField();
        PolyMapOfPriority polyMapOfPriority = new PolyMapOfPriority();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String unit = battleField.getMatrix().get(j).get(i);
                PolyPriorityUnit polyPriorityUnit = new PolyPriorityUnit(unit.charAt(4), polyMapOfPriority.getMapOfPriorityUnits().get(unit.charAt(4)), new Point(j, i), "unchecked");
                priorityMatrix.get(j).set(i, polyPriorityUnit);
            }
        }
    }


    @NotNull
    @Override
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                stringBuilder.append(priorityMatrix.get(i).get(j).getPriority());
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
    public final List<List<PriorityUnit>> getMatrix() {
        return priorityMatrix;
    }
}
