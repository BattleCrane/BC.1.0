package polytech.polyNexus.probes;

import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.probes.Probe;
import game.unities.Unity;
import org.jetbrains.annotations.Contract;

import java.util.logging.Logger;

public final class PolyDistanceProbe implements Probe {
    private final Logger logger = Logger.getLogger(PolyDistanceProbe.class.getName());

    private final BattleManager battleManager;

    public PolyDistanceProbe(BattleManager battleManager) {
        this.battleManager = battleManager;
    }

    public static final class Params extends Probe.Params {
        private Unity unity;
        private final int unityWidth;
        private final int unityHeight;
        private final Point point;

        public Params(int unityWidth, int unityHeight, Point point) {
            this.unityWidth = unityWidth;
            this.unityHeight = unityHeight;
            this.point = point;
        }

        public Params(Unity unity, Point point){
            this.unity = unity;
            this.unityWidth = unity.getWidth();
            this.unityHeight = unity.getHeight();
            this.point  = point;
        }

        @Contract(pure = true)
        public Unity getUnity() {
            return unity;
        }

        @Contract(pure = true)
        public int getUnityWidth() {
            return unityWidth;
        }

        @Contract(pure = true)
        public int getUnityHeight() {
            return unityHeight;
        }

        @Contract(pure = true)
        public Point getPoint() {
            return point;
        }
    }

    @Override
    public Object probe(Probe.Params params) {
        Params inputParams = (Params) params;
        Point start = inputParams.point;
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
                if (!checkTouch(i, j)){
                    isNotFind = false;
                }
            }
            i--;
            for (j = startY - dy; j < startY + unityWidth + dy; j++) {
                if (!checkTouch(i, j)){
                    isNotFind = false;
                }
            }
            j--;
            for (i = startX + unityHeight; i >= startX - dx; i--) {
                if (!checkTouch(i, j)){
                    isNotFind = false;
                }
            }
            i++;
            for (j = startY + unityWidth; j >= startY - dy; j--) {
                if (!checkTouch(i, j)){
                    isNotFind = false;
                }
            }
            distance++;
            dx++;
            dy++;
        }
        return distance;
    }

    private boolean checkTouch(int i, int j) {
        if (i >= 0 && i < 16 && j >= 0 && j < 16) {
            String color = battleManager.getBattleField().getMatrix().get(i).get(j).substring(3, 4);
            if (color.equals(battleManager.getOpponentPlayer().getColorType())) {
                return false;
            }
        }
        return true;
    }
}
