package Supports;

/**
 * Абстрактный класс Support являтся бонусом поддержки для игроков и реализует частично интерфейс SupportInterface.
 * У него есть единственный параметр - энергия, взамен на которую активируется бонус.
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
