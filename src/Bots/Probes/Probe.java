package Bots.Probes;

import polytech.polyNexus.Probes.parametres.Params;

/**
 * Интерфейс Probe - это классический интерфейс, который содержит в себе
 * стандартный набор методов по умолчанию для реализации классов-исследователей.
 * Например, имплементируясь от interface Probe, класс уже может выполнять следующее:
 * 1.) Исследование опасных зон на карте;
 * 2.) Определение активных юнитов;
 * 3.) Сортировать юнитов на поле боя по приоритетам используя алгоритм quickSort
 */


public interface Probe {

    Object probe(Params params);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    default List<Point> probeDangerousZone(BattleManager battleManager) {
//        List<Point> listDangerousZone = new ArrayList<>();
//        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
//        Pattern pattern = Pattern.compile("[GT]");
//        Pattern patternTurret = Pattern.compile("[tu]");
//        Pattern patternBonus = Pattern.compile("[HCBEiQ]");
//        Player currentPlayer = battleManager.getPlayer();
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                String current = matrix.get(i).get(j);
//                if (current.substring(3, 4).equals(battleManager.getOpponentPlayer().getColorType())) { //Если это точка противника
//                    Matcher matcher = pattern.matcher(current);
//                    Matcher matcherBonus = patternBonus.matcher(current);
//                    if (matcherBonus.find() || matcher.find()) { //Если это вражеский юнит, кторый стреляет по прямым и диагоналям:
//                        shift(currentPlayer, matrix, listDangerousZone, -1, 0, new Point(j, i));
//                        shift(currentPlayer, matrix, listDangerousZone, 0, -1, new Point(j, i));
//                        shift(currentPlayer, matrix, listDangerousZone, 1, 0, new Point(j, i));
//                        shift(currentPlayer, matrix, listDangerousZone, 0, 1, new Point(j, i));
//                        shift(currentPlayer, matrix, listDangerousZone, -1, -1, new Point(j, i));
//                        shift(currentPlayer, matrix, listDangerousZone, -1, 1, new Point(j, i));
//                        shift(currentPlayer, matrix, listDangerousZone, 1, -1, new Point(j, i));
//                        shift(currentPlayer, matrix, listDangerousZone, 1, 1, new Point(j, i));
//                    }
//                    Matcher matcherTurret = patternTurret.matcher(current);
//                    //Если это вражеская турель:
//                    if (matcherTurret.find()) {
//                        radiusMark(matrix, listDangerousZone, getRadius(current), new Point(j, i));
//                    }
//                }
//            }
//        }
//        return listDangerousZone;
//    }
//
//    default int getRadius(String current) {
//        int radius;
//        switch (current.substring(1, 2) + current.substring(4, 5)) {
//            case "^t":
//                radius = 2;
//                break;
//            case "<t":
//                radius = 3;
//                break;
//            case "^u":
//            case "<u":
//                radius = 5;
//                break;
//            default:
//                radius = 0;
//        }
//        return radius;
//    }
//
//    //Определение опасных точек от автоматчиков, танков
//    default void shift(Player currentPlayer, List<List<String>> matrix, List<Point> listDangerousZone, int dx, int dy, Point start) {
//        Pattern patternBuildings = Pattern.compile("[hgbfwt]");
//        while (start.X() + dx >= 0 && start.X() + dx < 16 && start.Y() + dy >= 0 && start.Y() + dy < 16) {
//            start.setX(start.X() + dx);
//            start.setY(start.Y() + dy);
//            String currentUnity = matrix.get(start.X()).get(start.Y()).substring(1);
//            Matcher matcher = patternBuildings.matcher(currentUnity.substring(3, 4));
//
//            if (matcher.matches() && currentUnity.substring(2, 3).equals(currentPlayer.getColorType())){
//                break;
//            } else {
//                Point next = new Point(start.X(), start.Y());
//                if (!listDangerousZone.contains(next)) {
//                    listDangerousZone.add(next);
//                }
//            }
//
////            if (!matcher.matches() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType())) {
////
////            } else break;
//        }
//    }
//
//
//    //Определение опасных точек от турелей:
//    default void radiusMark(List<List<String>> matrix, List<Point> listDangerousZone, int radius, Point middle) {
//        int x = middle.X();
//        int y = middle.Y();
//        int countShift = 0; //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
//        for (int i = x - radius; i < x + radius + 1; i++) {
//            for (int j = y - countShift; j < y + 1 + countShift; j++) {
//                boolean inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
//                if (inBounds && !listDangerousZone.contains(new Point(j, i))) {
//                    listDangerousZone.add(new Point(j, i));
//                }
//            }
//            countShift++;
//            if (i >= x) {
//                countShift = countShift - 2; //Перетягивание countShift--
//            }
//        }
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}


