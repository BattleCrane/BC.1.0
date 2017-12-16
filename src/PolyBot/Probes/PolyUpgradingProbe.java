package PolyBot.Probes;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Priority.PriorityUnit;
import Players.Player;
import PolyBot.Priority.PolyPriorityUnit;
import Unities.Unity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PolyUpgradingProbe {
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
                if (unity.getId().equals("g")) {
                    return new PolyPriorityUnit(polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0)), point, unity);
                } else {
                    return new PolyPriorityUnit(1.2 * polyMapOfPriority.getMapOfPriorityUnits().get(unity.getId().charAt(0)), point, unity);
                }

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
}
