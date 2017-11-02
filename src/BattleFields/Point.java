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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Contract(pure = true)
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
