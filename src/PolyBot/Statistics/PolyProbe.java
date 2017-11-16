package PolyBot.Statistics;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.PriorityUnit;
import Bots.Statistics.Probe;
import Controllers.ControllerMatchMaking;
import Players.Player;
import PolyBot.Priority.PolyAdjutantPriorityField;
import PolyBot.Priority.PolyMapOfPriority;
import PolyBot.Priority.PolyPriorityUnit;
import Unities.Unity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyProbe implements Probe {
    ControllerMatchMaking controllerMatchMaking;
    private PolyMapOfPriority polyMapOfPriority = new PolyMapOfPriority();
    private List<Point> listDangerousZone = probeDangerousZone(controllerMatchMaking.getBattleManager());


    public PriorityUnit probeBalisticUnit(PolyAdjutantPriorityField polyAdjutantPriorityField) {
        PriorityUnit mostPriorityUnit = new PolyPriorityUnit(0);
        List<List<PriorityUnit>> priorityMatrix = polyAdjutantPriorityField.getMatrix();
        List<List<String>> basicMatrix = controllerMatchMaking.getBattleManager().getBattleField().getMatrix();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                String currentUnity = basicMatrix.get(j).get(i);
                if (currentUnity.substring(1).equals("    0")) {

                }
            }
        }


        return mostPriorityUnit;
    }

    @NotNull
    @Contract(pure = true)
    private PriorityUnit probeValuationOfBallisticUnit(Unity unity, Point point) {
        double startValue = polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0));
        double value = startValue;
        if (listDangerousZone.contains(point)){
            value = -value;
        }
        value += findClosestEnemy(controllerMatchMaking.getBattleManager(), point, unity.getWidth(), unity.getHeight()) * 0.1 * startValue;

        Player currentPlayer = controllerMatchMaking.getBattleManager().getPlayer();
        List<List<String>> matrix = controllerMatchMaking.getBattleManager().getBattleField().getMatrix();

        valuationOfBallisticPosition(currentPlayer, matrix, -1,0, point, false, value);
        valuationOfBallisticPosition(currentPlayer, matrix, 0, -1, point, false, value);
        valuationOfBallisticPosition(currentPlayer, matrix, 1, 0, point, false, value);
        valuationOfBallisticPosition(currentPlayer, matrix,0, 1, point, false, value);
        valuationOfBallisticPosition(currentPlayer, matrix,  -1, -1, point, false, value);
        valuationOfBallisticPosition(currentPlayer, matrix, -1, 1, point, false, value);
        valuationOfBallisticPosition(currentPlayer, matrix, 1, -1, point, false, value);
        valuationOfBallisticPosition(currentPlayer, matrix,  1, 1, point, false, value);






        return new PolyPriorityUnit(unity.getId().charAt(0), value, point);
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
            int j;
            for (i = startX - dx; i < startX + width + dx; i++){
                String currentUnity = battleManager.getBattleField().getMatrix().get(height).get(i);
                if (i >= 0 && i < 16 && height >= 0 && height < 16 && currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                    isNotFind = false;
                }
            }
            for (j = startY - dy; i < startY + height + dy; j++){
                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(width);
                if (j >= 0 && j < 16 && width >= 0 && width < 16 && currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                    isNotFind = false;
                }
            }

            for (i = startX + width + dx; i > startX - dx; i--){
                String currentUnity = battleManager.getBattleField().getMatrix().get(height).get(i);
                if (i >= 0 && i < 16 && height >= 0 && height < 16 && currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                    isNotFind = false;
                }
            }

            for (j = startY + dy; j > startX + width - dy; j++){
                String currentUnity = battleManager.getBattleField().getMatrix().get(height).get(i);
                if (j >= 0 && j < 16 && width >= 0 && width < 16 && currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                    isNotFind = false;
                }
            }
            distance++;
            dx++;
            dy++;
        }
        return distance;
    }

    private void valuationOfBallisticPosition(Player currentPlayer, List<List<String>> matrix, int dx, int dy, Point start, boolean isSecondaryPurpose, double inputValue) {
        Pattern patternBuildings = Pattern.compile("[hgbfwt]");
        String currentUnity = matrix.get(start.Y() + dy).get(start.X() + dx);
        Matcher matcher = patternBuildings.matcher(currentUnity);
        boolean inBounds = start.Y() + dy >= 0 && start.Y() + dy < 16 && start.X() + dx >= 0 && start.X() + dx < 16;
        boolean isNotOpponentBuilding = !matcher.find() && !currentUnity.substring(3, 4).equals(currentPlayer.getColorType());
        if (inBounds){
            Point nextPoint = new Point(start.Y() + dy, start.X() + dx);
            if (isNotOpponentBuilding){
                if (!isSecondaryPurpose){
                    inputValue += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(4)) * 0.5;
                    isSecondaryPurpose = true;
                } else {
                    inputValue += polyMapOfPriority.getMapOfPriorityUnits().get(currentUnity.charAt(4)) * 0.2;
                }
            }
            valuationOfBallisticPosition(currentPlayer, matrix, dx, dy, nextPoint, isSecondaryPurpose, inputValue);
        }
    }
}
