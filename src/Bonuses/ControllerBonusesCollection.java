package Bonuses;

import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantSleeper;
import Adjutants.AdjutantWakeUpper;
import BattleFields.BattleField;
import BattleFields.BattleManager;
import BattleFields.IdentificationField;
import BattleFields.Point;
import Controllers.ControllerMatchMaking;
import Graphics.Painter;
import Players.Player;
import ResourceInit.ResourceOfBonuses;
import Unities.Unity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс ControllerBonusCollection хранит в себе экземпляры класса Support и управляет ими
 */
public final class ControllerBonusesCollection {

    public static void showBonuses(ControllerMatchMaking controllerMatchMaking, Player player, Pane paneControlBonus){
        int x = 42;
        int y = 37;
        for (Bonus bonus : player.getListOfBonuses()){
            bonus.getSprite().setLayoutX(x);
            bonus.getSprite().setLayoutY(y);
            if (bonus.equals(obstacle)) {
                returnEnergyFromObstacle(controllerMatchMaking.getBattleManager());
            }
            if (bonus.equals(energyBattery)){
                int collectedEnergy = 0;
                for (int i = 0; i < 16; i++){
                    for (int j = 0; j < 16; j++){
                        if (controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j).contains(controllerMatchMaking.getBattleManager().getPlayer().getColorType() + "e")){
                            collectedEnergy += returnEnergyFromEnergyBattery(controllerMatchMaking.getBattleManager().getBattleField(), controllerMatchMaking.getBattleManager().getIdentificationField(), new Point(i, j),
                                    new Unity(1, 1, "e", 1), controllerMatchMaking.getBattleManager().getPlayer());
                            controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).set(j,
                                    AdjutantAttacker.attack(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j), 1));
                            controllerMatchMaking.getBattleManager().checkDestroyedUnities();

                        }
                    }
                }
                controllerMatchMaking.getBattleManager().getPlayer().setEnergy(controllerMatchMaking.getBattleManager().getPlayer().getEnergy() + collectedEnergy);
            }
            if (bonus.equals(explosive)){
                for (int i = 0; i < 16; i++){
                    for (int j = 0; j < 16; j++){
                        String currentUnity = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j);
                        if (currentUnity.contains("!" +controllerMatchMaking.getBattleManager().getOpponentPlayer().getColorType() +  "w")){
                            controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).set(j,
                                    AdjutantAttacker.attack(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j), 4));
                            controllerMatchMaking.getBattleManager().checkDestroyedUnities();
                            System.out.println("Destroyed");
                        }
                    }
                }
            }
            paneControlBonus.getChildren().add(bonus.getSprite());
            if (x + 80 > 450){
                x = 42;
                y += 80;
            } else {
                x += 80;
            }
        }
    }

    public static void flush(Pane paneControlBonus, BattleManager battleManager){
        paneControlBonus.getChildren().retainAll(paneControlBonus.getChildren().get(0));
        battleManager.getBattleField().getMatrix().get(0).set(0,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(0).get(0)));
        battleManager.getBattleField().getMatrix().get(0).set(1,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(0).get(1)));
        battleManager.getBattleField().getMatrix().get(1).set(0,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(1).get(0)));
        battleManager.getBattleField().getMatrix().get(1).set(1,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(1).get(1)));
        battleManager.getBattleField().getMatrix().get(14).set(14,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(14).get(14)));
        battleManager.getBattleField().getMatrix().get(14).set(15,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(14).get(15)));
        battleManager.getBattleField().getMatrix().get(15).set(14,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(15).get(14)));
        battleManager.getBattleField().getMatrix().get(15).set(15,
                AdjutantSleeper.sleepUnity(battleManager.getBattleField().getMatrix().get(15).get(15)));
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
            Pane paneControlField = controllerMatchMaking.getPaneControlField();
            BattleManager battleManager = controllerMatchMaking.getBattleManager();
            paneControlField.setOnMouseClicked(event -> {
                int currentEnergy = battleManager.getPlayer().getEnergy();
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0){
                    controllerMatchMaking.setClick(false);
                    battleManager.putUnity(battleManager.getPlayer(),
                            new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5)), obstacle);
                    battleManager.getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    Painter.drawGraphic(battleManager, controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    private static void returnEnergyFromObstacle(BattleManager battleManager){
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(i).get(j).substring(4,5).equals("o") &&
                        battleManager.getPlayer().getColorType().equals(battleManager.getBattleField().getMatrix()
                                .get(i).get(j).substring(3,4))){
                    battleManager.getPlayer().setEnergy(battleManager.getPlayer().getEnergy() + 1);
                    battleManager.getBattleField().getMatrix().get(i).set(j,
                            AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
                    battleManager.checkDestroyedUnities();
                }
            }
        }
    }

    /**
     * Бонус: "Реактивная готовность"
     * Стоимость: 1 ед. энергии;
     * Ваш выбранный пехотинец становиться активным, после чего теряет 1 ед. здоровья
     */

    private static final Bonus combatReadiness = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1CombatReadiness\\Sprite\\CombatReadiness.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                Pattern pattern = Pattern.compile("[G]");
                Pattern patternBonuses = Pattern.compile("[H]");
                String currentUnit = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5));
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                Matcher matcher = pattern.matcher(currentUnit);
                Matcher matcherBonuses = patternBonuses.matcher(currentUnit);
                if (currentEnergy - this.getEnergy()>= 0 && (matcher.find() || matcherBonuses.find()) &&
                        !currentUnit.contains("!")){
                    controllerMatchMaking.setClick(false);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), (int) (event.getY() / 33.5), (int) (event.getX() / 33.5));
                    controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).set((int) (event.getX() / 33.5),
                            AdjutantAttacker.attack(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5)), 1));
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    /**
     * Бонус: "Скорая помощь"
     * Стоимость: 1 ед. энергии;
     * Все ваши обыкновенные автоматчики улучшаются до бронированных автоматчиков и получают +1 к здоровью;
     * Восстанавливает здоровье всем бронированным автоматчикам;
     */

    private static final Bonus ambulance = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\Sprite\\Ambulance.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
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
            }
            controllerMatchMaking.setClick(false);
        }
    };

    /**
     * Бонус: "Тяжелые снаряды"
     * Стоимость: 1 ед. энергии;
     * Все ваши обыкновенные автоматчики улучшаются до тяжелых автоматчиков и получают +2 к атаке до конца матча;
     */

    private static final Bonus heavyShells = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1HeavyShells\\Sprite\\HeavyShells.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                String currentUnit = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5));
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                if (currentEnergy - this.getEnergy()>= 0 && currentUnit.substring(1, 2).equals("^") && currentUnit.substring(4, 5).equals("G")){
                    controllerMatchMaking.setClick(false);
                    controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).set((int) (event.getX() / 33.5),
                            currentUnit.substring(0, 4) + "H" + currentUnit.substring(5));
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    /**
     * Бонус: "Энергетическая батарея"
     * Стоимость: 1 ед. энергии;
     * Устанавливается на вашей и нейтральной территории;
     * Тип: "Сооружение" (не является ни строением ни армией);
     * Запас прочности 1;
     * На следующий ход уничтожается, и Вы получаете 1 ед. энергии за каждый барак, стоящий рядом.
     */

    private static final Bonus energyBattery = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1EnergyBlock\\Sprite\\EnergyBlock.png" ))) {
        private Unity energyBattery = new Unity(1, 1, "e", 1);
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0){
                    controllerMatchMaking.setClick(false);
                    controllerMatchMaking.getBattleManager().putUnity(controllerMatchMaking.getBattleManager().getPlayer(),
                            new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5)), energyBattery);
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    private static int returnEnergyFromEnergyBattery(BattleField battleField, IdentificationField identificationField, Point point, Unity unity, Player player) {
        int returnedEnergy = 0;
        Set<Integer> setId = new LinkedHashSet<>();
        int startX = point.X() - 1; //Сдвигаем начальную точку в левый верхний угол (Тут ошибка в проектировании осей координат)
        int startY = point.Y() - 1;
        for (int i = startX; i <= startX + unity.getWidth() + 1; i++) {
            for (int j = startY; j <= startY + unity.getHeight() + 1; j++) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    if (battleField.getMatrix().get(i).get(j).contains(player.getColorType() + "b") &&
                            setId.add(Integer.parseInt(identificationField.getMatrix().get(i).get(j)))) {
                        returnedEnergy++;
                    }
                }
            }
        }
        return returnedEnergy;
    }

    /**
     * Бонус: "Взрывчатка"
     * Стоимость: 2 ед. энергии;
     * Устанавливается на любой вражеской стене;
     * На следующий ход уничтожает вражескую стену.
     */

    private static final Bonus explosive = new Bonus(2,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\Sprite\\Explosive.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                int x = (int) (event.getX() / 33.5);
                int y = (int) (event.getY() / 33.5);
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0 &&
                        controllerMatchMaking.getBattleManager().getBattleField().getMatrix().
                                get(x).get(y).contains(controllerMatchMaking.getBattleManager().getOpponentPlayer().getColorType() + "w'")){
                    controllerMatchMaking.setClick(false);
                    if (!controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(y).get(x).contains("!")){
                        AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), y, x);
                        controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                        Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                                controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                    }
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    private static final Bonus fightingHeadquarters = new Bonus(2,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Sprite\\FightingHeadquarters.png" ))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0){
                if (controllerMatchMaking.getBattleManager().getPlayer().getColorType().equals("+")){
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 14 , 14 );
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 14 , 15 );
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 15 , 14 );
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 15 , 15 );
                }
                if (controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy() >= 0 &&
                        controllerMatchMaking.getBattleManager().getPlayer().getColorType().equals("-")){
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 1 , 1 );
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 1 , 0 );
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 0 , 1 );
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 0 , 0 );
                }
                countShortsForHeadquarters = 2;
                controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                        controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                controllerMatchMaking.setClick(true);
            }

        }
    };

    private static int countShortsForHeadquarters = 0;

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
            if (controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy() >= 0){
                Pattern pattern = Pattern.compile("[hgbfwt]");
                Pattern patternBonus = Pattern.compile("[/]");
                for (int i = 0; i < 16; i++){
                    for (int j  = 0; j < 16; j++){
                        String currentUnity = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j);
                        Matcher matcher = pattern.matcher(currentUnity);
                        Matcher matcherBonus = patternBonus.matcher(currentUnity);
                        if (currentUnity.substring(3,4).equals(controllerMatchMaking.getBattleManager().getOpponentPlayer().getColorType()) &&
                                (matcher.find() || matcherBonus.find())){
                            currentUnity = AdjutantAttacker.attack(currentUnity, 1);
                            controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).set(j, currentUnity);
                        }
                    }
                }
                controllerMatchMaking.getBattleManager().checkDestroyedUnities();
                Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                        controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                controllerMatchMaking.getBattleManager().getBattleField().toString();
            }
            controllerMatchMaking.setClick(false);

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

    @Contract(pure = true)
    public static Bonus getEnergyBattery() {
        return energyBattery;
    }

    @Contract(pure = true)
    public static Bonus getExplosive() {
        return explosive;
    }

    @Contract(pure = true)
    public static Bonus getFightingHeadquarters() {
        return fightingHeadquarters;
    }

    @Contract(pure = true)
    public static int getCountShortsForHeadquarters() {
        return countShortsForHeadquarters;
    }

    public static void setCountShortsForHeadquarters(int countShortsForHeadquarters) {
        ControllerBonusesCollection.countShortsForHeadquarters = countShortsForHeadquarters;
    }



}
