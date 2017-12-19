package polytech.polyCombinations;

public class CodeContainer {
}
//    public List<PriorityUnit> probeAccommodationOfUnits(BattleManager battleManager) {
//        Pair listOfBestPriorityBallistic = new Pair(new ArrayList<>(), 0);
//        Pair listOfBestPriorityBuilding = new Pair(new ArrayList<>(), 0);
//        List<List<String>> matrix = battleManager.getBattleField().getMatrix();
//
//        int howICanProductArmyLevel1 = battleManager.getHowICanProductArmyLevel1();
//        int howICanProductTanksLevel1 = battleManager.getHowICanProductTanksLevel1();
//        int howICanProductArmyLevel2 = battleManager.getHowICanProductArmyLevel2();
//        int howICanProductTanksLevel2 = battleManager.getHowICanProductTanksLevel2();
//        int howICanProductArmyLevel3 = battleManager.getHowICanProductArmyLevel3();
//        int howICanProductTanksLevel3 = battleManager.getHowICanProductTanksLevel3();
//
//        int howICanBuild = battleManager.getHowICanBuild();
//        int howCanBuildFactories = battleManager.getHowCanBuildFactories();
//        boolean isConstructedGenerator = battleManager.isConstructedGenerator();
//
//        int sum = howICanProductArmyLevel1 + howICanProductTanksLevel1 + howICanProductArmyLevel2 +
//                howICanProductTanksLevel2 + howICanProductArmyLevel3 + howICanProductTanksLevel3 + howICanBuild;
//        System.out.println(sum);
//
//        Unity barracks = battleManager.getBarracks();
//        Unity factory = battleManager.getFactory();
//        Unity generator = battleManager.getGenerator();
//        Unity wall = battleManager.getWall();
//        Unity turret = battleManager.getTurret();
//
//        PriorityUnit mostPriorityBallistic = new PolyPriorityUnit(-10000.0, null, null);
//        PriorityUnit mostPriorityBuilding = new PolyPriorityUnit(-10000.0, null, null);
//
//        List<Point> listOfProbeBuildings = new ArrayList<>();
//        List<Point> listOfProbeBallistic = new ArrayList<>();
//
////        howICanProductArmyLevel1 + howICanProductTanksLevel1 + howICanProductArmyLevel2 +
//////                howICanProductTanksLevel2 + howICanProductArmyLevel3 + howICanProductTanksLevel3 +
//
//        for (int k = 0; k < sum; k++) {
//            String labelStepBuilding = "";
//            Point currentPoint;
//            mostPriorityBuilding = new PolyPriorityUnit(-10000.0, null, null);
//            for (int i = 0; i < 16; i++) {
//                for (int j = 0; j < 16; j++) {
//                    currentPoint = new Point(i, j);
//                    String currentUnity = matrix.get(j).get(i);
//                    if (currentUnity.substring(1).equals("    0")) { //Если это пустая клетка ->
//                        String labelStepBallistic = "";
//                        if (currentUnity.substring(0, 1).equals(battleManager.getPlayer().getColorType())) {
//                            //Если это наша пустая клетка ->
//
////                            //Army:
////                            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////
////                            if (howICanProductTanksLevel1 > 0) { //Проверяем танки 1-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getTank(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    mostPriorityBallistic = priorityUnit;
////                                    labelStepBallistic = "tankLvl1";
////                                }
////                            }
////                            if (howICanProductArmyLevel1 > 0) { //Проверяем автоматчиков 1-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getGunner(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    mostPriorityBallistic = priorityUnit;
////                                    labelStepBallistic = "gunnerLvl1";
////                                }
////                            }
////                            if (howICanProductTanksLevel2 > 0) { //Проверяем танки 2-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getTank(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "tankLvl2";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                            if (howICanProductArmyLevel2 > 0) { //Проверяем автоматчиков 2-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getGunner(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "gunnerLvl2";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                            if (howICanProductTanksLevel3 > 0) { //Проверяем танки 3-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getTank(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "tankLvl13";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                            if (howICanProductArmyLevel3 > 0) {  //Проверяем автоматчиков 3-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getGunner(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "gunnerLvl3";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
//                        }
////                        if (currentUnity.substring(0, 1).equals(" ")) {//Если это нейтральная клетка ->
////                            if (howICanProductTanksLevel2 > 0) { //Проверяем танки 2-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getTank(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "tankLvl2";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                            if (howICanProductArmyLevel2 > 0) { //Проверяем автоматчиков 2-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getGunner(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "gunnerLvl2";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                            if (howICanProductTanksLevel3 > 0) { //Проверяем танки 3-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getTank(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "tankLvl3";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                            if (howICanProductArmyLevel3 > 0) { //Проверяем автоматчиков 3-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getGunner(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "gunnerLvl3";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                        }
////                        if (currentUnity.substring(0, 1).equals(battleManager.getOpponentPlayer().getColorType())) {//Если это клетка оппонента ->
////                            if (howICanProductTanksLevel3 > 0) { //Проверяем танки 3-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getTank(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "tankLvl3";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                            if (howICanProductArmyLevel3 > 0) { //Проверяем автоматчиков 3-го уровня:
////                                PriorityUnit priorityUnit = probeBallisticUnit(battleManager, battleManager.getGunner(), currentPoint);
////                                if (priorityUnit.getPriority() > mostPriorityBallistic.getPriority()) {
////                                    labelStepBallistic = "gunnerLvl3";
////                                    mostPriorityBallistic = priorityUnit;
////                                }
////                            }
////                        }
////                        switch (labelStepBallistic) {
////                            case "tankLvl1":
////                                howICanProductTanksLevel1--;
////                                break;
////                            case "tankLvl2":
////                                howICanProductTanksLevel2--;
////                                break;
////                            case "tankLvl3":
////                                howICanProductTanksLevel3--;
////                                break;
////                            case "gunnerLvl1":
////                                howICanProductArmyLevel1--;
////                                break;
////                            case "gunnerLvl2":
////                                howICanProductArmyLevel2--;
////                                break;
////                            case "gunnerLvl3":
////                                howICanProductArmyLevel3--;
////                                break;
////                        }
////                        if (mostPriorityBallistic.getPriority() > -100000 &&
////                                !listOfBestPriorityBallistic.priorityUnitList.contains(mostPriorityBallistic)) {
////                            listOfBestPriorityBallistic.add(mostPriorityBallistic);
////                        }
//
//                        //////////////////////////////////////////////////////////////////////////////////////////////
//                        if (howICanBuild > 0) {//Если можно строить строения:
//
////                            //Проверяем генератор:
////                            if (howICanBuild <= 2 ){
////
////                                if (!isConstructedGenerator && battleManager.canConstructBuilding(currentPoint, generator, battleManager.getPlayer()) &&
////                                        battleManager.isEmptyTerritory(currentPoint, generator)) {
////                                    PriorityUnit priorityUnit = probeBuilding(battleManager, generator, currentPoint);
////                                    if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityUnit) && priorityUnit.getPriority() >= mostPriorityBuilding.getPriority()) {
////                                        labelStepBuilding = "generator";
////                                        mostPriorityBuilding = priorityUnit;
////                                    }
////                                }
////                            }
//
//
//                            //Проверяем бараки:
//                            if (battleManager.canConstructBuilding(currentPoint, barracks, battleManager.getPlayer()) &&
//                                    battleManager.isEmptyTerritory(currentPoint, barracks)) {
//                                PriorityUnit priorityUnit = probeBuilding(battleManager, barracks, currentPoint);
//                                if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityUnit) && priorityUnit.getPriority() > mostPriorityBuilding.getPriority()) {
//                                    labelStepBuilding = "barracks";
//                                    mostPriorityBuilding = priorityUnit;
//                                }
//                            }
//
////                            //Проверяем завод:
////                            if (howCanBuildFactories > 0 && battleManager.canConstructBuilding(currentPoint, factory, battleManager.getPlayer()) &&
////                                    battleManager.isEmptyTerritory(currentPoint, factory)) {
////                                PriorityUnit priorityUnit = probeBuilding(battleManager, factory, currentPoint);
////                                if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityUnit) && priorityUnit.getPriority() > mostPriorityBuilding.getPriority()) {
////                                    labelStepBuilding = "factory";
////                                    mostPriorityBuilding = priorityUnit;
////                                }
////                            }
////
////
////                            //Проверяем стену:
////                            if (battleManager.canConstructBuilding(currentPoint, barracks, battleManager.getPlayer()) &&
////                                    battleManager.isEmptyTerritory(currentPoint, barracks)) {
////                                PriorityUnit priorityUnit = probeBuilding(battleManager, wall, currentPoint);
////                                if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityUnit) && priorityUnit.getPriority() > mostPriorityBuilding.getPriority()) {
////                                    labelStepBuilding = "wall";
////                                    mostPriorityBuilding = priorityUnit;
////                                }
////                            }
////
////                            //Проверяем турель:
////                            if (battleManager.canConstructBuilding(currentPoint, turret, battleManager.getPlayer()) &&
////                                    battleManager.isEmptyTerritory(currentPoint, turret)) {
////                                PriorityUnit priorityUnit = probeRadiusUnit(battleManager, turret, currentPoint);
////                                if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityUnit) && priorityUnit.getPriority() > mostPriorityBuilding.getPriority()) {
////                                    labelStepBuilding = "turret";
////                                    mostPriorityBuilding = priorityUnit;
////                                }
////                            }
//                        }
//
//                    } else {
//                        if (howICanBuild > 0) {
////
////                            PriorityUnit priorityBarracks = probeUpgrade(battleManager, barracks, currentPoint);
////                            if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityBarracks) && priorityBarracks.getPriority() > mostPriorityBuilding.getPriority()) {
////                                labelStepBuilding = "upgrade";
////                                mostPriorityBuilding = priorityBarracks;
////                            }
////
////                            PriorityUnit priorityFactory = probeUpgrade(battleManager, factory, currentPoint);
////                            if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityFactory) && priorityFactory.getPriority() > mostPriorityBuilding.getPriority()) {
////                                labelStepBuilding = "upgrade";
////                                mostPriorityBuilding = priorityFactory;
////                            }
////
////                            PriorityUnit priorityGenerator = probeUpgrade(battleManager, generator, currentPoint);
////                            if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityGenerator) && priorityGenerator.getPriority() > mostPriorityBuilding.getPriority()) {
////                                labelStepBuilding = "upgrade";
////                                mostPriorityBuilding = priorityGenerator;
////                            }
////
////                            PriorityUnit priorityTurret = probeUpgrade(battleManager, turret, currentPoint);
////                            if (!listOfBestPriorityBuilding.priorityUnitList.contains(priorityTurret) && priorityTurret.getPriority() > mostPriorityBuilding.getPriority()) {
////                                labelStepBuilding = "upgrade";
////                                mostPriorityBuilding = priorityTurret;
////                            }
//                        }
//                    }
//                }
//            }
//            switch (labelStepBuilding) {
//                case "barracks":
//                    howICanBuild = howICanBuild - 1;
//                    break;
//                case "factory":
//                    howICanBuild = howICanBuild - 1;
//                    howCanBuildFactories = howCanBuildFactories - 1;
//                    break;
//                case "generator":
//                    System.out.println(!isConstructedGenerator + "      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//                    howICanBuild = howICanBuild - 1;
//                    isConstructedGenerator = true;
//                    System.out.println(!isConstructedGenerator + "      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//                    break;
//                case "wall":
//                    howICanBuild = howICanBuild - 1;
//                    break;
//                case "turret":
//                    howICanBuild = howICanBuild - 1;
//                    break;
//                case "upgrade":
//                    howICanBuild = howICanBuild - 1;
//                    break;
//            }
//
////            &&
////            !containsTerritory(listOfProbeBuildings, currentPoint, mostPriorityBuilding.getUnity())
//
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11111     " + mostPriorityBuilding.getPriority());
//            if (mostPriorityBuilding.getPriority() > -10000.0) {
//                listOfBestPriorityBuilding.add(mostPriorityBuilding);
//                System.out.println(listOfBestPriorityBuilding.priorityUnitList.toString());
////                markTerritory(listOfProbeBuildings, mostPriorityBuilding.getPoint(), mostPriorityBuilding.getUnity());
//            }
//        }
//
//        System.out.println(listOfBestPriorityBuilding.priorityUnitList.toString());
//        show(battleManager, listOfBestPriorityBuilding.priorityUnitList);
//        battleManager.getBattleField().toString();
//        return listOfBestPriorityBuilding.priorityUnitList;
////        if (listOfBestPriorityBallistic.sum > listOfBestPriorityBuilding.sum) {
////            return listOfBestPriorityBallistic.priorityUnitList;
////        } else {
////            return listOfBestPriorityBuilding.priorityUnitList;
////        }
//    }