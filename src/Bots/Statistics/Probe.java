package Bots.Statistics;

import BattleFields.BattleManager;
import BattleFields.Point;
import Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Probe {

    default List<Point> probeDangerousZone(BattleManager battleManager) {
        List<Point> listDangerousZone = new ArrayList<>();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        Pattern pattern = Pattern.compile("[GT]");
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

                    //Если это вражеская турель:
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
        return listDangerousZone;
    }

    //Определение опасных точек от автоматчиков, танков
    private void shift(Player currentPlayer, List<List<String>> matrix, List<Point> listDangerousZone, int dx, int dy, Point start) {
        Pattern patternBuildings = Pattern.compile("[hgbfwt]");
        String currentUnity = matrix.get(start.Y() + dy).get(start.X() + dx).substring(1);
        Matcher matcher = patternBuildings.matcher(currentUnity);
        if (!matcher.find() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType())) {
            Point nextPoint = new Point(start.Y() + dy, start.X() + dx);
            if (!listDangerousZone.contains(nextPoint)) {
                listDangerousZone.add(nextPoint);
            }
            shift(currentPlayer, matrix, listDangerousZone, dx, dy, nextPoint);
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
                if (inBounds && listDangerousZone.contains(new Point(j, i))) {
                    listDangerousZone.add(new Point(j, i));
                }
            }
            countShift++;
            if (i >= x) {
                countShift = countShift - 2; //Перетягивание countShift--
            }
        }
    }


}
