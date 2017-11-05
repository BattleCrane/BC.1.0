package Players;

import Bonuses.Bonus;
import org.jetbrains.annotations.Contract;

import java.util.List;

/**
 * Класс Player реализует состояние игрока во время матча.
 * Каждый игрок обладает:
 * 1.) Ходом;
 * 2.) Запасом энергии;
 * 3.) Цветом;
 */

public final class Player {
    private int turn;
    private int energy;
    private int supplyEnergy; //не превышает 20
    private String colorType;
    private List<Bonus> listOfBonuses;

    public Player(int turn, int energy, int supplyEnergy, String colorType) {
        this.turn = turn;
        this.energy = energy;
        this.supplyEnergy = supplyEnergy;
        this.colorType = colorType;
    }

    public Player(int turn, int energy, int supplyEnergy, String colorType, List<Bonus> listOfBonuses) {
        this.turn = turn;
        this.energy = energy;
        this.supplyEnergy = supplyEnergy;
        this.colorType = colorType;
        this.listOfBonuses = listOfBonuses;
    }

    public Player(String colorType) {
        this.colorType = colorType;
    }

    @Contract(pure = true)
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Contract(pure = true)
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Contract(pure = true)
    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    @Contract(pure = true)
    public List<Bonus> getListOfBonuses() {
        return listOfBonuses;
    }

    public void setListOfBonuses(List<Bonus> listOfBonuses) {
        this.listOfBonuses = listOfBonuses;
    }

    @Contract(pure = true)
    public int getSupplyEnergy() {
        return supplyEnergy;
    }

    public void setSupplyEnergy(int supplyEnergy) {
        this.supplyEnergy = supplyEnergy;
    }

}
