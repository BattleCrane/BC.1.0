package Controllers;

import BattleFields.BattleField;
import BattleFields.ControlBattler;
import BattleFields.DataUnit;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerMatchMaking implements Initializable {

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
    private ControlBattler controlBattler = new ControlBattler(new BattleField(), new ArrayList<DataUnit>());
    //Графические ресурсы:
    private Resource resource = new Resource();

    private Boolean click = false;
    private Unity unit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMessages();
        controlBattler.initializeField();
        controlBattler.getBattleField().toString();

        drawGraphic();
        initializeGameButtons();
        System.out.println(controlBattler.getPlayer().getColorType());
    }

    private void nextTurn() {
        controlBattler.nextTurnOfCurrentPlayer();
        System.out.println(controlBattler.getPlayer().getColorType());
        System.out.println(controlBattler.getHowICanBuild());
        System.out.println(controlBattler.getHowICanProductArmy());
        System.out.println(controlBattler.getHowICanProductTanks());
    }


    private void drawGraphic() {
        try {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    switch (controlBattler.getBattleField().getMatrix().get(i).get(j)) {
                        //Штаб:
                        case "+h'":
                            ImageView headquartersBlue = resource.getHeadquartersBlue();
                            headquartersBlue.setLayoutX(467);
                            headquartersBlue.setLayoutY(467);
                            paneControlField.getChildren().add(new Pane(headquartersBlue));
                            break;
                        case "-h'":
                            ImageView headquartersRed = resource.getHeadquartersRed();
                            headquartersRed.setLayoutX(2);
                            headquartersRed.setLayoutY(2);
                            paneControlField.getChildren().add(new Pane(headquartersRed));
                            break;
                        //Генератор:
                        case "+g'":
                            ImageView generatorLevel1Blue = resource.getGeneratorLevel1Blue();
                            generatorLevel1Blue.setLayoutX(33.5 * j);
                            generatorLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(generatorLevel1Blue));
                            break;
                        case "-g'":
                            ImageView generatorLevel1Red = resource.getGeneratorLevel1Red();
                            generatorLevel1Red.setLayoutX(33.5 * j);
                            generatorLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(generatorLevel1Red));
                            break;
                        //Бараки:
                        case "+b'":
                            ImageView barracksLevel1Blue = resource.getBarracksLevel1Blue();
                            barracksLevel1Blue.setLayoutX(33.5 * j);
                            barracksLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(barracksLevel1Blue));
                            break;
                        case "-b'":
                            ImageView barracksLevel1Red = resource.getBarracksLevel1Red();
                            barracksLevel1Red.setLayoutX(33.5 * j);
                            barracksLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(barracksLevel1Red));
                            break;
                        //Завод:
                        case "+f'":
                            ImageView factoryLevel1Blue = resource.getFactoryLevel1Blue();
                            factoryLevel1Blue.setLayoutX(33.5 * j);
                            factoryLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(factoryLevel1Blue));
                            break;
                        case "-f'":
                            ImageView factoryLevel1Red = resource.getFactoryLevelRed();
                            factoryLevel1Red.setLayoutX(33.5 * j);
                            factoryLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(factoryLevel1Red));
                            break;
                        //Турель:
                        case "+t'":
                            ImageView turretLevel1Blue = resource.getTurretLevel1Blue();
                            turretLevel1Blue.setLayoutX(33.5 * j);
                            turretLevel1Blue.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(turretLevel1Blue));
                            break;
                        case "-t'":
                            ImageView turretLevel1Red = resource.getTurretLevel1Red();
                            turretLevel1Red.setLayoutX(33.5 * j);
                            turretLevel1Red.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(new Pane(turretLevel1Red));
                            break;
                        //Стена:
                        case "-w'":
                            ImageView imageViewRedWall = resource.getWallRed();
                            imageViewRedWall.setLayoutX(33.5 * j);
                            imageViewRedWall.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewRedWall);
                            break;
                        case "+w'":
                            ImageView imageViewBlueWall = resource.getWallBlue();
                            imageViewBlueWall.setLayoutX(33.5 * j);
                            imageViewBlueWall.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewBlueWall);
                            break;
                        //Автоматчик:
                        case "+G'":
                            ImageView imageViewBlueGunner = resource.getGunnerBlue();
                            imageViewBlueGunner.setLayoutX(33.5 * j);
                            imageViewBlueGunner.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewBlueGunner);
                            break;
                        case "-G'":
                            ImageView imageViewRedGunner = resource.getGunnerRed();
                            imageViewRedGunner.setLayoutX(33.5 * j);
                            imageViewRedGunner.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewRedGunner);
                            break;
                        //Танк:
                        case "+T'":
                            ImageView imageViewBlueTank = resource.getTankBlue();
                            imageViewBlueTank.setLayoutX(33.5 * j);
                            imageViewBlueTank.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewBlueTank);
                            break;
                        case "-T'":
                            ImageView imageViewRedTank = resource.getTankRed();
                            imageViewRedTank.setLayoutX(33.5 * j);
                            imageViewRedTank.setLayoutY(33.5 * i);
                            paneControlField.getChildren().add(imageViewRedTank);
                            break;
                        //Пустое поле:
                        case "  0":
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
        buttonEndTurn.setOnMouseClicked(event -> nextTurn());
        //Постройка генератора:
        buttonBuildGenerator.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getGenerator();
        });
        //Постройка бараков:
        buttonBuildBarracks.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getBarracksHorisontal();
        });
        //Постройка завода:
        buttonBuildFactory.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getFactoryHorisontal();
        });
        //Постройка турели:
        buttonBuildTurret.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getTurret();
        });
        //Постройка стены:
        buttonBuildWall.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getWall();
        });
        //Создание автоматчика:
        buttonProductGunner1.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getGunner();
        });
        //Создание танка:
        buttonProductTank1.setOnMouseClicked(event -> {
            click = !click;
            unit = controlBattler.getTank();
        });
        //Инкапсуляция производства:
        paneControlField.setOnMouseClicked(event -> {
            if (click) {
                click = !click;
                Point pointClick = new Point((int) (event.getY() / 33.5), (int) (event.getX() / 33.5));
                controlBattler.putUnity(controlBattler.getPlayer(), pointClick, unit);
                controlBattler.getBattleField().toString();
                drawGraphic();
                System.out.println();
            }
        });
    }
}