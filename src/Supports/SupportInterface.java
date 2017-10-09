package Supports;

import Controllers.ControllerMatchMaking;

/**
 * Интерфейс SupportInterface описывает методы для абстрактного класса Support:
 * run() - происходит событие бонуса
 */
public interface SupportInterface {

    void run(ControllerMatchMaking controllerMatchMaking);

    int getEnergy();
}
