package PolyBot.Probes;

import BattleFields.BattleManager;
import BattleFields.Point;

public class PolyDistanceProbe {
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
}
