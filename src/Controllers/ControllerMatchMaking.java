package Controllers;

import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantSleeper;
import BattleFields.*;
import Graphics.Painter;
import Players.Player;
import ResourceInit.Resource;
import Unities.Unity;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс ControllerMatchMaking реализует интерфейс Initializable.
 * Занимается инициализацией и событиями игры, а именно:
 * 1.) инициализация событий;
 * 2.) дилигирующий метод "смена хода";
 * 3.) отрисовка графики;
 */

public final class ControllerMatchMaking implements Initializable {

    @FXML
    private Text textCountDown;
    @FXML
    private Pane paneControlField;
    @FXML
    private AnchorPane paneGlobal;
    @FXML
    private Pane paneControlBuild;
    @FXML
    private Pane paneControlArmy;
    @FXML
    private Pane paneTriggerEnergy;

    //Глобальные кнопки:
    @FXML
    private Button buttonMenu;
    @FXML
    private Button buttonInfo;
    @FXML
    private Button buttonCreateArmy;
    @FXML
    private Button buttonBuild;
    @FXML
    private Button buttonEndTurn;

    //Создание построек:
    @FXML
    private Button buttonBuildBarracks;
    @FXML
    private Button buttonBuildWall;
    @FXML
    private Button buttonUpgradeBuild;
    @FXML
    private Button buttonBuildGenerator;
    @FXML
    private Button buttonBuildTurret;
    @FXML
    private Button buttonBuildFactory;

    //Создание армии:
    @FXML
    private Button buttonProductGunner1;
    @FXML
    private Button buttonProductGunner2;
    @FXML
    private Button buttonProductGunner3;
    @FXML
    private Button buttonProductTank1;
    @FXML
    private Button buttonProductTank2;
    @FXML
    private Button buttonProductTank3;

    //Сообщения:
    @FXML
    private ImageView imageMessageEnergy;
    @FXML
    private ImageView imageMessageGenerator;
    @FXML
    private ImageView imageMessageBarracks;
    @FXML
    private ImageView imageMessageFactory;
    @FXML
    private ImageView imageMessageWall;
    @FXML
    private ImageView imageMessageTurret;
    @FXML
    private ImageView imageMessageUpgrade;

    //Механизм внутренней игры:
    private BattleManager battleManager = new BattleManager(new BattleField());

    //Графические ресурсы:
    private Resource resource = new Resource();

    //Триггеры:
    private Boolean click = false; //Небольшая защелка для кликов мыши
    private Unity unit;
    private String labelUnit = ""; //Определитель действия

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMessages();
        battleManager.initializeField();
        battleManager.getBattleField().toString();
        buttonCreateArmy.setVisible(false);
        Painter.drawGraphic(battleManager, resource, paneControlField);
        initializeGameButtons();
        System.out.println(battleManager.getPlayer().getColorType());
    }

    private void nextTurn() {
        battleManager.nextTurnOfCurrentPlayer();
        labelUnit = "";
        System.out.println(battleManager.getPlayer().getColorType());
        System.out.println("Осталось построек: " + battleManager.getHowICanBuild());
        System.out.println("Осталось автоматчиков: " + battleManager.getHowICanProductArmy());
        System.out.println("Осталось танков: " + battleManager.getHowICanProductTanks());
    }


    private void launchTimer(Player player) {
        //Таймер:
        if (player.getColorType().equals("+")) {
            textCountDown.setFill(Paint.valueOf("Blue"));
        } else {
            textCountDown.setFill(Paint.valueOf("Red"));
        }
        final int[] countdown = {30};
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1), ae -> {
                    countdown[0]--;
                    textCountDown.setText(String.valueOf(countdown[0]));
                }
                )
        );
        int time = 30;
        timeline.setCycleCount(time);
        timeline.setOnFinished(event -> System.exit(1));
        timeline.play();
    }

    private void initializeMessages() {
        //Сообщение об энергии:
        paneTriggerEnergy.setOnMouseEntered(event -> {
            imageMessageEnergy.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageEnergy);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });

        paneTriggerEnergy.setOnMouseExited(event -> {
            imageMessageEnergy.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageEnergy);
            fadeTransition.setToValue(0);
            fadeTransition.play();
            buttonBuild.toFront();
        });

        //Сообщение о бараках:
        buttonBuildBarracks.setOnMouseEntered(event -> {
            imageMessageBarracks.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageBarracks);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
        buttonBuildBarracks.setOnMouseExited(event -> {
            imageMessageBarracks.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageBarracks);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });

        //Сообщение о генераторе:
        buttonBuildGenerator.setOnMouseEntered(event -> {
            imageMessageGenerator.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageGenerator);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
        buttonBuildGenerator.setOnMouseExited(event -> {
            imageMessageGenerator.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageGenerator);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });

        //Сообщение о заводе:
        buttonBuildFactory.setOnMouseEntered(event -> {
            imageMessageFactory.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageFactory);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
        buttonBuildFactory.setOnMouseExited(event -> {
            imageMessageFactory.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageFactory);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });

        //Сообщение о стене:
        buttonBuildWall.setOnMouseEntered(event -> {
            imageMessageWall.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageWall);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
        buttonBuildWall.setOnMouseExited(event -> {
            imageMessageWall.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageWall);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });

        //Сообщение о турели:
        buttonBuildTurret.setOnMouseEntered(event -> {
            imageMessageTurret.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageTurret);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
        buttonBuildTurret.setOnMouseExited(event -> {
            imageMessageTurret.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageTurret);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });

        //Сообщение об улучшении:
        buttonUpgradeBuild.setOnMouseEntered(event -> {
            imageMessageUpgrade.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageUpgrade);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
        buttonUpgradeBuild.setOnMouseExited(event -> {
            imageMessageUpgrade.toFront();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageMessageUpgrade);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });
    }

    private void initializeGameButtons() {
        //Следующий ход:
        buttonEndTurn.setOnMouseClicked(event -> {
            nextTurn();
            Painter.drawGraphic(battleManager, resource, paneControlField);
            if (battleManager.getHowICanProductArmy() - battleManager.getHowICanProductTanks() > 0) {
                buttonBuildFactory.setVisible(true);
            } else {
                buttonBuildFactory.setVisible(false);
            }
            if (battleManager.getHowICanProductTanks() > 0 || battleManager.getHowICanProductArmy() > 0) {
                buttonCreateArmy.setVisible(true);
            } else {
                buttonCreateArmy.setVisible(false);
            }
            buttonBuild.setVisible(true);
            paneControlBuild.setVisible(false);
            paneControlArmy.setVisible(false);
        });

        //Выбрать строительство:
        buttonBuild.setOnMouseClicked(event -> {
            paneControlBuild.setVisible(true);
            buttonCreateArmy.setVisible(false);
        });

        //Выбрать производство армии:
        buttonCreateArmy.setOnMouseClicked(event -> {
            paneControlArmy.setVisible(true);
            buttonBuild.setVisible(false);
        });

        //Постройка генератора:
        buttonBuildGenerator.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getGenerator();
            labelUnit = "generator";
        });

        //Постройка бараков:
        buttonBuildBarracks.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getBarracksHorizontal();
            labelUnit = "building";
        });

        //Постройка завода:
        buttonBuildFactory.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getFactoryHorizontal();
            labelUnit = "factory";
        });

        //Постройка турели:
        buttonBuildTurret.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getTurret();
            labelUnit = "turret";
        });

        //Постройка стены:
        buttonBuildWall.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getWall();
            labelUnit = "wall";
        });

        //Создание автоматчика:
        buttonProductGunner1.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getGunner();
            labelUnit = "gunner";
        });

        //Создание танка:
        buttonProductTank1.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getTank();
            labelUnit = "tank";
        });

        //Улучшение строения:
        buttonUpgradeBuild.setOnMouseClicked(event -> {
            click = !click;
            labelUnit = "upgradeBuilding";
        });

        //Инкапсуляция производства:
        EventHandler<? super MouseEvent> eventHandler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                {
                    try {
                        if (click) {
                            click = false;
                            Point pointClick = new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5));
                            //Если строите бараки или стену:
                            if (labelUnit.equals("building") && battleManager.getHowICanBuild() > 0) {
                                if (battleManager.checkConstructionOfBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                        battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                    battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);

                                }
                            }
                            //Если строите завод:
                            if (labelUnit.equals("factory") && battleManager.getHowCanBuildFactories() > 0 && battleManager.getHowICanBuild() > 0) {
                                if (battleManager.checkConstructionOfBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                        battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                    battleManager.setHowICanProductArmy(battleManager.getHowICanProductArmy() - 1);
                                }
                            }
                            //Если строите генератор:
                            if (labelUnit.equals("generator") && battleManager.getHowICanBuild() > 0 && battleManager.getHowICanBuild() <= 2 && !battleManager.isConstructedGenerator()) {
                                if (battleManager.checkConstructionOfBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                        battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                    battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);
                                    battleManager.setConstructedGenerator(true);
                                }
                            }
                            //Если создаем стену:
                            if (labelUnit.equals("wall") && battleManager.getHowICanBuild() > 0) {
                                if (battleManager.checkConstructionOfBuilding(pointClick, battleManager.getBarracksHorizontal(), battleManager.getPlayer()) &&
                                        battleManager.putDoubleWall(battleManager.getPlayer(), pointClick, unit)) {
                                    battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);

                                }
                            }
                            //Если создаем турель:
                            if (labelUnit.equals("turret") && battleManager.getHowICanBuild() > 0) {
                                if (battleManager.checkConstructionOfBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                        battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                    battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);
                                    AdjutantAttacker.radiusAttack(battleManager, pointClick, 2, 1);

                                }
                            }

                            //Если улучшаем строение:
                            if (labelUnit.equals("upgradeBuilding") && battleManager.getHowICanBuild() > 0) {
                                if (battleManager.upgradeBuilding(pointClick, battleManager.getPlayer())) { //Если удалось улучшить строение:
                                    battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);
                                }
                            }


                            //Если создаете автоматчика:
                            if (labelUnit.equals("gunner") && battleManager.getHowICanProductArmy() > 0) {
                                if (battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                    battleManager.setHowICanProductArmy(battleManager.getHowICanProductArmy() - 1);
                                }
                            }

                            //Если создаете танк:
                            if (labelUnit.equals("tank") && battleManager.getHowICanProductTanks() > 0) {
                                if (battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                    battleManager.setHowICanProductTanks(battleManager.getHowICanProductTanks() - 1);
                                }
                            }
                            //Если атакуем:
                        } else {
                            System.out.println("Выбрали юнита");
                            Point pointClick = new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5));
                            String clickedUnit = battleManager.getBattleField().getMatrix().get(pointClick.X()).get(pointClick.Y());
                            System.out.println("Юнит: " + clickedUnit);
                            if (clickedUnit.contains(battleManager.getPlayer().getColorType()) && clickedUnit.contains("!")) {
                                switch (clickedUnit.substring(4, 5)) {
                                    case "G":
                                        System.out.println("Это автоматчик: " + clickedUnit);
                                        paneControlField.setOnMouseClicked(secondEvent -> {
                                            Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                            System.out.println("Второй клик: ");
                                            String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                            if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                                Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                                Matcher matcher = pattern.matcher(targetAttackUnity);
                                                System.out.println(AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick));
                                                System.out.println("X" + pointSecondClick.X() + " " + "Y" + pointSecondClick.Y());
                                                if (matcher.find() && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                    for (int i = 0; i < 16; i++){
                                                        for (int j = 0; j < 16; j++){
                                                            String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                            String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                            if (attackerUnitID.equals(targetUnitID)){
                                                                battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                        AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
                                                            }
                                                        }
                                                    }
                                                    System.out.println("ATTACK!");
                                                    battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                            AdjutantSleeper.sleepUnity(clickedUnit));
                                                    battleManager.getBattleField().toString();
                                                    System.out.println("ZZZ: " + AdjutantSleeper.sleepUnity(clickedUnit));
                                                    battleManager.checkDestroyedUnities();
                                                    Painter.drawGraphic(battleManager, resource, paneControlField);
                                                    paneControlField.setOnMouseClicked(this);

                                                }
                                            }
                                            paneControlField.setOnMouseClicked(this);
                                        });
                                        break;
                                    case "T":
                                        System.out.println("Это танк: " + clickedUnit);
                                        paneControlField.setOnMouseClicked(secondEvent -> {
                                            Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                            System.out.println("Второй клик: ");
                                            String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                            if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                                Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                                Matcher matcher = pattern.matcher(targetAttackUnity);
                                                if (matcher.find()) {
                                                    for (int i = 0; i < 16; i++){
                                                        for (int j = 0; j < 16; j++){
                                                            String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                            String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                            if (attackerUnitID.equals(targetUnitID)){
                                                                battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                        AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 2));
                                                            }
                                                        }
                                                    }
                                                    System.out.println("ATTACK!");
                                                    battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                            AdjutantSleeper.sleepUnity(clickedUnit));
                                                    battleManager.getBattleField().toString();
                                                    System.out.println("ZZZ: " + AdjutantSleeper.sleepUnity(clickedUnit));
                                                    battleManager.checkDestroyedUnities();
                                                    Painter.drawGraphic(battleManager, resource, paneControlField);
                                                    paneControlField.setOnMouseClicked(this);

                                                }
                                            }
                                            paneControlField.setOnMouseClicked(this);
                                        });
                                        break;
                                }
                            }
                        }
                        //После события:
                        Painter.drawGraphic(battleManager, resource, paneControlField);
                        battleManager.getBattleField().toString();
                        battleManager.getIdentificationField().toString();
                        System.out.println();
                    } catch (Exception ignored) {
                    }
                }
            }
        };
        paneControlField.setOnMouseClicked(eventHandler);
    }
}