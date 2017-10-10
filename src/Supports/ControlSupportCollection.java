package Supports;

import Controllers.ControllerMatchMaking;
import Unities.Unity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс ControlSupportCollection хранит в себе экземпляры класса Support и делегирует над ними
 */
public final class ControlSupportCollection {


    /**
     * Бонус: "Боевая преграда"
     * Стоимость: 1 ед. энергии;
     * Устанавливается на вашей и нейтральной территории;
     * Тип: "Сооружение" (не является ни строением ни армией);
     * Запас прочности 1;
     * На следующий ход уничтожается, и Вы получаете 1 ед. энергии.
     */

    private static final Support obstacle = new Support(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1Obstacle\\Sprite\\Obstacle.png" )),
            Arrays.asList(
                    new ImageView(new Image("file:src\\Resources\\Bonuses\\1Obstacle\\BlueUnity\\Obstacle.png" )),
                    new ImageView(new Image("file:src\\Resources\\Bonuses\\1Obstacle\\redUnity\\Obstacle.png" ))
            ), 33.5, 33.5) {
        private Unity obstacle = new Unity(1, 1, "o", 1);
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support combatReadiness = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support ambulance = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support heavyShells = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support energyBlock = new Support(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support explosive = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support fightingHeadquarters = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support clusterBomb = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support closeFight = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support bear = new Support(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support heavyTankHammer = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support cloning = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support superMortarTurret = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support attackOfTank = new Support(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support tankGenerator = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support fort = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support rocketCorsair = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support tankBuffalo = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support intensiveProduction = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support diversion = new Support(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support doubleTraining = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support coupling = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support airStrike = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Support mobilization = new Support(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    @Contract(pure = true)
    public static Support getObstacle() {
        return obstacle;
    }
}
