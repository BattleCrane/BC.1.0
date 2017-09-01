package BattleFields;

import Players.Player;
import Unities.Unity;

public class DataUnit {



    private Unity unity;
    private Point point;
    private Player player;

    public DataUnit(Unity unity, Point point, Player player) {
        this.unity = unity;
        this.point = point;
        this.player = player;
    }

    public Unity getUnity() {
        return unity;
    }

    public void setUnity(Unity unity) {
        this.unity = unity;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
