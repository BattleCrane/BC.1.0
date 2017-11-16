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
import javafx.scene.layout.Priority;
import org.jetbrains.annotations.Contract;

import java.util.List;

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

    @Contract(pure = true)
    private PriorityUnit probeValuationOfBallisticUnit(char type, Point point) {
        double value = polyMapOfPriority.getMapOfPriorityUnits().get(type);
        if (listDangerousZone.contains(point)){
            value = -value;
        }







        return new PolyPriorityUnit(type, value, point);
    }

    private int findClosestEnemy(BattleManager battleManager, PriorityUnit priorityUnit){
        boolean isNotFind = true;
        int width  = priorityUnit.getWidth();
        int height = priorityUnit.getHeight();
        int distance = 0;
        int dx = 1;
        int dy = 1;
        while (isNotFind){
            int i = 0;
            int j = 0;
            for (i = width - dx; i < width + dx; i++){
                String currentUnity = battleManager.getBattleField().getMatrix().get(height).get(i);
                if (i >= 0 && i < 16 && height >= 0 && height < 16 && currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                    isNotFind = false;
                }
            }
            for (j = height - dy; i < height + dy; j++){
                String currentUnity = battleManager.getBattleField().getMatrix().get(i).get(width);
                if (j >= 0 && j < 16 && width >= 0 && width < 16 && currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                    isNotFind = false;
                }
            }

            for (i = width + dx; i > width - dx; i--){
                String currentUnity = battleManager.getBattleField().getMatrix().get(height).get(i);
                if (i >= 0 && i < 16 && height >= 0 && height < 16 && currentUnity.contains(battleManager.getOpponentPlayer().getColorType())){
                    isNotFind = false;
                }
            }

            for (j = height + dy; j > width - dy; j++){
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


}
