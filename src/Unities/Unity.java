package Unities;

/**
 * Класс Unity реализует объект в матрице.
 * Его основыми параметрами являются:
 * 1.) Ширина;
 * 2.) Высота;
 * 3.) Строка, которая состоит из флагов:
 *     а.) цвет (+ синий, - красный);
 *     б.) название объекта (буква h - штаб);
 *     в.) корня объекта (' - координатная точка);
 *     г.) здоровья (4 - число);
 *     д.) активированности объекта (!)
 */

public final class Unity {
    private int width;
    private int height;
    private String id;

    private int hitPoints;

    public Unity(int width, int height, String id) {
        this.width = width;
        this.height = height;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unity unity = (Unity) o;

        if (width != unity.width) return false;
        if (height != unity.height) return false;
        if (hitPoints != unity.hitPoints) return false;
        return id.equals(unity.id);
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + id.hashCode();
        result = 31 * result + hitPoints;
        return result;
    }

    public Unity(int width, int height, String id, int hitPoints) {
        this.width = width;
        this.height = height;
        this.id = id;
        this.hitPoints = hitPoints;
    }

    public Unity() {

    }

    @Override
    public String toString() {
        return "Unity{" +
                "width=" + width +
                ", height=" + height +
                ", id='" + id + '\'' +
                ", hitPoints=" + hitPoints +
                '}';
    }

    public Unity(String id) {
        this.id = id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
