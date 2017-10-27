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
import Unities.Unity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.Contract;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс ControllerBonusCollection хранит в себе экземпляры класса Support и управляет ими
 */
public final class ControllerBonusesCollection {

    public static void showBonuses(ControllerMatchMaking controllerMatchMaking, Player player, Pane paneControlBonus) {
        int x = 42;
        int y = 37;
        for (Bonus bonus : player.getListOfBonuses()) {
            bonus.getSprite().setLayoutX(x);
            bonus.getSprite().setLayoutY(y);
            if (bonus.equals(obstacle)) {
                returnEnergyFromObstacle(controllerMatchMaking.getBattleManager());
            }
            if (bonus.equals(energyBattery)) {
                int collectedEnergy = 0;
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        if (controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j).contains(controllerMatchMaking.getBattleManager().getPlayer().getColorType() + "e")) {
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
            if (bonus.equals(explosive)) {
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        String currentUnity = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j);
                        if (currentUnity.contains("!" + controllerMatchMaking.getBattleManager().getOpponentPlayer().getColorType() + "w")) {
                            controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).set(j,
                                    AdjutantAttacker.attack(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j), 4));
                            controllerMatchMaking.getBattleManager().checkDestroyedUnities();
                            System.out.println("Destroyed");
                        }
                    }
                }
            }

            if (bonus.equals(intensiveProduction)) {
                if (player.getColorType().equals("+")) {
                    player.setEnergy(player.getEnergy() - 1 + additionalEnergyBlue);
                }
                if (player.getColorType().equals("-")) {
                    player.setEnergy(player.getEnergy() - 1 + additionalEnergyRed);
                }
            }
            paneControlBonus.getChildren().add(bonus.getSprite());
            if (x + 80 > 450) {
                x = 42;
                y += 80;
            } else {
                x += 80;
            }
        }
    }

    public static void flush(Pane paneControlBonus, BattleManager battleManager) {
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
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1Obstacle\\Sprite\\Obstacle.png"))) {
        private Unity obstacle = new Unity(1, 1, "o", 1);

        public void run(ControllerMatchMaking controllerMatchMaking) {
            Pane paneControlField = controllerMatchMaking.getPaneControlField();
            BattleManager battleManager = controllerMatchMaking.getBattleManager();
            paneControlField.setOnMouseClicked(event -> {
                int currentEnergy = battleManager.getPlayer().getEnergy();
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0) {
                    controllerMatchMaking.setClick(false);
                    if (battleManager.putUnity(battleManager.getPlayer(),
                            new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5)), obstacle)) {
                        battleManager.getPlayer().setEnergy(currentEnergy - this.getEnergy());
                        Painter.drawGraphic(battleManager, controllerMatchMaking.getResource(),
                                controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                    }
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    private static void returnEnergyFromObstacle(BattleManager battleManager) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(i).get(j).substring(4, 5).equals("o") &&
                        battleManager.getPlayer().getColorType().equals(battleManager.getBattleField().getMatrix()
                                .get(i).get(j).substring(3, 4))) {
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
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1CombatReadiness\\Sprite\\CombatReadiness.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                Pattern pattern = Pattern.compile("[G]");
                Pattern patternBonuses = Pattern.compile("[HC]");
                String currentUnit = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5));
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                Matcher matcher = pattern.matcher(currentUnit);
                Matcher matcherBonuses = patternBonuses.matcher(currentUnit);
                if (currentEnergy - this.getEnergy() >= 0 && (matcher.find() || matcherBonuses.find()) &&
                        !currentUnit.contains("!")) {
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
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\Sprite\\Ambulance.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            if (controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy() >= 0) {
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        String currentUnity = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j);
                        if (currentUnity.substring(3, 4).equals(controllerMatchMaking.getBattleManager().getPlayer().getColorType()) &&
                                currentUnity.substring(4, 5).equals("G") && !currentUnity.substring(0, 2).equals("2A")) {
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
     * Ваш выбранный автоматчик улучшается до тяжелого автоматчика и получает +2 к атаке до конца матча;
     */

    private static final Bonus heavyShells = new Bonus(1,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1HeavyShells\\Sprite\\HeavyShells.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                String currentUnit = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5));
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                if (currentEnergy - this.getEnergy() >= 0 && currentUnit.substring(1, 2).equals("^") && currentUnit.substring(4, 5).equals("G")) {
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
            new ImageView(new Image("file:src\\Resources\\Bonuses\\1EnergyBlock\\Sprite\\EnergyBlock.png"))) {
        private Unity energyBattery = new Unity(1, 1, "e", 1);

        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0) {
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
            new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\Sprite\\Explosive.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                int x = (int) (event.getX() / 33.5);
                int y = (int) (event.getY() / 33.5);
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0 &&
                        controllerMatchMaking.getBattleManager().getBattleField().getMatrix().
                                get(y).get(x).contains(controllerMatchMaking.getBattleManager().getOpponentPlayer().getColorType() + "w'")) {
                    controllerMatchMaking.setClick(false);
                    if (!controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(y).get(x).contains("!")) {
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

    /**
     * Бонус: "Боевой штаб"
     * Стоимость: 2 ед. энергии;
     * Ваш штаб получает три выстела по 1 ед. урона до конца хода ;
     */

    private static final Bonus fightingHeadquarters = new Bonus(2,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Sprite\\FightingHeadquarters.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0) {
                if (controllerMatchMaking.getBattleManager().getPlayer().getColorType().equals("+")) {
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 14, 14);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 14, 15);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 15, 14);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 15, 15);
                }
                if (controllerMatchMaking.getBattleManager().getPlayer().getEnergy() - this.getEnergy() >= 0 &&
                        controllerMatchMaking.getBattleManager().getPlayer().getColorType().equals("-")) {
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 1, 1);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 1, 0);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 0, 1);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), 0, 0);
                }
                countShortsForHeadquarters = 3;
                controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                        controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                controllerMatchMaking.setClick(true);
            }

        }
    };
    private static int countShortsForHeadquarters = 0;


    /**
     * Бонус: "Кластерные стрелы"
     * Стоимость: 2 ед. энергии;
     * Ваш выбранный автоматчик улучшается до кластерного автоматчика и наносит ТОЛЬКО СТРОЕНИЯМ урон, равный их площади до конца матча;
     */


    private static final Bonus clusterArrow = new Bonus(2,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\2ClusterArrow\\Sprite\\ClusterArrow.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                String currentUnit = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).get((int) (event.getX() / 33.5));
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                if (currentEnergy - this.getEnergy() >= 0 && currentUnit.substring(1, 2).equals("^") && currentUnit.substring(4, 5).equals("G")) {
                    controllerMatchMaking.setClick(false);
                    controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get((int) (event.getY() / 33.5)).set((int) (event.getX() / 33.5),
                            currentUnit.substring(0, 4) + "C" + currentUnit.substring(5));
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };


    /**
     * Бонус: "Генеральная уборка"
     * Стоимость: 2 ед. энергии;
     * Очищает битые клетки площадью 2х2 от заданного левого верхнего угла;
     */

    private static final Bonus cleanup = new Bonus(2,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\2Cleanup\\Sprite\\Cleanup.png"))) {
        String field = "     0";

        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                int x = (int) (event.getX() / 33.5);
                int y = (int) (event.getY() / 33.5);
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0) {
                    controllerMatchMaking.setClick(false);
                    for (int i = x; i < x + 2; i++) {
                        for (int j = y; j < y + 2; j++) {
                            if (i >= 0 && i < 16 && j >= 0 && j < 16 &&
                                    controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(j).get(i).equals("XXXXXX")) {
                                controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(j).set(i, field);
                            }
                        }
                    }
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };

    /**
     * Бонус: "БМП Медведь"
     * Стоимость: 2 ед. энергии;
     * Устанавливается в зависимости от доступной территории;
     * Тип: "Техника";
     * Запас прочности 2;
     * Требуется завод;
     * Наносит кол-во урона, равное кол-ву ваших казарм на поле боя.
     */

    private static final Bonus bear = new Bonus(2,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\2Bear\\Sprite\\Bear.png"))) {
        Unity bear = new Unity(1, 1, "B", 2);

        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                if (controllerMatchMaking.isClick() && currentEnergy - this.getEnergy() >= 0) {
                    controllerMatchMaking.setClick(false);
                    System.out.println(controllerMatchMaking.getBattleManager().getHowICanProductTanks() > 0);
                    if (controllerMatchMaking.getBattleManager().getHowICanProductTanks() > 0 && controllerMatchMaking.getBattleManager().putUnity(controllerMatchMaking.getBattleManager().getPlayer(),
                            new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5)), bear)) {
                        controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                        controllerMatchMaking.getBattleManager().setHowICanProductTanks(controllerMatchMaking.getBattleManager().getHowICanProductTanks() - 1);
                    }
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });
        }
    };


    @Contract(pure = true)
    public static int getBearDamage(BattleManager battleManager) {
        int bearsDamage = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (battleManager.getBattleField().getMatrix().get(i).get(j).contains(
                        battleManager.getPlayer().getColorType() + "b'")) {
                    bearsDamage++;
                }
            }
        }
        return bearsDamage;
    }

    /**
     * Бонус: "Тяжелый танк 'Молот'"
     * Стоимость: 3 ед. энергии;
     * Улучшает ваш обыкновенный танк до танка "Молот", становится активным и получает +1 к прочности
     */

    private static final Bonus heavyTankHammer = new Bonus(3,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\3HeavyTankHammer\\Sprite\\HeavyTankHammer.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                int x = (int) (event.getX() / 33.5);
                int y = (int) (event.getY() / 33.5);
                Pattern pattern = Pattern.compile("[T]");
                Pattern patternBonuses = Pattern.compile("[/]");
                String currentUnit = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(y).get(x);
                int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
                Matcher matcher = pattern.matcher(currentUnit);
                Matcher matcherBonuses = patternBonuses.matcher(currentUnit);
                if (currentEnergy - this.getEnergy() >= 0 && (matcher.find() || matcherBonuses.find()) &&
                        currentUnit.substring(3, 5).equals(controllerMatchMaking.getBattleManager().getPlayer().getColorType() + "T")) {
                    controllerMatchMaking.setClick(false);
                    currentUnit = controllerMatchMaking.getBattleManager().increaseHitPoints(currentUnit, 1);
                    currentUnit = currentUnit.substring(0, 4) + "E" + currentUnit.substring(5);
                    controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(y).set(x, currentUnit);
                    AdjutantWakeUpper.wakeUpExactly(controllerMatchMaking.getBattleManager(), y, x);
                    controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                }
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
            });

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

    /**
     * Бонус: "Засада"
     * Стоимость: 3 ед. энергии;
     * Окружает выбранный вами танк противника автоматчиками.
     */

    private static final Bonus attackOfTank = new Bonus(3,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\3CapturingOfTank\\Sprite\\CapturingOfTank.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            System.out.println(currentEnergy - this.getEnergy() >= 0);
            if (currentEnergy - this.getEnergy() >= 0) {
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(event -> {
                    int x = (int) (event.getX() / 33.5);
                    int y = (int) (event.getY() / 33.5);
                    Pattern pattern = Pattern.compile("[T]");
                    Pattern patternBonuses = Pattern.compile("[E]");
                    Matcher matcher = pattern.matcher(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(y).get(x));
                    Matcher matcherOfBonuses = patternBonuses.matcher(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(y).get(x));
                    if (controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(y).get(x).
                            contains(controllerMatchMaking.getBattleManager().getOpponentPlayer().getColorType()) && (matcher.find() || matcherOfBonuses.find())) {
                        for (int i = x - 1; i <= x + 1; i++) {
                            for (int j = y - 1; j <= y + 1; j++) {
                                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                                    controllerMatchMaking.getBattleManager().putUnity(controllerMatchMaking.getBattleManager().getPlayer(),
                                            new Point(j, i), controllerMatchMaking.getBattleManager().getGunner());
                                }
                            }
                        }
                        controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                    }
                    controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
                    Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                            controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                });
            }
        }
    };

    /**
     * Бонус: "Танковая перегрузка"
     * Стоимость: 4 ед. энергии;
     * Вы получаете по 1 ед. энергии за каждый  ваш танк.
     * Все ваши танки наносят противникам по 2 ед. урона в радиусе 2 и уничтожаются.
     */

    private static final Bonus tankCharge = new Bonus(4,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\4TankGenerator\\Sprite\\TankGenerator.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0) {
                int additionalEnergy = 0;
                Pattern pattern = Pattern.compile("[T]");
                Pattern patternBonuses = Pattern.compile("[E]");
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        String currentUnit = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(j).get(i);
                        Matcher matcher = pattern.matcher(currentUnit);
                        Matcher matcherOfBonus = patternBonuses.matcher(currentUnit);
                        if ((matcher.find() || matcherOfBonus.find()) && currentUnit.contains(controllerMatchMaking.getBattleManager().getPlayer().getColorType())) {
                            additionalEnergy++;
                            AdjutantAttacker.radiusAttack(controllerMatchMaking.getBattleManager(), new Point (j ,i), 2, 2);
                            controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(j).
                                    set(i, "XXXXXX");
                        }
                    }
                }
                controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy() + additionalEnergy);
            }
            controllerMatchMaking.setClick(true);
            Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                    controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
        }
    };

    private final Bonus fort = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    /**
     * Бонус: "Ракета 'Корсар'"
     * Стоимость: 4 ед. энергии;
     * Наносит 2 ед. урона в любую точку.
     */

    private static final Bonus rocketCorsair = new Bonus(4,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\4RocketCorsair\\Sprite\\RocketCorsair.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0) {
                controllerMatchMaking.setClick(false);
                controllerMatchMaking.getPaneControlField().setOnMouseClicked(secondEvent -> {
                    Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                    System.out.println("Второй клик: ");
                    String targetAttackUnity = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                    if (!targetAttackUnity.contains(controllerMatchMaking.getBattleManager().getPlayer().getColorType())) {
                        Pattern pattern = Pattern.compile("[hgbfwtGT]");
                        Matcher matcher = pattern.matcher(targetAttackUnity);
                        Pattern patternBonus = Pattern.compile("[oHeCBE]");
                        Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                        if ((matcher.find() || matcherBonus.find())) {
                            for (int i = 0; i < 16; i++) {
                                for (int j = 0; j < 16; j++) {
                                    String attackerUnitID = controllerMatchMaking.getBattleManager().getIdentificationField().getMatrix().get(i).get(j);
                                    String targetUnitID = controllerMatchMaking.getBattleManager().getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                    if (attackerUnitID.equals(targetUnitID)) {
                                        controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).set(j,
                                                AdjutantAttacker.attack(controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j), 2));
                                    }
                                }
                            }
                            controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                            controllerMatchMaking.getBattleManager().checkDestroyedUnities();
                            Painter.drawGraphic(controllerMatchMaking.getBattleManager(), controllerMatchMaking.getResource(),
                                    controllerMatchMaking.getPaneControlField(), controllerMatchMaking.getResourceOfBonuses());
                        }
                    }
                    controllerMatchMaking.getPaneControlField().setOnMouseClicked(controllerMatchMaking.getEventHandler());
                });
            }
        }
    };

    private final Bonus tankBuffalo = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };


    /**
     * Бонус: "Интенсивная выработка"
     * Стоимость: 4 ед. энергии;
     * Удваивает вырабатываемую энергию за ход до конца матча.
     */

    private static final Bonus intensiveProduction = new Bonus(4,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\4IntensiveProduction\\Sprite\\IntensiveProduction.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0) {
                if (controllerMatchMaking.getBattleManager().getPlayer().getColorType().equals("+")) {
                    additionalEnergyBlue *= 2;
                }
                if (controllerMatchMaking.getBattleManager().getPlayer().getColorType().equals("-")) {
                    additionalEnergyRed *= 2;
                }
                controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
            }
            controllerMatchMaking.setClick(true);
        }
    };

    private static int additionalEnergyBlue = 1;

    private static int additionalEnergyRed = 1;

    private final Bonus diversion = new Bonus(4) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {

        }
    };

    /**
     * Бонус: "Удвоенная подготовка"
     * Стоимость: 5 ед. энергии;
     * Удваивает число обучаемых автоматчиков и танков до конца хода.
     */

    private static final Bonus doubleTraining = new Bonus(5,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\5DoubleTraining\\Sprite\\DoubleTraining.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0) {
                System.out.println(controllerMatchMaking.getBattleManager().getHowICanProductTanks());
                System.out.println(controllerMatchMaking.getBattleManager().getHowICanProductArmy());
                controllerMatchMaking.getBattleManager().setHowICanProductTanks(controllerMatchMaking.getBattleManager().getHowICanProductTanks() * 2);
                controllerMatchMaking.getBattleManager().setHowICanProductArmy(controllerMatchMaking.getBattleManager().getHowICanProductArmy() * 2);
                controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
                System.out.println(controllerMatchMaking.getBattleManager().getHowICanProductTanks());
                System.out.println(controllerMatchMaking.getBattleManager().getHowICanProductArmy());
            }
            controllerMatchMaking.setClick(true);
        }
    };

    /**
     * Бонус: "Супер краны"
     * Стоимость: 5 ед. энергии;
     * Удваивает число возможных построек до конца хода.
     */

    private static final Bonus superCranes = new Bonus(5,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\5SuperCranes\\Sprite\\SuperCranes.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0) {
                controllerMatchMaking.getBattleManager().setHowICanBuild(controllerMatchMaking.getBattleManager().getHowICanBuild() * 2);
                controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
            }
            controllerMatchMaking.setClick(true);
        }
    };

    /**
     * Бонус: "Авиаудар"
     * Стоимость: 5 ед. энергии;
     * Наносит всем постройкам противника 1 ед. урона.
     */

    private static final Bonus airStrike = new Bonus(5,
            new ImageView(new Image("file:src\\Resources\\Bonuses\\5AirStrike\\Sprite\\AirStrike.png"))) {
        @Override
        public void run(ControllerMatchMaking controllerMatchMaking) {
            int currentEnergy = controllerMatchMaking.getBattleManager().getPlayer().getEnergy();
            if (currentEnergy - this.getEnergy() >= 0) {
                Pattern pattern = Pattern.compile("[hgbfwt]");
                Pattern patternBonus = Pattern.compile("[/]");
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        String currentUnity = controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).get(j);
                        Matcher matcher = pattern.matcher(currentUnity);
                        Matcher matcherBonus = patternBonus.matcher(currentUnity);
                        if (currentUnity.substring(3, 4).equals(controllerMatchMaking.getBattleManager().getOpponentPlayer().getColorType()) &&
                                (matcher.find() || matcherBonus.find())) {
                            currentUnity = AdjutantAttacker.attack(currentUnity, 1);
                            controllerMatchMaking.getBattleManager().getBattleField().getMatrix().get(i).set(j, currentUnity);
                            controllerMatchMaking.getBattleManager().getPlayer().setEnergy(currentEnergy - this.getEnergy());
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

    @Contract(pure = true)
    public static Bonus getClusterArrow() {
        return clusterArrow;
    }

    @Contract(pure = true)
    public static Bonus getCleanup() {
        return cleanup;
    }

    @Contract(pure = true)
    public static Bonus getBear() {
        return bear;
    }

    @Contract(pure = true)
    public static Bonus getHeavyTankHammer() {
        return heavyTankHammer;
    }

    @Contract(pure = true)
    public static Bonus getRocketCorsair() {
        return rocketCorsair;
    }

    @Contract(pure = true)
    public static Bonus getIntensiveProduction() {
        return intensiveProduction;
    }

    @Contract(pure = true)
    public static Bonus getAttackOfTank() {
        return attackOfTank;
    }


    @Contract(pure = true)
    public static Bonus getTankCharge() {
        return tankCharge;
    }

    @Contract(pure = true)
    public static Bonus getDoubleTraining() {
        return doubleTraining;
    }

    @Contract(pure = true)
    public static Bonus getSuperCranes() {
        return superCranes;
    }

    @Contract(pure = true)
    public static Bonus getAirStrike() {
        return airStrike;
    }
}
