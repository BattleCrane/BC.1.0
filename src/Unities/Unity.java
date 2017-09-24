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

    public Unity(int width, int height, String id) {
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public Unity() {

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
}
