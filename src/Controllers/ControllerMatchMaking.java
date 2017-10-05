package Controllers;

import BattleFields.Attacker;
import BattleFields.BattleField;
import BattleFields.ControlBattler;
import BattleFields.Point;
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
    private ControlBattler controlBattler = new ControlBattler(new BattleField());
    //Графические ресурсы:
    private Resource resource = new Resource();

    private Boolean click = false;
    private Unity unit;
    private String labelUnit = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMessages();
        controlBattler.initializeField();
        controlBattler.getBattleField().toString();
        controlBattler.getIdentificationField().toString();
        buttonCreateArmy.setVisible(false);
        Painter.drawGraphic(controlBattler, resource, paneControlField);
        initializeGameButtons();
        System.out.println(controlBattler.getPlayer().getColorType());
    }

    private void nextTurn() {
        controlBattler.nextTurnOfCurrentPlayer();
        labelUnit = "";
        System.out.println(controlBattler.getPlayer().getColorType());
        System.out.println("Осталось построек: " + controlBattler.getHowICanBuild());
        System.out.println("Осталось автоматчиков: " + controlBattler.getHowICanProductArmy());
        System.out.println("Осталось танков: " + controlBattler.getHowICanProductTanks());
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
            Painter.drawGraphic(controlBattler, resource, paneControlField);
            if (controlBattler.getHowICanProductArmy() - controlBattler.getHowICanProductTanks() > 0) {
                buttonBuildFactory.setVisible(true);
            } else {
                buttonBuildFactory.setVisible(false);
            }
            if (controlBattler.getHowICanProductTanks() > 0 || controlBattler.getHowICanProductArmy() > 0) {
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
            unit = controlBattler.getGenerator();
            labelUnit = "generator";
        });

        //Постройка бараков:
        buttonBuildBarracks.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getBarracksHorizontal();
            labelUnit = "building";
        });

        //Постройка завода:
        buttonBuildFactory.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getFactoryHorizontal();
            labelUnit = "factory";
        });

        //Постройка турели:
        buttonBuildTurret.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getTurret();
            labelUnit = "turret";
        });

        //Постройка стены:
        buttonBuildWall.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getWall();
            labelUnit = "wall";
        });

        //Создание автоматчика:
        buttonProductGunner1.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getGunner();
            labelUnit = "gunner";
        });

        //Создание танка:
        buttonProductTank1.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getTank();
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
                            if (labelUnit.equals("building") && controlBattler.getHowICanBuild() > 0) {
                                if (controlBattler.checkConstructionOfBuilding(pointClick, unit, controlBattler.getPlayer()) &&
                                        controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                                    controlBattler.setHowICanBuild(controlBattler.getHowICanBuild() - 1);

                                }
                            }
                            //Если строите завод:
                            if (labelUnit.equals("factory") && controlBattler.getHowCanBuildFactories() > 0 && controlBattler.getHowICanBuild() > 0) {
                                if (controlBattler.checkConstructionOfBuilding(pointClick, unit, controlBattler.getPlayer()) &&
                                        controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                                    controlBattler.setHowICanProductArmy(controlBattler.getHowICanProductArmy() - 1);
                                }
                            }
                            //Если строите генератор:
                            if (labelUnit.equals("generator") && controlBattler.getHowICanBuild() > 0 && controlBattler.getHowICanBuild() <= 2 && !controlBattler.isConstructedGenerator()) {
                                if (controlBattler.checkConstructionOfBuilding(pointClick, unit, controlBattler.getPlayer()) &&
                                        controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                                    controlBattler.setHowICanBuild(controlBattler.getHowICanBuild() - 1);
                                    controlBattler.setConstructedGenerator(true);
                                }
                            }
                            //Если создаем стену:
                            if (labelUnit.equals("wall") && controlBattler.getHowICanBuild() > 0) {
                                if (controlBattler.checkConstructionOfBuilding(pointClick, controlBattler.getBarracksHorizontal(), controlBattler.getPlayer()) &&
                                        controlBattler.putDoubleWall(controlBattler.getPlayer(), pointClick, unit)) {
                                    controlBattler.setHowICanBuild(controlBattler.getHowICanBuild() - 1);

                                }
                            }
                            //Если создаем турель:
                            if (labelUnit.equals("turret") && controlBattler.getHowICanBuild() > 0) {
                                if (controlBattler.checkConstructionOfBuilding(pointClick, unit, controlBattler.getPlayer()) &&
                                        controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                                    controlBattler.setHowICanBuild(controlBattler.getHowICanBuild() - 1);
                                    Attacker.radiusAttack(controlBattler, pointClick, 2, 1);

                                }
                            }

                            //Если улучшаем строение:
                            if (labelUnit.equals("upgradeBuilding") && controlBattler.getHowICanBuild() > 0) {
                                if (controlBattler.upgradeBuilding(pointClick, controlBattler.getPlayer())) { //Если удалось улучшить строение:
                                    controlBattler.setHowICanBuild(controlBattler.getHowICanBuild() - 1);
                                }
                            }


                            //Если создаете автоматчика:
                            if (labelUnit.equals("gunner") && controlBattler.getHowICanProductArmy() > 0) {
                                if (controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                                    controlBattler.setHowICanProductArmy(controlBattler.getHowICanProductArmy() - 1);
                                }
                            }

                            //Если создаете танк:
                            if (labelUnit.equals("tank") && controlBattler.getHowICanProductTanks() > 0) {
                                if (controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                                    controlBattler.setHowICanProductTanks(controlBattler.getHowICanProductTanks() - 1);
                                }
                            }
                            //Если атакуем:
                        } else {
                            System.out.println("Выбрали юнита");
                            Point pointClick = new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5));
                            String clickedUnit = controlBattler.getBattleField().getMatrix().get(pointClick.getX()).get(pointClick.getY());
                            System.out.println("Юнит: " + clickedUnit);
                            if (clickedUnit.contains(controlBattler.getPlayer().getColorType()) && clickedUnit.contains("!")) {
                                switch (clickedUnit.substring(4, 5)) {
                                    case "G":
                                        System.out.println("Это автоматчик: " + clickedUnit);
                                        paneControlField.setOnMouseClicked(secondEvent -> {
                                            Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                            System.out.println("Второй клик: ");
                                            if (!controlBattler.getBattleField().getMatrix().get(pointSecondClick.getX()).get(pointSecondClick.getY()).
                                                    contains(controlBattler.getPlayer().getColorType())) {
                                                Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                                Matcher matcher = pattern.matcher(controlBattler.getBattleField().getMatrix().get(pointSecondClick.getX()).get(pointSecondClick.getY()));
                                                if (matcher.find()) {
                                                    for (int i = 0; i < 16; i++){
                                                        for (int j = 0; j < 16; j++){
                                                            if (controlBattler.getIdentificationField().getMatrix().get(i).get(j).
                                                                    equals(controlBattler.getIdentificationField().getMatrix().get(pointSecondClick.getX()).get(pointSecondClick.getY()))){
                                                                controlBattler.getBattleField().getMatrix().get(i).set(j,
                                                                        Attacker.attack(controlBattler.getBattleField().getMatrix().get(i).get(j), 1));
                                                            }
                                                        }
                                                    }
                                                    System.out.println("ATTACK!");
                                                    controlBattler.getBattleField().getMatrix().get(pointClick.getX()).set(pointClick.getY(),
                                                            controlBattler.sleepUnity(controlBattler.getBattleField().getMatrix().get(pointClick.getX()).get(pointClick.getY())));
                                                    controlBattler.getBattleField().toString();
                                                    System.out.println("ZZZ: " + controlBattler.sleepUnity(controlBattler.getBattleField().getMatrix().get(pointClick.getX()).get(pointClick.getY())));
                                                    paneControlField.setOnMouseClicked(this);
                                                    controlBattler.checkDestroyedUnities();
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
                                            if (!controlBattler.getBattleField().getMatrix().get(pointSecondClick.getX()).get(pointSecondClick.getY()).
                                                    contains(controlBattler.getPlayer().getColorType())) {
                                                Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                                Matcher matcher = pattern.matcher(controlBattler.getBattleField().getMatrix().get(pointSecondClick.getX()).get(pointSecondClick.getY()));
                                                if (matcher.find()) {
                                                    controlBattler.getBattleField().getMatrix().get(pointSecondClick.getX()).set(pointSecondClick.getY(),
                                                            Attacker.attack(controlBattler.getBattleField().getMatrix().get(pointSecondClick.getX()).get(pointSecondClick.getY()), 1));
                                                    System.out.println("ATTACK!");
                                                    controlBattler.getBattleField().getMatrix().get(pointClick.getX()).set(pointClick.getY(),
                                                            controlBattler.sleepUnity(controlBattler.getBattleField().getMatrix().get(pointClick.getX()).get(pointClick.getY())));
                                                    controlBattler.getBattleField().toString();
                                                    System.out.println("ZZZ: " + controlBattler.sleepUnity(controlBattler.getBattleField().getMatrix().get(pointClick.getX()).get(pointClick.getY())));
                                                    paneControlField.setOnMouseClicked(this);
                                                }
                                            }
                                        });
                                        break;
                                }
                            }
                        }
                        //После события:
                        Painter.drawGraphic(controlBattler, resource, paneControlField);
                        controlBattler.getBattleField().toString();
                        controlBattler.getIdentificationField().toString();
                        System.out.println();
                    } catch (Exception ignored) {
                    } //Может выскочить null

                }
            }
        };


        paneControlField.setOnMouseClicked(eventHandler);
    }
}