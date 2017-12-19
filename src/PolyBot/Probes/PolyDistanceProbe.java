package PolyBot.Probes;

import BattleFields.BattleManager;
import BattleFields.Point;
import Bots.Probes.Probe;
import PolyBot.Probes.parametres.Params;

public final class PolyDistanceProbe implements Probe {
    private final BattleManager battleManager;

    PolyDistanceProbe(BattleManager battleManager) {
        this.battleManager = battleManager;
    }

    static final class DistanceParams extends Params {
        private final int unityWidth;
        private final int unityHeight;
        private final Point startPoint;


        DistanceParams(int unityWidth, int unityHeight, Point startPoint) {
            this.unityWidth = unityWidth;
            this.unityHeight = unityHeight;
            this.startPoint = startPoint;

        }
    }

    @Override
    public Object probe(Params params) {
        DistanceParams inputParams = (DistanceParams) params;
        Point start = inputParams.startPoint;
        int unityWidth = inputParams.unityWidth;
        int unityHeight = inputParams.unityHeight;
        return findClosestEnemy(start, unityWidth, unityHeight);
    }

    private Integer findClosestEnemy(Point startPoint, int unityWidth, int unityHeight) {
        boolean isNotFind = true;
        int startX = startPoint.X();
        int startY = startPoint.Y();
        int distance = 0;
        int dx = 1;
        int dy = 1;
        while (isNotFind) {
            int i;
            int j = startY - dy;
            for (i = startX - dx; i < startX + unityHeight + dx; i++) {
                isNotFind = checkTouch(i, j);
            }
            i--;
            for (j = startY - dy; j < startY + unityWidth + dy; j++) {
                isNotFind = checkTouch(i, j);
            }
            j--;
            for (i = startX + unityHeight; i >= startX - dx; i--) {
                isNotFind = checkTouch(i, j);
            }
            i++;
            for (j = startY + unityWidth; j >= startY - dy; j--) {
                isNotFind = checkTouch(i, j);
            }
            distance++;
            dx++;
            dy++;
        }
        return distance;
    }

    private boolean checkTouch(int i, int j) {
        if (i >= 0 && i < 16 && j >= 0 && j < 16) {
            String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i).substring(1);
            if (currentUnity.contains(battleManager.getOpponentPlayer().getColorType())) {
                return false;
            }
        }
        return true;
    }


}
