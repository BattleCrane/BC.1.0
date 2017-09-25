package Controllers;

import BattleFields.BattleField;
import BattleFields.ControlBattler;
import BattleFields.Point;
import Players.Player;
import ResourceInit.Resource;
import Unities.Unity;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

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
        buttonCreateArmy.setVisible(false);
        drawGraphic();
        initializeGameButtons();
        System.out.println(controlBattler.getPlayer().getColorType());
    }

    private void nextTurn() {
        controlBattler.nextTurnOfCurrentPlayer();
        System.out.println(controlBattler.getPlayer().getColorType());
        System.out.println("Осталось построек: " + controlBattler.getHowICanBuild());
        System.out.println("Осталось автоматчиков: " + controlBattler.getHowICanProductArmy());
        System.out.println("Осталось танков: " + controlBattler.getHowICanProductTanks());
    }


    private void drawGraphic() {
        try {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    switch (controlBattler.getBattleField().getMatrix().get(i).get(j)) {
                        //Штаб:
                        case "8^?+h'":
                            ImageView headquartersBlue = resource.getHeadquartersBlue();
                            headquartersBlue.setLayoutX(467);
                            headquartersBlue.setLayoutY(467);
                            paneControlField.getChildren().add(new Pane(headquartersBlue));
                            break;
                        case "8^?-h'":
                            ImageView headquartersRed = resource.getHeadquartersRed();
                            headquartersRed.setLayoutX(2);
                            headquartersRed.setLayoutY(2);
                            paneControlField.getChildren().add(new Pane(headquartersRed));
                            break;
                        //Генератор:
                        case "1^?+g'":
                            ImageView generatorLevel1Blue = resource.getGeneratorLevel1Blue();
                            generatorLevel1Blue.setLayoutX(33.5 * j);
                            generatorLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(generatorLevel1Blue));
                            break;
                        case "1^?-g'":
                            ImageView generatorLevel1Red = resource.getGeneratorLevel1Red();
                            generatorLevel1Red.setLayoutX(33.5 * j);
                            generatorLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(generatorLevel1Red));
                            break;
                        //Бараки:
                        case "1^?+b'":
                            ImageView barracksLevel1Blue = resource.getBarracksLevel1Blue();
                            barracksLevel1Blue.setLayoutX(33.5 * j);
                            barracksLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(barracksLevel1Blue));
                            break;
                        case "1^?-b'":
                            ImageView barracksLevel1Red = resource.getBarracksLevel1Red();
                            barracksLevel1Red.setLayoutX(33.5 * j);
                            barracksLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(barracksLevel1Red));
                            break;
                        //Завод:
                        case "1^?+f'":
                            ImageView factoryLevel1Blue = resource.getFactoryLevel1Blue();
                            factoryLevel1Blue.setLayoutX(33.5 * j);
                            factoryLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(factoryLevel1Blue));
                            break;
                        case "1^?-f'":
                            ImageView factoryLevel1Red = resource.getFactoryLevelRed();
                            factoryLevel1Red.setLayoutX(33.5 * j);
                            factoryLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(factoryLevel1Red));
                            break;
                        //Турель:
                        case "1^?+t'":
                            ImageView turretLevel1Blue = resource.getTurretLevel1Blue();
                            turretLevel1Blue.setLayoutX(33.5 * j);
                            turretLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(turretLevel1Blue));
                            break;
                        case "1^?-t'":
                            ImageView turretLevel1Red = resource.getTurretLevel1Red();
                            turretLevel1Red.setLayoutX(33.5 * j);
                            turretLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(turretLevel1Red));
                            break;
                        //Стена:
                        case "4^?-w'":
                            ImageView imageViewRedWall = resource.getWallRed();
                            imageViewRedWall.setLayoutX(33.5 * j);
                            imageViewRedWall.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewRedWall);
                            break;
                        case "4^?+w'":
                            ImageView imageViewBlueWall = resource.getWallBlue();
                            imageViewBlueWall.setLayoutX(33.5 * j);
                            imageViewBlueWall.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewBlueWall);
                            break;
                        //Автоматчик:
                        case "1^?+G'":
                            ImageView imageViewBlueGunner = resource.getGunnerBlue();
                            imageViewBlueGunner.setLayoutX(33.5 * j);
                            imageViewBlueGunner.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewBlueGunner);
                            break;
                        case "1^?-G'":
                            ImageView imageViewRedGunner = resource.getGunnerRed();
                            imageViewRedGunner.setLayoutX(33.5 * j);
                            imageViewRedGunner.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewRedGunner);
                            break;
                        //Танк:
                        case "2^?+T'":
                            ImageView imageViewBlueTank = resource.getTankBlue();
                            imageViewBlueTank.setLayoutX(33.5 * j);
                            imageViewBlueTank.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewBlueTank);
                            break;
                        case "2^?-T'":
                            ImageView imageViewRedTank = resource.getTankRed();
                            imageViewRedTank.setLayoutX(33.5 * j);
                            imageViewRedTank.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewRedTank);
                            break;
                        //Пустое поле:
                        case "     0":
                            ImageView imageViewCell = new ImageView(new Image("file:src\\Resources\\BattleFields\\Cell.png"));
                            imageViewCell.setFitWidth(33.5);
                            imageViewCell.setFitHeight(33.5);
                            imageViewCell.setLayoutX(33.5 * j);
                            imageViewCell.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewCell);
                            break;
                    }
                }
            }

        } catch (Exception ignored) {
        }
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
            if (controlBattler.getHowICanProductArmy() > 0 || controlBattler.getHowICanProductTanks() > 0) {
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
            unit = controlBattler.getBarracksHorisontal();
            labelUnit = "building";
        });

        //Постройка завода:
        buttonBuildFactory.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getFactoryHorisontal();
            labelUnit = "factory";
        });

        //Постройка турели:
        buttonBuildTurret.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getTurret();
            labelUnit = "building";
        });

        //Постройка стены:
        buttonBuildWall.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getWall();
            labelUnit = "building";
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
        paneControlField.setOnMouseClicked(event -> {
            if (click) {
                click = false;
                Point pointClick = new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5));
                //Если строите бараки или стену:
                if (labelUnit.equals("building") && controlBattler.getHowICanBuild() > 0) {
                    if (controlBattler.checkConstructionOfBuilding(pointClick, unit, controlBattler.getPlayer()) &&
                            controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                        controlBattler.setHowICanBuild(controlBattler.getHowICanBuild() - 1);
                        System.out.println("Осталось построек: " + controlBattler.getHowICanBuild());
                    }
                }
                //Если строите завод:
                if (labelUnit.equals("factory") && controlBattler.getHowCanBuildFactories() > 0 && controlBattler.getHowICanBuild() > 0) {
                    if (controlBattler.checkConstructionOfBuilding(pointClick, unit, controlBattler.getPlayer()) &&
                            controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                        controlBattler.setHowICanProductArmy(controlBattler.getHowICanProductArmy() - 1);
                        System.out.println("Осталось заводов: " + controlBattler.getHowICanProductArmy());
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
                //Если улучшаем строение:
                if (labelUnit.equals("upgradeBuilding")){
                    if (controlBattler.upgradeBuilding(pointClick, controlBattler.getPlayer())) { //Если удалось улучшить строение:
                        controlBattler.setHowICanBuild(controlBattler.getHowICanBuild() - 1);
                    }
                }


                //Если создаете автоматчика:
                if (labelUnit.equals("gunner") && controlBattler.getHowICanProductArmy() > 0) {
                    if (controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit)) {
                        controlBattler.setHowICanProductArmy(controlBattler.getHowICanProductArmy() - 1);
                        System.out.println("Осталось автоматчиков: " + controlBattler.getHowICanProductArmy());
                    }
                }

                drawGraphic();
                controlBattler.getBattleField().toString();
                System.out.println();
            }
        });
    }
}