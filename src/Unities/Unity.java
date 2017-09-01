package Unities;
public class Unity{

    private int width;
    private int height;
    private String id;

    Unity(){};
    Unity(int width, int height, String id) {
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
