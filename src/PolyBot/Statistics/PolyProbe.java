package PolyBot.Statistics;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.PriorityUnit;
import Bots.Statistics.Probe;
import Controllers.ControllerMatchMaking;
import PolyBot.Priority.PolyAdjutantPriorityField;
import PolyBot.Priority.PolyMapOfPriority;
import PolyBot.Priority.PolyPriorityUnit;
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


}
