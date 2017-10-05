package BattleFields;
import org.jetbrains.annotations.Contract;

/**
 * Класс Point реализует координаты объектов на оси XoY
 */

public final class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Contract(pure = true)
    public int X() {
        return x;
    }

    @Contract(pure = true)
    public int Y() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }
}
