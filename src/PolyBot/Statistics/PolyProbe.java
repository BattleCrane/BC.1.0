package PolyBot.Statistics;

import Adjutants.AdjutantFielder;
import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Bots.Statistics.Probe;
import Controllers.ControllerMatchMaking;
import Players.Player;
import PolyBot.Priority.PolyAdjutantPriorityField;
import PolyBot.Priority.PolyMapOfPriority;
import PolyBot.Priority.PolyPriorityUnit;
import Unities.Unity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyProbe implements Probe {
    private ControllerMatchMaking controllerMatchMaking;
    private PolyMapOfPriority polyMapOfPriority = new PolyMapOfPriority();
    private List<Point> listDangerousZone = new ArrayList<>();

//            probeDangerousZone(controllerMatchMaking.getBattleManager()) == null ? new ArrayList<>()
//            : probeDangerousZone(controllerMatchMaking.getBattleManager());

    public PolyProbe() {}

    public PolyProbe(ControllerMatchMaking controllerMatchMaking) {
        this.controllerMatchMaking = controllerMatchMaking;
    }

    public PriorityUnit probeUnit(PolyAdjutantPriorityField polyAdjutantPriorityField) {
        PriorityUnit mostPriorityUnit = new PolyPriorityUnit(0);
        List<List<PriorityUnit>> priorityMatrix = polyAdjutantPriorityField.getMatrix();
        List<List<String>> basicMatrix = controllerMatchMaking.getBattleManager().getBattleField().getMatrix();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String currentUnity = basicMatrix.get(j).get(i);
                if (currentUnity.substring(1).equals("    0")) {
//                    for (...)
                }
            }
        }


        return mostPriorityUnit;
    }

    //BallisticUnits
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @NotNull
    @Contract(pure = true)
    private PriorityUnit probeBallisticUnit(Unity unity, Point point) {
        double startValue = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        double value = startValue;
        if (listDangerousZone.contains(point)){
            value = -value;
        }
        value += findClosestEnemy(controllerMatchMaking.getBattleManager(), point, unity.getWidth(), unity.getHeight()) *
                0.1 * startValue;

        Player currentPlayer = controllerMatchMaking.getBattleManager().getPlayer();
        List<List<String>> matrix = controllerMatchMaking.getBattleManager().getBattleField().getMatrix();

        collectValOfBallisticUnit(currentPlayer, matrix, -1,0, point, false, value);
        collectValOfBallisticUnit(currentPlayer, matrix, 0, -1, point, false, value);
        collectValOfBallisticUnit(currentPlayer, matrix, 1, 0, point, false, value);
        collectValOfBallisticUnit(currentPlayer, matrix,0, 1, point, false, value);
        collectValOfBallisticUnit(currentPlayer, matrix,  -1, -1, point, false, value);
        collectValOfBallisticUnit(currentPlayer, matrix, -1, 1, point, false, value);
        collectValOfBallisticUnit(currentPlayer, matrix, 1, -1, point, false, value);
        collectValOfBallisticUnit(currentPlayer, matrix,  1, 1, point, false, value);

        return new PolyPriorityUnit(unity.getId().charAt(0), value, point);
    }

    public int findClosestEnemyTest(BattleManager battleManager, Point startPoint, int width, int height){
        return findClosestEnemy(battleManager, startPoint, width, height);
    }

    private int findClosestEnemy(BattleManager battleManager, Point startPoint, int width, int height){
        boolean isNotFind = true;
        int startX = startPoint.X();
        int startY = startPoint.Y();
        int distance = 0;
        int dx = 1;
        int dy = 1;
        while (isNotFind){
            int i;
            int j = startY - dy;
            for (i = startX - dx; i < startX + width + dx; i++){
                if (i >= 0 && i < 16 && j >= 0 && j < 16){
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                        isNotFind = false;
                    }
                    System.out.println(i + "  a  " + j);
                }
            }
            i--;
            for (j = startY - dy + 1; j < startY + height + dy; j++){
                if (i >= 0 && i < 16 && j >= 0 && j < 16){
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                        isNotFind = false;
                    }
                    System.out.println(i + "  b  " + j);
                }
            }
            j--;
            for (i = startX + width + dx - 2; i > startX - dx; i--){
                if (i >= 0 && i < 16 && j >= 0 && j < 16){
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                        isNotFind = false;
                    }
                    System.out.println(i + "  c  " + j);
                }
            }

            for (j = startY + dy; j > startX + height - dy; j--){
                if (i >= 0 && i < 16 && j >= 0 && j < 16){
                    String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
                    if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                        isNotFind = false;
                    }
                    System.out.println(i + "  d  " + j);
                }
            }
            distance++;
            dx++;
            dy++;
        }
        return distance;
    }

    private void collectValOfBallisticUnit(Player currentPlayer, List<List<String>> matrix, int dx, int dy, Point start, boolean isSecondaryPurpose, double inputValue) {
        Pattern patternBuildings = Pattern.compile("[hgbfwt]");
        String currentUnity = matrix.get(start.Y() + dy).get(start.X() + dx).substring(1);
        Matcher matcher = patternBuildings.matcher(currentUnity);
        boolean inBounds = start.Y() + dy >= 0 && start.Y() + dy < 16 && start.X() + dx >= 0 && start.X() + dx < 16;
        boolean isNotOpponentBuilding = !matcher.find() && !currentUnity.substring(2, 3).equals(currentPlayer.getColorType());
        if (inBounds){
            Point nextPoint = new Point(start.Y() + dy, start.X() + dx);
            if (!isNotOpponentBuilding){
                if (!isSecondaryPurpose){
                    inputValue += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(4)) * 0.5;
                    isSecondaryPurpose = true;
                } else {
                    inputValue += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(4)) * 0.2;
                }
            }
            collectValOfBallisticUnit(currentPlayer, matrix, dx, dy, nextPoint, isSecondaryPurpose, inputValue);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //TurretUnits:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @NotNull
    private PriorityUnit probeRadiusUnit(Unity unity, Point point){
        double value = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        if (listDangerousZone.contains(point)){
            value = -value;
        }
        collectValOfRadius(controllerMatchMaking.getBattleManager().getPlayer(), controllerMatchMaking.getBattleManager().getBattleField().getMatrix(), point, value);
        return new PolyPriorityUnit(value);
    }

    private void collectValOfRadius(Player currentPlayer, List<List<String>> matrix, Point start, double inputValue){
        int x = start.X();
        int y = start.Y();
        int radius = 0;
        String current = matrix.get(y).get(x);
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
        int countShift = 0; //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        for (int i = x - radius; i < x + radius + 1; i++) {
            for (int j = y - countShift; j < y + 1 + countShift; j++) {
                boolean inBounds = i >= 0 && i < 16 && j >= 0 && j < 16;
                if (inBounds && !current.substring(1).equals("    0") &&
                        current.charAt(3) != currentPlayer.getColorType().charAt(0)) {
                    inputValue += polyMapOfPriority.getMapOfPriorityUnits().get(current.charAt(4));
                }
            }
            countShift++;
            if (i >= x) {
                countShift = countShift - 2; //Перетягивание countShift--
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Building:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @NotNull
    private PriorityUnit probeBuilding(Unity unity, Point point){
        double value = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        if (listDangerousZone.contains(point)){
            value = -value;
        }
        value += probeForLock(controllerMatchMaking.getBattleManager(), unity, point);

        return new PolyPriorityUnit(value);
    }

    private int probeForLock(BattleManager battleManager, Unity unity, Point point){
        BattleManager cloneBattleManager = new BattleManager();
        List<List<String>> probedList = new ArrayList<>();
        for (int i = 0; i < 16; i++){
            probedList.add(battleManager.getBattleField().getMatrix().get(i));
            for (int j = 0; j < 16;  j++){
                probedList.get(i).add(battleManager.getBattleField().getMatrix().get(i).get(j));
            }
        }
        BattleField battleField = new BattleField();
        battleField.setMatrix(probedList);
        cloneBattleManager.setBattleField(battleField);
        if (cloneBattleManager.putUnity(controllerMatchMaking.getBattleManager().getPlayer(), point, unity)){
            new AdjutantFielder().fillZones(cloneBattleManager);
        }
        return countValOfNewLockedCells(battleField.getMatrix(), probedList);
    }

    private int countValOfNewLockedCells(List<List<String>> matrix, List<List<String>> probedMatrix){
        String playerColorType = controllerMatchMaking.getBattleManager().getPlayer().getColorType();
        int value = 0;
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                if (probedMatrix.get(j).get(i).equals(playerColorType + "    0") && !matrix.get(j).get(i).equals(playerColorType + "    0")){
                    value += 30;
                }
                if (!probedMatrix.get(j).get(i).equals(playerColorType + "    0") && matrix.get(j).get(i).equals(playerColorType + "    0")){
                    value -= 30;
                }
            }
        }
        return value;
    }

    @Contract(pure = true)
    private double probeUpgrade(double inputValue){
        return inputValue * 2;
    }


    public void probeEnemyBonus(ControllerMatchMaking controllerMatchMaking) {
        if (controllerMatchMaking.getBattleManager().getOpponentPlayer().getEnergy() == 4){}



    }

    @Contract(pure = true)
    private int countSlackMainBuildings(BattleManager battleManager){
        Pattern patternBuildings = Pattern.compile("[hgbft]");
        int count = 0;
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i);
                Matcher matcher = patternBuildings.matcher(currentUnity);
//                if (matcher.find() && ())




            }
        }

        return count;
    }





}
