package Controllers;

import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantFielder;
import Adjutants.AdjutantSleeper;
import BattleFields.*;
import Bonuses.Bonus;
import Graphics.Painter;
import Players.Player;
import ResourceInit.Resource;
import Bonuses.ControllerBonusesCollection;
import ResourceInit.ResourceOfBonuses;
import Unities.Unity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.Contract;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @FXML
    private Pane paneControlSupport;

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
    @FXML
    private Button buttonSupport;

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

    //Механизм внутренней игры:
    private BattleManager battleManager = new BattleManager(new BattleField());

    //Графические ресурсы:
    private final Resource resource = new Resource();
    private final ResourceOfBonuses resourceOfBonuses = new ResourceOfBonuses();

    //Триггеры:
    private boolean click = false; //Небольшая защелка для кликов мыши
    private boolean isClickButtonOfBonus = false;
    private Unity unit;
    private String labelUnit = ""; //Определитель действия

    private AdjutantFielder adjutantFielder = new AdjutantFielder();

    //Управляющий базовыми событиями:
    private EventHandler<? super MouseEvent> eventHandler = new EventHandler<>() {
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
                                for (int i = 0; i < 16; i++) {
                                    for (int j = 0; j < 16; j++) {
                                        String currentUnity = battleManager.getBattleField().getMatrix().get(j).get(i);
                                        if (currentUnity.contains(battleManager.getPlayer().getColorType() + "Q")) {
                                            int temp = Integer.parseInt(currentUnity.substring(1, 2)) + 1;
                                            currentUnity = currentUnity.substring(0, 1) + temp + currentUnity.substring(2);
                                            battleManager.getBattleField().getMatrix().get(j).set(i, currentUnity);
                                        }
                                    }
                                }
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
                        String clickedUnitID = battleManager.getIdentificationField().getMatrix().get(pointClick.X()).get(pointClick.Y());
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            System.out.println(AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick));
                                            System.out.println("X" + pointSecondClick.X() + " " + "Y" + pointSecondClick.Y());
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
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
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
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
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                                                paneControlField.setOnMouseClicked(this);

                                            }
                                        }
                                        paneControlField.setOnMouseClicked(this);
                                    });
                                    break;
                                case "H":
                                    System.out.println("Это тяжелый автоматчик: " + clickedUnit);
                                    paneControlField.setOnMouseClicked(secondEvent -> {
                                        Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                        System.out.println("Второй клик: ");
                                        String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                            Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 3));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.checkDestroyedUnities();
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                                                paneControlField.setOnMouseClicked(this);

                                            }
                                        }
                                        paneControlField.setOnMouseClicked(this);
                                    });
                                    break;
                                case "h":
                                    System.out.println("Это штаб: " + clickedUnit);
                                    paneControlField.setOnMouseClicked(secondEvent -> {
                                        Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                        System.out.println("Второй клик: ");
                                        String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                            Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
                                                        }
                                                    }
                                                }
                                                ControllerBonusesCollection.setCountShortsForHeadquarters(ControllerBonusesCollection.getCountShortsForHeadquarters() - 1);
                                                System.out.println("ATTACK!");
                                                if (ControllerBonusesCollection.getCountShortsForHeadquarters() == 0) {
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
                                                battleManager.getBattleField().toString();
                                                battleManager.checkDestroyedUnities();
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                                                paneControlField.setOnMouseClicked(this);
                                            }
                                        }
                                        paneControlField.setOnMouseClicked(this);
                                    });
                                    break;
                                case "C":
                                    System.out.println("Это кластерный автоматчик: " + clickedUnit);
                                    paneControlField.setOnMouseClicked(secondEvent -> {
                                        Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                        System.out.println("Второй клик: ");
                                        String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                            Pattern pattern = Pattern.compile("[hgbfwt]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            if (matcher.find() && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                List<Integer> listX = new ArrayList<>();
                                                List<Integer> listY = new ArrayList<>();
                                                int damage = 0;
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            listX.add(i);
                                                            listY.add(j);
                                                            damage++;
                                                        }
                                                    }
                                                }
                                                for (int i = 0; i < listX.size(); i++) {
                                                    battleManager.getBattleField().getMatrix().get(listX.get(i)).set(listY.get(i),
                                                            AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(listX.get(i)).get(listY.get(i)), damage));
                                                }
                                                System.out.println("ATTACK! " + damage);
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.checkDestroyedUnities();
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                                                paneControlField.setOnMouseClicked(this);
                                            }
                                        }
                                        paneControlField.setOnMouseClicked(this);
                                    });
                                    break;
                                case "B":
                                    System.out.println("Это медведь: " + clickedUnit);
                                    paneControlField.setOnMouseClicked(secondEvent -> {
                                        Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                        System.out.println("Второй клик: ");
                                        String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                            Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j),
                                                                            ControllerBonusesCollection.getBearDamage(battleManager)));
                                                            System.out.println(ControllerBonusesCollection.getBearDamage(battleManager));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.checkDestroyedUnities();
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                                                paneControlField.setOnMouseClicked(this);

                                            }
                                        }
                                        paneControlField.setOnMouseClicked(this);
                                    });
                                    break;
                                case "E":
                                    System.out.println("Это Тяжелый танк 'Молот': " + clickedUnit);
                                    paneControlField.setOnMouseClicked(secondEvent -> {
                                        Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                        System.out.println("Второй клик: ");
                                        String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                            Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 3));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + AdjutantSleeper.sleepUnity(clickedUnit));
                                                battleManager.checkDestroyedUnities();
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                                                paneControlField.setOnMouseClicked(this);

                                            }
                                        }
                                        paneControlField.setOnMouseClicked(this);
                                    });
                                    break;
                                case "Q":
                                    System.out.println("Это танк 'Буффало': " + clickedUnit);
                                    paneControlField.setOnMouseClicked(secondEvent -> {
                                        Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                        System.out.println("Второй клик: ");
                                        String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                            Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            Pattern patternBonus = Pattern.compile("[oHeCBEiuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 2));
                                                        }
                                                    }
                                                }
                                                if (!clickedUnit.substring(1, 2).equals("0")) {
                                                    int shotsOfTankBuffalo = Integer.parseInt(clickedUnit.substring(1, 2)) - 1;
                                                    String sameClickedUnit = clickedUnit;
                                                    sameClickedUnit = sameClickedUnit.substring(0, 1) + shotsOfTankBuffalo + sameClickedUnit.substring(2);
                                                    if (shotsOfTankBuffalo == 0) {
                                                        battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(), AdjutantSleeper.sleepUnity(sameClickedUnit));
                                                    } else {
                                                        battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(), sameClickedUnit);
                                                    }

                                                }
                                                battleManager.getBattleField().toString();
                                                battleManager.checkDestroyedUnities();
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                                                paneControlField.setOnMouseClicked(this);
                                            }
                                        }
                                        paneControlField.setOnMouseClicked(this);
                                    });
                                    break;
                                case "i":
                                    System.out.println("Это форт: " + clickedUnit);
                                    paneControlField.setOnMouseClicked(secondEvent -> {
                                        Point pointSecondClick = new Point((int) (secondEvent.getY() / 33.5), (int) (secondEvent.getX() / 33.5));
                                        System.out.println("Второй клик: ");
                                        String targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                                            Pattern pattern = Pattern.compile("[hgbfwtGT]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            Pattern patternBonus = Pattern.compile("[oHeCBEiuQ]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && AdjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    AdjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
                                                        }
                                                    }
                                                }
                                                for (int a = 0; a < 16; a++) {
                                                    for (int b = 0; b < 16; b++) {
                                                        String currentFort = battleManager.getBattleField().getMatrix().get(b).get(a);
                                                        if (clickedUnitID.equals(battleManager.getIdentificationField().getMatrix().get(b).get(a)) &&
                                                                !currentFort.substring(1, 2).equals("0")) {
                                                            int shotsOfFort = Integer.parseInt(currentFort.substring(1, 2)) - 1;
                                                            currentFort = currentFort.substring(0, 1) + shotsOfFort + currentFort.substring(2);
                                                            if (shotsOfFort == 0) {
                                                                battleManager.getBattleField().getMatrix().get(b).set(a, AdjutantSleeper.sleepUnity(currentFort));
                                                            } else {
                                                                battleManager.getBattleField().getMatrix().get(b).set(a, currentFort);
                                                            }

                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");

                                                battleManager.getBattleField().toString();
                                                battleManager.checkDestroyedUnities();
                                                Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
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
                    Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
                    battleManager.getBattleField().toString();
                    System.out.println();
                } catch (Exception ignored) {
                }
            }
        }
    };


    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        battleManager.initializeField();
        battleManager.getBattleField().toString();
        buttonCreateArmy.setVisible(false);
        Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
        initializeGameButtons();
        System.out.println(battleManager.getPlayer().getColorType());
        initializeBonuses(battleManager);
        ControllerBonusesCollection.showBonuses(this, battleManager.getPlayer(), paneControlSupport);
    }

    private void nextTurn() {
        ControllerBonusesCollection.flush(paneControlSupport, battleManager);
        battleManager.checkDestroyedUnities();
        battleManager.nextTurnOfCurrentPlayer();
        ControllerBonusesCollection.showBonuses(this, battleManager.getPlayer(), paneControlSupport);
        labelUnit = "";
        System.out.println(battleManager.getPlayer().getColorType());
        System.out.println("Осталось построек: " + battleManager.getHowICanBuild());
        System.out.println("Осталось автоматчиков: " + battleManager.getHowICanProductArmy());
        System.out.println("Осталось танков: " + battleManager.getHowICanProductTanks());
        System.out.println("Осталось энергии: " + battleManager.getPlayer().getEnergy());
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

    private void initializeGameButtons() {
        //Следующий ход:
        buttonEndTurn.setOnMouseClicked(event -> {
            nextTurn();
            Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
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

        //Выбрать поддержку:
        buttonSupport.setOnMouseClicked(event -> {
            if (!isClickButtonOfBonus) {
                paneControlSupport.setVisible(true);
                isClickButtonOfBonus = true;
                buttonSupport.toFront();
            } else {
                paneControlSupport.setVisible(false);
                isClickButtonOfBonus = false;
            }

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
            adjutantFielder.fillZones(battleManager);
            battleManager.getBattleField().toString();
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
        paneControlField.setOnMouseClicked(eventHandler);
    }

    private void initializeBonuses(BattleManager battleManager) {
        battleManager.getPlayerBlue().setListOfBonuses(Arrays.asList(
                ControllerBonusesCollection.getObstacle(),
                ControllerBonusesCollection.getAmbulance(),
                ControllerBonusesCollection.getCombatReadiness(),
                ControllerBonusesCollection.getHeavyShells(),
                ControllerBonusesCollection.getEnergyBattery(),
                ControllerBonusesCollection.getExplosive(),
                ControllerBonusesCollection.getFightingHeadquarters(),
                ControllerBonusesCollection.getClusterArrow(),
                ControllerBonusesCollection.getCleanup(),
                ControllerBonusesCollection.getBear(),
                ControllerBonusesCollection.getHeavyTankHammer(),
                ControllerBonusesCollection.getRocketCorsair(),
                ControllerBonusesCollection.getAttackOfTank(),
                ControllerBonusesCollection.getTankCharge(),
                ControllerBonusesCollection.getIntensiveProduction(),
                ControllerBonusesCollection.getDoubleTraining(),
                ControllerBonusesCollection.getSuperCranes(),
                ControllerBonusesCollection.getAirStrike(),
                ControllerBonusesCollection.getCloning(),
                ControllerBonusesCollection.getSuperMortarTurret(),
                ControllerBonusesCollection.getFort(),
                ControllerBonusesCollection.getTankBuffalo(),
                ControllerBonusesCollection.getDiversion(),
                ControllerBonusesCollection.getMerge()


        ));
        battleManager.getPlayerRed().setListOfBonuses(Arrays.asList(
                ControllerBonusesCollection.getObstacle(),
                ControllerBonusesCollection.getAmbulance(),
                ControllerBonusesCollection.getCombatReadiness(),
                ControllerBonusesCollection.getHeavyShells(),
                ControllerBonusesCollection.getEnergyBattery(),
                ControllerBonusesCollection.getExplosive(),
                ControllerBonusesCollection.getFightingHeadquarters(),
                ControllerBonusesCollection.getClusterArrow(),
                ControllerBonusesCollection.getCleanup(),
                ControllerBonusesCollection.getBear(),
                ControllerBonusesCollection.getHeavyTankHammer(),
                ControllerBonusesCollection.getRocketCorsair(),
                ControllerBonusesCollection.getAttackOfTank(),
                ControllerBonusesCollection.getTankCharge(),
                ControllerBonusesCollection.getIntensiveProduction(),
                ControllerBonusesCollection.getDoubleTraining(),
                ControllerBonusesCollection.getSuperCranes(),
                ControllerBonusesCollection.getAirStrike(),
                ControllerBonusesCollection.getCloning(),
                ControllerBonusesCollection.getSuperMortarTurret(),
                ControllerBonusesCollection.getFort(),
                ControllerBonusesCollection.getTankBuffalo(),
                ControllerBonusesCollection.getDiversion(),
                ControllerBonusesCollection.getMerge()

        ));


        for (Bonus bonus : battleManager.getPlayerBlue().getListOfBonuses()) {
            bonus.getSprite().setOnMouseClicked(event -> {
                bonus.run(this);
                click = !click;
            });
        }
        for (Bonus bonus : battleManager.getPlayerRed().getListOfBonuses()) {
            bonus.getSprite().setOnMouseClicked(event -> {
                bonus.run(this);
                click = !click;
            });
        }
    }


    @Contract(pure = true)
    public Pane getPaneControlField() {
        return paneControlField;
    }

    @Contract(pure = true)
    public BattleManager getBattleManager() {
        return battleManager;
    }

    @Contract(pure = true)
    public EventHandler<? super MouseEvent> getEventHandler() {
        return eventHandler;
    }

    @Contract(pure = true)
    public Resource getResource() {
        return resource;
    }

    @Contract(pure = true)
    public ResourceOfBonuses getResourceOfBonuses() {
        return resourceOfBonuses;
    }

    @Contract(pure = true)
    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public void setBattleManager(BattleManager battleManager) {
        this.battleManager = battleManager;
    }

    @Contract(pure = true)
    public Button getButtonCreateArmy() {
        return buttonCreateArmy;
    }

    @Contract(pure = true)
    public Button getButtonBuild() {
        return buttonBuild;
    }

    @Contract(pure = true)
    public AnchorPane getPaneGlobal() {
        return paneGlobal;
    }

    @Contract(pure = true)
    public Pane getPaneControlBuild() {
        return paneControlBuild;
    }

    @Contract(pure = true)
    public Pane getPaneControlArmy() {
        return paneControlArmy;
    }

}