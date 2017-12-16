package PolyBot.Probes;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import PolyBot.Priority.PolyPriorityUnit;
import Unities.Unity;
import org.jetbrains.annotations.NotNull;

public class PolyBuildingProbe {
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
            value += (10 - findClosestEnemy(battleManager, point, unity.getWidth(), unity.getHeight())) * 0.1 * startValue;
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
}
