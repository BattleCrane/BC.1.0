package Supports;

/**
 * Класс ControlSupportCollection хранит в себе экземпляры класса Support и делегирует над ними
 */
public final class ControlSupportCollection {

    private final SupportInterface obstacle = new Support(1) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface combatReadiness = new Support(1) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface ambulance = new Support(1) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface heavyShells = new Support(1) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface energyBlock = new Support(1) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface explosive = new Support(2) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface fightingHeadquarters = new Support(2) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface clusterBomb = new Support(2) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface closeFight = new Support(2) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface bear = new Support(2) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface heavyTankHammer = new Support(3) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface cloning = new Support(3) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface superMortarTurret = new Support(3) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface attackOfTank = new Support(3) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface tankGenerator = new Support(4) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface fort = new Support(4) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface rocketCorsair = new Support(4) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface tankBuffalo = new Support(4) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface intensiveProduction = new Support(4) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface diversion = new Support(4) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface doubleTraining = new Support(5) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface coupling = new Support(5) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface airStrike = new Support(5) {
        @Override
        public void run() {

        }
    };

    private final SupportInterface mobilization = new Support(5) {
        @Override
        public void run() {

        }
    };
}
