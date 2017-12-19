package polytech.polyNexus.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.probes.Probe;
import game.players.Player;
import polytech.polyNexus.probes.parametres.Params;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PolyZoneProbe implements Probe {
    private final BattleManager battleManager;
    private Set<Point> dangerousZone;

    public PolyZoneProbe(BattleManager battleManager) {
        this.battleManager = battleManager;
    }

    static final class ZoneParams extends Params {
        public ZoneParams() {}
    }

    @Override
    public Object probe(Params params) {
        Set<Point> zone = new HashSet<>();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        Pattern basicPattern = Pattern.compile("[GT]");
        Pattern turretPattern = Pattern.compile("[tu]");
        Pattern bonusPattern = Pattern.compile("[HCBEiQ]");
        Player currentPlayer = battleManager.getPlayer();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String current = matrix.get(i).get(j);
                if (current.substring(3, 4).equals(battleManager.getOpponentPlayer().getColorType())) { //Если это точка противника
                    Matcher matcher = basicPattern.matcher(current);
                    Matcher matcherBonus = bonusPattern.matcher(current);
                    if (matcherBonus.find() || matcher.find()) { //Если это вражеский юнит, кторый стреляет по прямым и диагоналям:
                        for (int m = -1; m <= 1; m++){
                            for (int k = -1; k <= 1; k++){
                                if (m == 0 && k == 0){
                                    continue;
                                }
                                directShift(currentPlayer, matrix, zone, m, k, new Point(j, i));
                            }
                        }
                    }
                    Matcher matcherTurret = turretPattern.matcher(current);
                    //Если это вражеская турель:
                    if (matcherTurret.find()) {
                        radiusShift(zone, getRadius(current), new Point(j, i));
                    }
                }
            }
        }
        dangerousZone = zone;
        return zone;
    }

    //Определение опасных точек от автоматчиков, танков:
    private void directShift(Player currentPlayer, List<List<String>> matrix, Set<Point> listDangerousZone
            , int dx, int dy, Point start) {
        Pattern patternBuildings = Pattern.compile("[hgbfwt]");
        while (start.X() + dx >= 0 && start.X() + dx < 16 && start.Y() + dy >= 0 && start.Y() + dy < 16) {
            start.setX(start.X() + dx);
            start.setY(start.Y() + dy);
            String currentUnity = matrix.get(start.X()).get(start.Y()).substring(1);
            Matcher matcher = patternBuildings.matcher(currentUnity.substring(3, 4));
            if (matcher.matches() && currentUnity.substring(2, 3).equals(currentPlayer.getColorType())) {
                break;
            } else {
                Point next = new Point(start.X(), start.Y());
                if (!listDangerousZone.contains(next)) {
                    listDangerousZone.add(next);
                }
            }
        }
    }

    //Определение опасных точек от турелей:
    private void radiusShift(Set<Point> listDangerousZone, int radius, Point middle) {
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

    int getRadius(String current) {
        int radius;
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
                break;
            default:
                radius = 0;
        }
        return radius;
    }

    public Set<Point> getDangerousZone() {
        return dangerousZone;
    }
}


//                        shift(currentPlayer, matrix, zone, -1, 0, new Point(j, i));
//                        shift(currentPlayer, matrix, zone, 0, -1, new Point(j, i));
//                        shift(currentPlayer, matrix, zone, 1, 0, new Point(j, i));
//                        shift(currentPlayer, matrix, zone, 0, 1, new Point(j, i));
//                        shift(currentPlayer, matrix, zone, -1, -1, new Point(j, i));
//                        shift(currentPlayer, matrix, zone, -1, 1, new Point(j, i));
//                        shift(currentPlayer, matrix, zone, 1, -1, new Point(j, i));
//                        shift(currentPlayer, matrix, zone, 1, 1, new Point(j, i));