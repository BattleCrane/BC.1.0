package polytech.Steps;

import Adjutants.AdjutantAttacker;
import BattleFields.BattleManager;
import BattleFields.Point;
import Bonuses.ControllerBonusesCollection;
import Controllers.ControllerMatchMaking;
import Graphics.Painter;
import polytech.Priority.Priorities;
import polytech.polyNexus.Probes.PolyTargetProbe;
import ResourceInit.Resource;
import ResourceInit.ResourceOfBonuses;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttackStep implements Step {
    private final ControllerMatchMaking controllerMatchMaking;
    private final Priorities map;
    private final Point startPoint;

    public AttackStep(ControllerMatchMaking controllerMatchMaking, Priorities map, Point startPoint) {
        this.controllerMatchMaking = controllerMatchMaking;
        this.map = map;
        this.startPoint = startPoint;
    }

    @Override
    public void makeStep() {
        Point shotPoint = new PolyTargetProbe(controllerMatchMaking, map).findBestShot(startPoint);
        if (shotPoint != null) {
            AdjutantAttacker adjutantAttacker = new AdjutantAttacker();
            Resource resource = controllerMatchMaking.getResource();
            ResourceOfBonuses resourceOfBonuses = controllerMatchMaking.getResourceOfBonuses();
            BattleManager battleManager = controllerMatchMaking.getBattleManager();
            Pane paneControlField = controllerMatchMaking.getPaneControlField();
            System.out.println("Выбрали юнита");
            Point pointClick = startPoint;
            String clickedUnit = battleManager.getBattleField().getMatrix().get(pointClick.X()).get(pointClick.Y());
            String clickedUnitID = battleManager.getIdentificationField().getMatrix().get(pointClick.Y()).get(pointClick.X());
            System.out.println("Юнит: " + clickedUnit);
            if (clickedUnit.contains(battleManager.getPlayer().getColorType()) && clickedUnit.contains("!")) {
                Point pointSecondClick;
                String targetAttackUnity;
                switch (clickedUnit.substring(4, 5)) {
                    case "G":
                        System.out.println("Это автоматчик: " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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

                            }
                        }
                        break;
                    case "T":
                        System.out.println("Это танк: " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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
                            }
                        }
                        break;
                    case "H":
                        System.out.println("Это тяжелый автоматчик: " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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

                            }
                        }
                        break;
                    case "h":
                        System.out.println("Это штаб: " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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
                            }
                        }
                        break;
                    case "C":
                        System.out.println("Это кластерный автоматчик: " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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
                            }
                        }
                        break;
                    case "B":
                        System.out.println("Это медведь: " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
                        if (!targetAttackUnity.contains(battleManager.getPlayer().getColorType())) {
                            Pattern pattern = Pattern.compile("[hgbfwtGT]");
                            Matcher matcher = pattern.matcher(targetAttackUnity);
                            Pattern patternBonus = Pattern.compile("[oHeCBEuQi]");
                            Matcher matcherBonus = patternBonus.matcher(targetAttackUnity);
                            if ((matcher.find() || matcherBonus.find()) && adjutantAttacker.checkTarget(battleManager, pointClick, pointSecondClick)) {
                                ControllerBonusesCollection controllerBonusesCollection = new ControllerBonusesCollection();
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

                            }
                        }
                        break;
                    case "E":
                        System.out.println("Это Тяжелый танк 'Молот': " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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

                            }
                        }
                        break;
                    case "Q":
                        System.out.println("Это танк 'Буффало': " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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
                            }
                        }
                        break;
                    case "i":
                        System.out.println("Это форт: " + clickedUnit);
                        pointSecondClick = shotPoint;
                        System.out.println("Второй клик: ");
                        targetAttackUnity = battleManager.getBattleField().getMatrix().get(pointSecondClick.X()).get(pointSecondClick.Y());
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
                            }
                        }
                        break;
                }
            }
        }

    }
}

