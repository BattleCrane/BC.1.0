package polytech.polyNexus.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import botInterface.probes.Probe;
import game.players.Player;
import polytech.priority.PolyPriorityUnit;
import polytech.priority.Priorities;
import polytech.polyNexus.probes.parametres.ParentParams;
import game.unities.Unity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PolyUpgradingProbe implements Probe {
    private final double GENERATOR_UPGRADING_COEFFICIENT = 0.8;

    private final BattleManager battleManager;
    private final Priorities map;

    public PolyUpgradingProbe(BattleManager battleManager, Priorities map) {
        this.battleManager = battleManager;
        this.map = map;
    }

    public static final class UpgradingParams extends ParentParams {
        private final Unity unity;
        private final Point point;

        public UpgradingParams(Unity unity, Point point) {
            this.unity = unity;
            this.point = point;
        }
    }

    @Override
    public Object probe(ParentParams params) {
        UpgradingParams upgradingParams = (UpgradingParams) params;
        return probeUpgrade(upgradingParams.unity, upgradingParams.point);
    }

    @NotNull
    @Contract(pure = true)
    private PriorityUnit probeUpgrade(Unity unity, Point point) {
        String currentUnity = battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y());
        if (currentUnity.substring(5, 6).equals("'")) { //Если это корень ->
            if (upgradeBuilding(point, battleManager.getPlayer())) {
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
                double priority = map.getPriorities().get(unity.getId().charAt(0));
                if (unity.getId().equals("g")) {
                    return new PolyPriorityUnit(GENERATOR_UPGRADING_COEFFICIENT * priority, point, unity);
                } else {
                    return new PolyPriorityUnit(priority, point, unity);
                }
            }
        }
        return new PolyPriorityUnit(0, point, unity);
    }

    private boolean upgradeBuilding(Point point, Player player) {
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
