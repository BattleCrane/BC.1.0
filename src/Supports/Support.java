package Supports;

/**
 * Created by мсиайнина on 16.09.2017.
 */
abstract class Support implements SupportInterface {
    private int energy;

    Support(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

}
