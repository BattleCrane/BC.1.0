package PolyBot.Probes;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Players.Player;
import PolyBot.Priority.PolyPriorityUnit;
import Unities.Unity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PolyRadiusProbe {
    public PriorityUnit probeRadiusUnitTest(BattleManager battleManager, Unity unity, Point start) {
        return probeRadiusUnit(battleManager, unity, start);
    }

    @NotNull
    private PriorityUnit probeRadiusUnit(BattleManager battleManager, Unity unity, Point point) {
        double startValue = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        double value = startValue;
        if (listDangerousZone.contains(point)) {
            value = -value;
        }
        value += (8.0 - findClosestEnemy(battleManager, point, unity.getWidth(), unity.getHeight())) * 0.1 * startValue;
        value += collectValOfRadius(battleManager.getPlayer(), battleManager.getBattleField().getMatrix(), point) * 0.5;
        return new PolyPriorityUnit(value, point, unity);
    }

    private double collectValOfRadius(Player currentPlayer, List<List<String>> matrix, Point start) {
        double value = 0;
        int x = start.X();
        int y = start.Y();
        String current = matrix.get(x).get(y);
        int radius = getRadius(current);
        int countShift = 0; //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        for (int i = x - radius; i < x + radius + 1; i++) {
            for (int j = y - countShift; j < y + 1 + countShift; j++) {
                String otherUnit = matrix.get(j).get(i);
                boolean inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
                if (inBounds && !otherUnit.substring(1).equals("    0") &&
                        otherUnit.charAt(3) != currentPlayer.getColorType().charAt(0)) {
                    value += 10 * polyMapOfPriority.getMapOfPriorityUnits().get(otherUnit.charAt(4));
                }
            }
            countShift++;
            if (i >= x) {
                countShift = countShift - 2; //Перетягивание countShift--
            }
        }
        return value;
    }
}
