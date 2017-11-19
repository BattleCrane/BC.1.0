package Bots.Statistics;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Players.Player;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Интерфейс Probe - это классический интерфейс, который содержит в себе
 * стандартный набор методов по умолчанию для реализации классов-исследователей.
 * Например, имплементируясь от interface Probe, класс уже может выполнять следующее:
 * 1.) Исследование опасных зон на карте;
 * 2.) Определение активных юнитов;
 * 3.) Сортировать юнитов на поле боя по приоритетам используя алгоритм quickSort
 */


public interface Probe {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    default List<Point> probeDangerousZone(BattleManager battleManager) {
        List<Point> listDangerousZone = new ArrayList<>();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        Pattern pattern = Pattern.compile("[GT]");
        Pattern patternTurret = Pattern.compile("[tu]");
        Pattern patternBonus = Pattern.compile("[HCBEiQ]");
        Player currentPlayer = battleManager.getPlayer();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String current = matrix.get(j).get(i);
                if (current.substring(3, 4).equals(battleManager.getOpponentPlayer().getColorType())) { //Если это точка противника
                    Matcher matcher = pattern.matcher(current);
                    Matcher matcherBonus = patternBonus.matcher(current);
                    if (matcherBonus.find() || matcher.find()) { //Если это вражеский юнит, кторый стреляет по прямым и диагоналям:
                        shift(currentPlayer, matrix, listDangerousZone, -1, 0, new Point(j, i));
                        shift(currentPlayer, matrix, listDangerousZone, 0, -1, new Point(j, i));
                        shift(currentPlayer, matrix, listDangerousZone, 1, 0, new Point(j, i));
                        shift(currentPlayer, matrix, listDangerousZone, 0, 1, new Point(j, i));
                        shift(currentPlayer, matrix, listDangerousZone, -1, -1, new Point(j, i));
                        shift(currentPlayer, matrix, listDangerousZone, -1, 1, new Point(j, i));
                        shift(currentPlayer, matrix, listDangerousZone, 1, -1, new Point(j, i));
                        shift(currentPlayer, matrix, listDangerousZone, 1, 1, new Point(j, i));
                    }
                    Matcher matcherTurret = patternTurret.matcher(current);
                    //Если это вражеская турель:
                    if (matcherTurret.find()){
                        int radius = 0;
                        switch (current.substring(1, 2) + current.substring(4, 5)) {
                            case "^t":
                                radius = 2;
                                break;
                            case "<t":
                                radius = 3;
                                break;
                            case "^u":
                            case "<u":
                                radius = 5;
                        }
                        radiusMark(matrix, listDangerousZone, radius, new Point(j, i));
                    }

                }
            }
        }
        return listDangerousZone;
    }

    //Определение опасных точек от автоматчиков, танков
    private void shift(Player currentPlayer, List<List<String>> matrix, List<Point> listDangerousZone, int dx, int dy, Point start) {
        Pattern patternBuildings = Pattern.compile("[hgbfwt]");
            while (start.X() + dx >= 0 && start.X() + dx < 16 && start.Y() + dy >= 0 && start.Y() + dy < 16) {
                start.setX(start.X() + dx);
                start.setY(start.Y() + dy);
                String currentUnity = matrix.get(start.Y()).get(start.X()).substring(1);
                Matcher matcher = patternBuildings.matcher(currentUnity.substring(4, 5));

                if (!matcher.matches() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType())) {
                    Point next = new Point(start.Y(), start.X());
                    if (!listDangerousZone.contains(next)) {
                        listDangerousZone.add(next);
                    }
                } else break;
            }
    }


    //Определение опасных точек от турелей:
    private void radiusMark(List<List<String>> matrix, List<Point> listDangerousZone, int radius, Point middle) {
        int x = middle.X();
        int y = middle.Y();
        int countShift = 0; //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        for (int i = x - radius; i < x + radius + 1; i++) {
            for (int j = y - countShift; j < y + 1 + countShift; j++) {
                boolean inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
                if (inBounds && !listDangerousZone.contains(new Point(j, i))) {
                    listDangerousZone.add(new Point(j, i));
                }
            }
            countShift++;
            if (i >= x) {
                countShift = countShift - 2; //Перетягивание countShift--
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Contract(pure = true)
    default List<PriorityUnit> showActiveUnits(List<List<PriorityUnit>> matrix, Player player) {
        List<PriorityUnit> listOfActivePriorityUnit = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                PriorityUnit priorityUnit = matrix.get(j).get(i);
                if (priorityUnit.getColor() == player.getColorType().charAt(0) && priorityUnit.isActive()) {
                    listOfActivePriorityUnit.add(priorityUnit);
                }
            }
        }
        return listOfActivePriorityUnit;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    //QuickSort:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Random random = new Random(Calendar.getInstance().getTimeInMillis());

    private static int partition(int[] elements, int min, int max) {
        int x = elements[min + random.nextInt(max - min + 1)];
        int left = min, right = max;
        while (left <= right) {
            while (elements[left] < x) {
                left++;
            }
            while (elements[right] > x) {
                right--;
            }
            if (left <= right) {
                int temp = elements[left];
                elements[left] = elements[right];
                elements[right] = temp;
                left++;
                right--;
            }
        }
        return right;
    }

    private static void quickSort(int[] elements, int min, int max) {
        if (min < max) {
            int border = partition(elements, min, max);
            quickSort(elements, min, border);
            quickSort(elements, border + 1, max);
        }
    }

    default void quickSortPriority(int[] elements) {
        quickSort(elements, 0, elements.length - 1);
    }

    @Contract(pure = true)
    static int[] countingSort(int[] elements, int limit) {
        int[] count = new int[limit + 1];
        for (int element : elements) {
            count[element]++;
        }
        for (int j = 1; j <= limit; j++) {
            count[j] += count[j - 1];
        }
        int[] out = new int[elements.length];
        for (int j = elements.length - 1; j >= 0; j--) {
            out[count[elements[j]] - 1] = elements[j];
            count[elements[j]]--;
        }
        return out;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}


