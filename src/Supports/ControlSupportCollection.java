package Supports;

import Controllers.ControllerMatchMaking;
import Unities.Unity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Класс ControlSupportCollection хранит в себе экземпляры класса Support и делегирует над ними
 */
public final class ControlSupportCollection {

    private final SupportInterface obstacle = new Support(1) {
        private Unity obstacle = new Unity(1, 1, "o", 1);
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {


        }
        public final ImageView getSprite() {
            ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3.png"));
            unit.setFitWidth(50);
            unit.setFitHeight(50);
            return unit;
        }
        public final ImageView getResourceObstacle() {
            ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3.png"));
            unit.setFitWidth(33.5);
            unit.setFitHeight(33.5);
            return unit;
        }
        public Unity getObstacle() {
            return obstacle;
        }
    };

    private final SupportInterface combatReadiness = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface ambulance = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface heavyShells = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface energyBlock = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface explosive = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface fightingHeadquarters = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface clusterBomb = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface closeFight = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface bear = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface heavyTankHammer = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface cloning = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface superMortarTurret = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface attackOfTank = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface tankGenerator = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface fort = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface rocketCorsair = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface tankBuffalo = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface intensiveProduction = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface diversion = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface doubleTraining = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface coupling = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface airStrike = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final SupportInterface mobilization = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };
}
