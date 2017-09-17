package Players;

/**
 * Класс Player реализует состояние игрока во время матча.
 * Каждый игрок обладает:
 * 1.) Ходом;
 * 2.) Запасом энергии;
 * 3.) Цветом;
 */

public class Player {
    private int turn;
    private int energy;
    private String colorType;

    public Player(int turn, int energy, String colorType) {
        this.turn = turn;
        this.energy = energy;
        this.colorType = colorType;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }
}
