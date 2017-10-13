package Bonuses;

import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantWakeUpper;
import BattleFields.BattleManager;
import BattleFields.Point;
import Controllers.ControllerMatchMaking;
import Graphics.Painter;
import Players.Player;
import Unities.Unity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс ControllerBonusCollection хранит в себе экземпляры класса Support и управляет ими
 */
public final class ControllerBonusesCollection {

    public static void showBonuses(BattleManager battleManager, Player player, Pane paneControlBonus){
        int x = 40;
        int y = 37;
        for (Bonus bonus : player.getListOfBonuses()){
            bonus.getSprite().setLayoutX(x);
            bonus.getSprite().setLayoutY(y);
            if (bonus.equals(obstacle)) {
                returnEnergy(battleManager);
            }
            paneControlBonus.getChildren().add(bonus.getSprite());
            x += 80;
        }
    }

    public static void flush(Pane paneControlBonus){
        paneControlBonus.getChildren().retainAll(paneControlBonus.getChildren().get(0));
    }


    /**
     * Бонус: "Легкая преграда"
     * Стоимость: 1 ед. энергии;
     * Устанавливается на вашей и нейтральной территории;
     * Тип: "Сооружение" (не является ни строением ни армией);
     * Запас прочности 1;
     * На следующий ход уничтожается, и Вы получаете 1 ед. энергии.
     */

    private static final Bonus obstacle = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1Obstacle\\Sprite\\Obstacle.png" ))) {
        private Unity obstacle = new Unity(1, 1, "o", 1);

        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                if (controllerMatchMaking.isClick() && controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy() >= 0){
                    controllerMatchMaking.setClick(!controllerMatchMaking.isClick());
                    controllerMatchMaking.getBattleManager().putUnity(controllerMatchMaking.getBattleManager().getPlayer(),
                            new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5)), obstacle);
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                    controllerMatchMaking.getBattleManager().getBattleField().toString();
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    private static void returnEnergy(BattleManager battleManager){
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(i).get(j).substring(4,5).equals("o") &&
                        battleManager.getPlayer().getColorType().equals(battleManager.getBattleField().getMatrix()
                                .get(i).get(j).substring(3,4))){
                    System.out.println("!!!");
                    battleManager.getPlayer().setEnergy(battleManager.getPlayer().getEnergy() + 1);
                    battleManager.getBattleField().getMatrix().get(i).set(j,
                            AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
                    System.out.println("Obstacle: " + battleManager.getBattleField().getMatrix().get(i).get(j));
                    battleManager.checkDestroyedUnities();
                }
            }
        }
    }

    /**
     * Бонус: "Боевая готовность"
     * Стоимость: 1 ед. энергии;
     * Ваш выбранный пехотинец становиться активным
     */

    private static final Bonus combatReadiness = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1CombatReadiness\\Sprite\\CombatReadiness.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                Pattern pattern = Pattern.compile("[G]");
                Matcher matcher = pattern.matcher(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5)));
                if (controllerMatchMaking.isClick() && controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy() >= 0 && matcher.find() &&
                        !controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5)).contains("!")){
                    controllerMatchMaking.setClick(!controllerMatchMaking.isClick());
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), (int) (event.getY() / 33.5), (int) (event.getX() / 33.5));
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                    controllerMatchMaking.getBattleManager().getBattleField().toString();
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    /**
     * Бонус: "Скорая помощь"
     * Стоимость: 1 ед. энергии;
     * Все ваши автоматчики улучшаются до бронированных автоматчиков и получают +1 к здоровью;
     * Восстанавливает здоровье всем бронированным автоматчикам;
     */

    private static final Bonus ambulance = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\Sprite\\Ambulance.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            System.out.println("Ambulance");
            if (controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy() >= 0){
                for (int i = 0; i < 16; i++){
                    for (int j  = 0; j < 16; j++){
                        String currentUnity = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j);
                        if (currentUnity.substring(3,4).equals(controllerMatchMaking.getBattleManager().getPlayer().getColorType()) &&
                                currentUnity.substring(4,5).equals("G") && !currentUnity.substring(0,2).equals("2A")){
                            currentUnity = controllerMatchMaking.getBattleManager().increaseHitPoints(currentUnity, 1); //Здесь конечно можно вызвать AdjutantAttacker и положить -1...
                            currentUnity = currentUnity.substring(0, 1) + "A" + currentUnity.substring(2);
                            controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).set(j, currentUnity);
                        }
                    }
                }
                Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                        controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                controllerMatchMaking.getBattleManager().getBattleField().toString();
            }
        }
    };

    private static final Bonus heavyShells = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1HeavyShells\\Sprite\\HeavyShells.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            System.out.println("HeavyShells");
        }
    };

    private final Bonus energyBlock = new Bonus(1) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus explosive = new Bonus(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus fightingHeadquarters = new Bonus(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus clusterBomb = new Bonus(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus closeFight = new Bonus(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus bear = new Bonus(2) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus heavyTankHammer = new Bonus(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus cloning = new Bonus(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus superMortarTurret = new Bonus(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus attackOfTank = new Bonus(3) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus tankGenerator = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus fort = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus rocketCorsair = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus tankBuffalo = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus intensiveProduction = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus diversion = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus doubleTraining = new Bonus(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus coupling = new Bonus(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus airStrike = new Bonus(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    private final Bonus mobilization = new Bonus(5) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    //Getters
    @Contract(pure = true)
    public static Bonus getObstacle() {
        return obstacle;
    }


    @Contract(pure = true)
    public static Bonus getAmbulance() {
        return ambulance;
    }

    @Contract(pure = true)
    public static Bonus getHeavyShells() {
        return heavyShells;
    }

    @Contract(pure = true)
    public static Bonus getCombatReadiness() {
        return combatReadiness;
    }


}
