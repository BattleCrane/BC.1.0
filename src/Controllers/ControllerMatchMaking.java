package Controllers;

import Adjutants.AdjutantAttacker;
import Adjutants.AdjutantFielder;
import BattleFields.*;
import Bonuses.Bonus;
import Bots.Bot;
import Bots.Steps.Step;
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
    private AdjutantAttacker adjutantAttacker = new AdjutantAttacker();

    //Графические ресурсы:
    private final Resource resource = new Resource();
    private final ResourceOfBonuses resourceOfBonuses = new ResourceOfBonuses();

    //Триггеры:
    private boolean click = false; //Небольшая защелка для кликов мыши
    private boolean isClickButtonOfBonus = false;
    private Unity unit;
    private String labelUnit = ""; //Определитель действия

    private AdjutantFielder adjutantFielder = new AdjutantFielder();
    private ControllerBonusesCollection controllerBonusesCollection = new ControllerBonusesCollection();

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
                            if (battleManager.canConstructBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);

                            }
                        }
                        //Если строите завод:
                        if (labelUnit.equals("factory") && battleManager.getHowCanBuildFactories() > 0 && battleManager.getHowICanBuild() > 0) {
                            if (battleManager.canConstructBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowIcanBuildFactories(battleManager.getHowCanBuildFactories() - 1);
                                battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);
                                if (battleManager.getHowCanBuildFactories() == 0){
                                    buttonBuildFactory.setVisible(false);
                                }
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
                            if (battleManager.canConstructBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);
                                battleManager.setConstructedGenerator(true);
                            }
                        }
                        //Если создаем стену:
                        if (labelUnit.equals("wall") && battleManager.getHowICanBuild() > 0) {
                            if (battleManager.canConstructBuilding(pointClick, battleManager.getBarracks(), battleManager.getPlayer()) &&
                                    battleManager.putDoubleWall(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);

                            }
                        }
                        //Если создаем турель:
                        if (labelUnit.equals("turret") && battleManager.getHowICanBuild() > 0) {
                            if (battleManager.canConstructBuilding(pointClick, unit, battleManager.getPlayer()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);
                                adjutantAttacker.radiusAttack(battleManager, pointClick, 2, 1);

                            }
                        }

                        //Если улучшаем строение:
                        if (labelUnit.equals("upgradeBuilding") && battleManager.getHowICanBuild() > 0) {
                            if (battleManager.upgradeBuilding(pointClick, battleManager.getPlayer())) { //Если удалось улучшить строение:
                                battleManager.setHowICanBuild(battleManager.getHowICanBuild() - 1);
                            }
                        }


                        //Если создаете автоматчика:
                        if (labelUnit.equals("gunner1") && battleManager.getHowICanProductArmyLevel1() > 0) {
                            if (battleManager.getBattleField().getMatrix().get(pointClick.X()).get(pointClick.Y()).contains(battleManager.getPlayer().getColorType()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanProductArmyLevel1(battleManager.getHowICanProductArmyLevel1() - 1);
                            }
                        }

                        if (labelUnit.equals("gunner2") && battleManager.getHowICanProductArmyLevel2() > 0) {
                            if (!battleManager.getBattleField().getMatrix().get(pointClick.X()).get(pointClick.Y()).contains(battleManager.getOpponentPlayer().getColorType()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanProductArmyLevel2(battleManager.getHowICanProductArmyLevel2() - 1);
                            }
                        }

                        if (labelUnit.equals("gunner3") && battleManager.getHowICanProductArmyLevel3() > 0) {
                            if (battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanProductArmyLevel3(battleManager.getHowICanProductArmyLevel3() - 1);
                            }
                        }

                        //Если создаете танк:

                        if (labelUnit.equals("tank1") && battleManager.getHowICanProductTanksLevel1() > 0) {
                            if (battleManager.getBattleField().getMatrix().get(pointClick.X()).get(pointClick.Y()).contains(battleManager.getPlayer().getColorType()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanProductTanksLevel1(battleManager.getHowICanProductTanksLevel1() - 1);
                            }
                        }

                        if (labelUnit.equals("tank2") && battleManager.getHowICanProductTanksLevel2() > 0) {
                            if (!battleManager.getBattleField().getMatrix().get(pointClick.X()).get(pointClick.Y()).contains(battleManager.getOpponentPlayer().getColorType()) &&
                                    battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanProductTanksLevel2(battleManager.getHowICanProductTanksLevel2() - 1);
                            }
                        }

                        if (labelUnit.equals("tank3") && battleManager.getHowICanProductTanksLevel3() > 0) {
                            if (battleManager.putUnity(battleManager.getPlayer(), pointClick, unit)) {
                                battleManager.setHowICanProductTanksLevel3(battleManager.getHowICanProductTanksLevel3() - 1);
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQi]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            System.out.println(adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick));
                                            System.out.println("X" + pointSecondClick.X() + " " + "Y" + pointSecondClick.Y());
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQi]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 2));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQi]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 3));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQi]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
                                                        }
                                                    }
                                                }
                                                battleManager.getAdjutantAttacker().setCountShortsForHeadquarters(battleManager.getAdjutantAttacker().getCountShortsForHeadquarters() - 1);
                                                System.out.println("ATTACK!");
                                                if (battleManager.getAdjutantAttacker().getCountShortsForHeadquarters() == 0) {
                                                    battleManager.getAdjutantWakeUpper().sleepHeadquarters(battleManager);
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
                                            Pattern pattern = Pattern.compile("[hgbfwtiu]");
                                            Matcher matcher = pattern.matcher(targetAttackUnity);
                                            if (matcher.find() && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
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
                                                            adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(listX.get(i)).get(listY.get(i)), damage));
                                                }
                                                System.out.println("ATTACK! " + damage);
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQi]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j),
                                                                            controllerBonusesCollection.getBearDamage(battleManager)));
                                                            System.out.println(controllerBonusesCollection.getBearDamage(battleManager));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
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
                                            Pattern patternBonus = Pattern.compile("[oHeCBEuQi]");
                                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 3));
                                                        }
                                                    }
                                                }
                                                System.out.println("ATTACK!");
                                                battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(),
                                                        battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
                                                battleManager.getBattleField().toString();
                                                System.out.println("ZZZ: " + battleManager.getAdjutantWakeUpper().sleepUnity(clickedUnit));
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
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 2));
                                                        }
                                                    }
                                                }
                                                if (!clickedUnit.substring(1, 2).equals("0")) {
                                                    int shotsOfTankBuffalo = Integer.parseInt(clickedUnit.substring(1, 2)) - 1;
                                                    String sameClickedUnit = clickedUnit;
                                                    sameClickedUnit = sameClickedUnit.substring(0, 1) + shotsOfTankBuffalo + sameClickedUnit.substring(2);
                                                    if (shotsOfTankBuffalo == 0) {
                                                        battleManager.getBattleField().getMatrix().get(pointClick.X()).set(pointClick.Y(), battleManager.getAdjutantWakeUpper().sleepUnity(sameClickedUnit));
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
                                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                                for (int i = 0; i < 16; i++) {
                                                    for (int j = 0; j < 16; j++) {
                                                        String attackerUnitID = battleManager.getIdentificationField().getMatrix().get(i).get(j);
                                                        String targetUnitID = battleManager.getIdentificationField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                                                        if (attackerUnitID.equals(targetUnitID)) {
                                                            battleManager.getBattleField().getMatrix().get(i).set(j,
                                                                    adjutantAttacker.attack(battleManager.getBattleField().getMatrix().get(i).get(j), 1));
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
                                                                battleManager.getBattleField().getMatrix().get(b).set(a, battleManager.getAdjutantWakeUpper().sleepUnity(currentFort));
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
                    adjutantFielder.flush(battleManager);
                    adjutantFielder.fillZones(battleManager);
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
        adjutantFielder.fillZones(battleManager);
        Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
        initializeGameButtons();
        System.out.println(battleManager.getPlayer().getColorType());
        initializeBonuses(battleManager);
        controllerBonusesCollection.showBonuses(this, battleManager.getPlayer(), paneControlSupport);
    }

    private void nextTurn() {
        Timeline timeline = new Timeline();
        controllerBonusesCollection.flush(paneControlSupport, battleManager);
        battleManager.checkDestroyedUnities();
        battleManager.nextTurnOfCurrentPlayer();
        controllerBonusesCollection.showBonuses(this, battleManager.getPlayer(), paneControlSupport);
        labelUnit = "";
        System.out.println(battleManager.getPlayer().getColorType());
        System.out.println("Осталось построек: " + battleManager.getHowICanBuild());
        System.out.println("Осталось автоматчиков 1 Уровня: " + battleManager.getHowICanProductArmyLevel1());
        System.out.println("Осталось автоматчиков 2 Уровня: " + battleManager.getHowICanProductArmyLevel2());
        System.out.println("Осталось автоматчиков 3 Уровня: " + battleManager.getHowICanProductArmyLevel3());
        System.out.println("Осталось танков 1 Уровня : " + battleManager.getHowICanProductTanksLevel1());
        System.out.println("Осталось танков 2 Уровня : " + battleManager.getHowICanProductTanksLevel2());
        System.out.println("Осталось танков 3 Уровня : " + battleManager.getHowICanProductTanksLevel3());
        System.out.println("Осталось энергии: " + battleManager.getPlayer().getEnergy());
        System.out.println(battleManager.getPlayer().getSupplyEnergy() + "/20");


        if (battleManager.getPlayer().getBot() != null){
            buttonBuild.setDisable(true);
            buttonCreateArmy.setDisable(true);
            buttonEndTurn.setDisable(true);
            Bot bot = battleManager.getPlayer().getBot();
            List<Step> listOfStep = bot.loadSteps(battleManager);
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (bot.getCountOfStep() < listOfStep.size()){
                    listOfStep.get(bot.getCountOfStep()).run();
                    bot.setCountOfStep(bot.getCountOfStep() + 1);
                } else {
                    nextTurn();
                }
            }));
            timeline.play();
        } else {
            buttonBuild.setDisable(false);
            buttonCreateArmy.setDisable(false);
            buttonEndTurn.setDisable(false);
            timeline.stop();
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

    private void initializeGameButtons() {
        //Следующий ход:
        buttonEndTurn.setOnMouseClicked(event -> {
            nextTurn();
            Painter.drawGraphic(battleManager, resource, paneControlField, resourceOfBonuses);
            if (battleManager.getHowICanProductArmyLevel1() - battleManager.getHowICanProductTanksLevel1() > 0 ||
                    battleManager.getHowICanProductArmyLevel2() - battleManager.getHowICanProductTanksLevel2() > 0 ||
                    battleManager.getHowICanProductArmyLevel3() - battleManager.getHowICanProductTanksLevel3() > 0) {
                buttonBuildFactory.setVisible(true);
            } else {
                buttonBuildFactory.setVisible(false);
            }
            if (battleManager.getHowICanProductArmyLevel1() > 0 || battleManager.getHowICanProductTanksLevel1() > 0 ||
                    battleManager.getHowICanProductArmyLevel2() > 0 || battleManager.getHowICanProductTanksLevel2() > 0 ||
                    battleManager.getHowICanProductArmyLevel3() > 0 || battleManager.getHowICanProductTanksLevel3() > 0) {
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
            unit = battleManager.getBarracks();
            labelUnit = "building";

        });

        //Постройка завода:
        buttonBuildFactory.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getFactory();
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
            labelUnit = "gunner1";
        });

        buttonProductGunner2.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getGunner();
            labelUnit = "gunner2";
        });

        buttonProductGunner3.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getGunner();
            labelUnit = "gunner3";
        });


        //Создание танка:
        buttonProductTank1.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getTank();
            labelUnit = "tank1";
        });

        buttonProductTank2.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getTank();
            labelUnit = "tank2";
        });

        buttonProductTank3.setOnMouseClicked(event -> {
            click = !click;
            unit = battleManager.getTank();
            labelUnit = "tank3";
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
                controllerBonusesCollection.getObstacle(),
                controllerBonusesCollection.getAmbulance(),
                controllerBonusesCollection.getCombatReadiness(),
                controllerBonusesCollection.getHeavyShells(),
                controllerBonusesCollection.getEnergyBattery(),
                controllerBonusesCollection.getExplosive(),
                controllerBonusesCollection.getFightingHeadquarters(),
                controllerBonusesCollection.getClusterArrow(),
                controllerBonusesCollection.getCleanup(),
                controllerBonusesCollection.getBear(),
                controllerBonusesCollection.getHeavyTankHammer(),
                controllerBonusesCollection.getRocketCorsair(),
                controllerBonusesCollection.getAttackOfTank(),
                controllerBonusesCollection.getTankCharge(),
                controllerBonusesCollection.getIntensiveProduction(),
                controllerBonusesCollection.getDoubleTraining(),
                controllerBonusesCollection.getSuperCranes(),
                controllerBonusesCollection.getAirStrike(),
                controllerBonusesCollection.getCloning(),
                controllerBonusesCollection.getSuperMortarTurret(),
                controllerBonusesCollection.getFort(),
                controllerBonusesCollection.getTankBuffalo(),
                controllerBonusesCollection.getDiversion(),
                controllerBonusesCollection.getMerge(),
                controllerBonusesCollection.getMobilization()
        ));
        battleManager.getPlayerRed().setListOfBonuses(Arrays.asList(
                controllerBonusesCollection.getObstacle(),
                controllerBonusesCollection.getAmbulance(),
                controllerBonusesCollection.getCombatReadiness(),
                controllerBonusesCollection.getHeavyShells(),
                controllerBonusesCollection.getEnergyBattery(),
                controllerBonusesCollection.getExplosive(),
                controllerBonusesCollection.getFightingHeadquarters(),
                controllerBonusesCollection.getClusterArrow(),
                controllerBonusesCollection.getCleanup(),
                controllerBonusesCollection.getBear(),
                controllerBonusesCollection.getHeavyTankHammer(),
                controllerBonusesCollection.getRocketCorsair(),
                controllerBonusesCollection.getAttackOfTank(),
                controllerBonusesCollection.getTankCharge(),
                controllerBonusesCollection.getIntensiveProduction(),
                controllerBonusesCollection.getDoubleTraining(),
                controllerBonusesCollection.getSuperCranes(),
                controllerBonusesCollection.getAirStrike(),
                controllerBonusesCollection.getCloning(),
                controllerBonusesCollection.getSuperMortarTurret(),
                controllerBonusesCollection.getFort(),
                controllerBonusesCollection.getTankBuffalo(),
                controllerBonusesCollection.getDiversion(),
                controllerBonusesCollection.getMerge(),
                controllerBonusesCollection.getMobilization()
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

    @Contract(pure = true)
    public Button getButtonCreateArmy() {
        return buttonCreateArmy;
    }

    @Contract(pure = true)
    public Button getButtonBuild() {
        return buttonBuild;
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