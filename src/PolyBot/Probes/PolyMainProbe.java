package PolyBot.Probes;

import Adjutants.AdjutantFielder;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Bots.Probes.Probe;
import Controllers.ControllerMatchMaking;
import Players.Player;
import PolyBot.PolyCombinations.Building.IteratorBuilding.PolyIteratorBuilder;
import PolyBot.Priority.PolyMapOfPriority;
import PolyBot.Priority.PolyPriorityUnit;
import Unities.Unity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyMainProbe implements Probe {
    private ControllerMatchMaking controllerMatchMaking;
    private PolyMapOfPriority polyMapOfPriority = new PolyMapOfPriority();
    private List<Point> listDangerousZone = new ArrayList<>();
    private AdjutantFielder adjutantFielder = new AdjutantFielder();

    public PolyMainProbe() {}

    public PolyMainProbe(ControllerMatchMaking controllerMatchMaking) {
        this.controllerMatchMaking = controllerMatchMaking;
    }

    public List<PriorityUnit> probeAccommodationOfUnitsTest(BattleManager battleManager) {
        PolyIteratorBuilder polyIteratorBuilder = new PolyIteratorBuilder();
        polyIteratorBuilder.findCombination(battleManager, battleManager.getHowICanBuild());
        return polyIteratorBuilder.getBestCombinationOfBuild().getPriorityUnitList();
    }




    @NotNull
    @Contract(pure = true)
    public PriorityUnit probeBallisticUnit(BattleManager battleManager, Unity unity, Point point) {
        double startValue = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        double value = startValue;
        if (listDangerousZone.contains(point.invariant())) {
            value = -value;
        }
        value += findClosestEnemy(battleManager, point, unity.getWidth(), unity.getHeight()) *
                0.1 * startValue;
        Player currentPlayer = battleManager.getPlayer();
        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
        value += collectValOfBallisticUnit(currentPlayer, matrix, point);
        return new PolyPriorityUnit(value, point, unity);
    }

    public double findClosestEnemyTest(BattleManager battleManager, Point startPoint, int width, int height) {
        return findClosestEnemy(battleManager, startPoint, width, height);
    }

    private int findClosestEnemy(BattleManager battleManager, Point startPoint, int width, int height) {
        boolean isNotFind = true;
        int startX = startPoint.X();
        int startY = startPoint.Y();
        int distance = 0;
        int dx = 1;
        int dy = 1;
        while (isNotFind) {
            int i;
            int j = startY - dy;
            for (i = startX - dx; i < startX + height + dx; i++) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())) {
                        isNotFind = false;
                    }
                }
            }
            i--;
            for (j = startY - dy; j < startY + width + dy; j++) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())) {
                        isNotFind = false;
                    }
                }
            }
            j--;
            for (i = startX + height; i >= startX - dx; i--) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())) {
                        isNotFind = false;
                    }
                }
            }
            i++;
            for (j = startY +width; j >= startY - dy; j--) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())) {
                        isNotFind = false;
                    }
                }
            }
            distance++;
            dx++;
            dy++;
        }
        return distance;
    }

    public double collectValOfBallisticUnitTest(Player currentPlayer, List<List<String>> matrix, Point start) {
        return collectValOfBallisticUnit(currentPlayer, matrix, start);
    }

    private double collectValOfBallisticUnit(Player currentPlayer, List<List<String>> matrix, Point point) {
        double value = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                } else {
                    Point start = new Point(point.X(), point.Y());
                    PolyMapOfPriority polyMapOfPriority = new PolyMapOfPriority();
                    Pattern patternNotBlockedUnits = Pattern.compile("[GTQoHeCBE]");
                    Pattern patternBuildings = Pattern.compile("[hgbfwtiu]");
                    boolean isSecondaryPurpose = false;
                    while (start.X() + dx >= 0 && start.X() + dx < 16 && start.Y() + dy >= 0 && start.Y() + dy < 16) {
                        start.setX(start.X() + dx);
                        start.setY(start.Y() + dy);
                        String currentUnity = matrix.get(start.Y()).get(start.X()).substring(1);
                        Matcher matcher = patternBuildings.matcher(currentUnity.substring(3, 4));
                        Matcher matcherNotBlockedUnits = patternNotBlockedUnits.matcher(currentUnity.substring(3, 4));
                        boolean OpponentBuilding = matcher.matches() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType());
                        boolean OpponentOtherUnit = matcherNotBlockedUnits.matches() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType());
                        if (OpponentBuilding) {
                            if (!isSecondaryPurpose) {
                                value += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(3)) * 0.5;
                                isSecondaryPurpose = true;
                            } else {
                                value += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(3)) * 0.2;
                            }
                        }
                        if (OpponentOtherUnit) {
                            if (!isSecondaryPurpose) {
                                value += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(3)) * 0.5;
                            } else {
                                value += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(3)) * 0.2;
                            }
                        }
                    }
                }
            }
        }
        return value;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //TurretUnits:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                    value += polyMapOfPriority.getMapOfPriorityUnits().get(otherUnit.charAt(4));
                }
            }
            countShift++;
            if (i >= x) {
                countShift = countShift - 2; //Перетягивание countShift--
            }
        }
        return value;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Building:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       public PriorityUnit probeBuildingTest(BattleManager battleManager, Unity unity, Point point) {
        return probeBuilding(battleManager, unity, point);
    }

    //Прямая точка:
    @NotNull
    private PriorityUnit probeBuilding(BattleManager battleManager, Unity unity, Point point) {
        double startValue = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        double value = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        for (int i = point.X(); i < point.X() + unity.getWidth(); i++) {
            for (int j = point.Y(); j < point.Y() + unity.getHeight(); j++) {
//                System.out.println(i + "     " + j);
                if (listDangerousZone.contains(new Point(j, i))) {
                    value = -value;
                    break;
                }
            }
        }

        value += probeForLock(battleManager, unity, point);
        if (unity.getId().equals("w") && listDangerousZone.contains(new Point(point.Y() + 1, point.X()))) {
            value -= 10;
            value += (8 - findClosestEnemy(battleManager, point, unity.getWidth(), unity.getHeight())) * 0.1 * startValue;
        } else {
            value += findClosestEnemy(battleManager, point, unity.getWidth(), unity.getHeight()) * 0.1 * startValue;
        }

        return new PolyPriorityUnit(value, point, unity);
    }

    public int probeForLockTest(BattleManager battleManager, Unity unity, Point start) {
        return probeForLock(battleManager, unity, start);
    }

    private int probeForLock(BattleManager battleManager, Unity unity, Point point) {
        int currentPoints = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(j).get(i).charAt(0) ==
                        battleManager.getPlayer().getColorType().charAt(0)) {
                    currentPoints++;
                }
            }
        }
        int futurePoints = 0;
        String field = battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y());
        if (unity.getId().equals("w")) {
            battleManager.putDoubleWall(battleManager.getPlayer(), point, unity);
        } else {
            battleManager.putUnity(battleManager.getPlayer(), point, unity);
        }

        adjutantFielder.fillZones(battleManager);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(j).get(i).charAt(0) ==
                        battleManager.getPlayer().getColorType().charAt(0)) {
                    futurePoints++;
                }
            }
        }
        int QuantityBuildings = 1; //Существуют постройки, которые строятся по 2
        if (unity.getId().equals("w")) {//Пример стена
            QuantityBuildings *= 2;
        }
        for (int i = point.X(); i < point.X() + unity.getWidth(); i++) {
            for (int j = point.Y(); j < point.Y() + unity.getHeight() * QuantityBuildings; j++) {
                battleManager.getIdentificationField().getMatrix().get(i).set(j, "  0");
                battleManager.getBattleField().getMatrix().get(i).set(j, field);
            }
        }
        adjutantFielder.flush(battleManager);
        adjutantFielder.fillZones(battleManager);
        return (futurePoints - currentPoints) * 30;
    }


    public PriorityUnit probeUpgradeTest(BattleManager battleManager, Unity unity, Point point) {
        return probeUpgrade(battleManager, unity, point);
    }

    @NotNull
    @Contract(pure = true)
    private PriorityUnit probeUpgrade(BattleManager battleManager, Unity unity, Point point) {
        String currentUnity = battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y());
        if (currentUnity.substring(5, 6).equals("'")) { //Если это корень ->
            if (upgradeBuilding(battleManager, point, battleManager.getPlayer())) {
                for (int i = point.X(); i < point.X() + unity.getWidth(); i++) {
                    for (int j = point.Y(); j < point.Y() + unity.getHeight(); j++) {
                        if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                            if (i == point.X() && j == point.Y()) {
                                battleManager.getBattleField().getMatrix().get(i).set(j, currentUnity);
                            } else {
                                battleManager.getBattleField().getMatrix().get(i).set(j, currentUnity.substring(0, 5) + ".");
                            }
                        }
                    }
                }
                return new PolyPriorityUnit(100.0 + polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0)), point, unity);
            }
        }
        return new PolyPriorityUnit(0, point, unity);
    }

    private boolean upgradeBuilding(BattleManager battleManager, Point point, Player player) {
        boolean isUpgraded = false;
        String unityBuild = battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y());
        if (unityBuild.contains(player.getColorType())) {
            switch (unityBuild.substring(4, 5)) { //Смотрим строение:
                case "g": //Улучшение генератора: -> Опускаемся в бараки:
                case "b": //Улучшение бараков:
                    if (!unityBuild.contains(">")) {
                        isUpgraded = true;
                    }
                    break;
                case "f": //Улучшение завода:
                    if (!unityBuild.contains(">")) {
                        isUpgraded = true;
                    }
                    break;
                case "t": //Улучшение туррели:
                    if (unityBuild.contains("^")) {
                        isUpgraded = true;
                    }
            }
        }

        return isUpgraded;
    }


    public void probeEnemyBonus(ControllerMatchMaking controllerMatchMaking) {
        if (controllerMatchMaking.getBattleManager().getOpponentPlayer().getEnergy() == 4) {


        }
    }

    @Contract(pure = true)
    private int countSlackMainBuildings(BattleManager battleManager) {
        Pattern patternBuildings = Pattern.compile("[hgbft]");
        int count = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i);
                Matcher matcher = patternBuildings.matcher(currentUnity);
//                if (matcher.find() && ())


            }
        }

        return count;
    }


    public List<Point> getListDangerousZone() {
        return listDangerousZone;
    }

    public void setListDangerousZone(List<Point> listDangerousZone) {
        this.listDangerousZone = listDangerousZone;
    }
}
