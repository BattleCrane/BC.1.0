package Graphics;

import BattleFields.BattleManager;
import ResourceInit.Resource;
import ResourceInit.ResourceOfBonuses;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public final class Painter {
    public static void drawGraphic(BattleManager battleManager, Resource resource, Pane paneControlField,
                                   ResourceOfBonuses resourceOfBonuses){
        paneControlField.getChildren().removeAll(paneControlField.getChildren());
        try {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    boolean isReadyUnit = false;
                    boolean isReadyBonusUnit = false;
                    String element = battleManager.getBattleField().getMatrix().get(i).get(j);
                    ImageView currentUnity = new ImageView();
                    switch (element){
                        //Готовый автоматчик:
                        case "1^!+G'":
                            currentUnity = resource.getGunnerReadyBlue();
                            isReadyUnit = true;
                            break;
                        case "1^!-G'":
                            currentUnity = resource.getGunnerReadyRed();
                            isReadyUnit = true;
                            break;
                        //Готовый танк:
                        case "2^!+T'":
                            currentUnity = resource.getTankReadyBlue();
                            isReadyUnit = true;
                            break;
                        case "1^!+T'":
                            currentUnity = resource.getTankReadyBlue1HP();
                            isReadyUnit = true;
                            break;
                        case "2^!-T'":
                            currentUnity = resource.getTankReadyRed();
                            isReadyUnit = true;
                            break;
                        case "1^!-T'":
                            currentUnity = resource.getTankReadyRed1HP();
                            isReadyUnit = true;
                            break;
                    }
                    if (!isReadyUnit){
                        switch (element.substring(0, 2) + element.substring(3)) {
                            //Штаб:
                            case "8^+h'":
                                currentUnity = resource.getHeadquartersBlue();
                                break;
                            case "1^+h'":
                                currentUnity = resource.getHeadquartersBlue1HP();
                                break;
                            case "2^+h'":
                                currentUnity = resource.getHeadquartersBlue2HP();
                                break;
                            case "3^+h'":
                                currentUnity = resource.getHeadquartersBlue3HP();
                                break;
                            case "4^+h'":
                                currentUnity = resource.getHeadquartersBlue4HP();
                                break;
                            case "5^+h'":
                                currentUnity = resource.getHeadquartersBlue5HP();
                                break;
                            case "6^+h'":
                                currentUnity = resource.getHeadquartersBlue6HP();
                                break;
                            case "7^+h'":
                                currentUnity = resource.getHeadquartersBlue7HP();
                                break;
                            case "8^-h'":
                                currentUnity = resource.getHeadquartersRed();
                                break;
                            case "1^-h'":
                                currentUnity = resource.getHeadquartersRed1HP();
                                break;
                            case "2^-h'":
                                currentUnity = resource.getHeadquartersRed2HP();
                                break;
                            case "3^-h'":
                                currentUnity = resource.getHeadquartersRed3HP();
                                break;
                            case "4^-h'":
                                currentUnity = resource.getHeadquartersRed4HP();
                                break;
                            case "5^-h'":
                                currentUnity = resource.getHeadquartersRed5HP();
                                break;
                            case "6^-h'":
                                currentUnity = resource.getHeadquartersRed6HP();
                                break;
                            case "7^-h'":
                                currentUnity = resource.getHeadquartersRed7HP();
                                break;
                            //Генератор:
                            //--Уровень 1:
                            case "1^+g'":
                                currentUnity = resource.getGeneratorLevel1Blue();
                                break;
                            case "1^-g'":
                                currentUnity = resource.getGeneratorLevel1Red();
                                break;
                            //--Уровень 2:
                            case "2<+g'":
                                currentUnity = resource.getGeneratorLevel2Blue();
                                break;
                            case "1<+g'":
                                currentUnity = resource.getGeneratorLevel2Blue1HP();
                                break;
                            case "2<-g'":
                                currentUnity= resource.getGeneratorLevel2Red();
                                break;
                            case "1<-g'":
                                currentUnity= resource.getGeneratorLevel2Red1HP();
                                break;
                            //--Уровень 3:
                            case "4>+g'":
                                currentUnity = resource.getGeneratorLevel3Blue();
                                break;
                            case "3>+g'":
                                currentUnity = resource.getGeneratorLevel3Blue3HP();
                                break;
                            case "2>+g'":
                                currentUnity = resource.getGeneratorLevel3Blue2HP();
                                break;
                            case "1>+g'":
                                currentUnity = resource.getGeneratorLevel3Blue1HP();
                                break;
                            case "4>-g'":
                                currentUnity = resource.getGeneratorLevel3Red();
                                break;
                            case "1>-g'":
                                currentUnity = resource.getGeneratorLevel3Red1HP();
                                break;
                            case "2>-g'":
                                currentUnity = resource.getGeneratorLevel3Red2HP();
                                break;
                            case "3>-g'":
                                currentUnity = resource.getGeneratorLevel3Red3HP();
                                break;
                            //Бараки:
                            //--Уровень 1:
                            case "1^+b'":
                                currentUnity = resource.getBarracksLevel1Blue();
                                break;
                            case "1^-b'":
                                currentUnity = resource.getBarracksLevel1Red();
                                break;
                            //--Уровень 2:
                            case "2<+b'":
                                currentUnity = resource.getBarracksLevel2Blue();
                                break;
                            case "2<-b'":
                                currentUnity = resource.getBarracksLevel2Red();
                                break;
                            case "1<+b'":
                                currentUnity = resource.getBarracksLevel2Blue1HP();
                                break;
                            case "1<-b'":
                                currentUnity = resource.getBarracksLevel2Red1HP();
                                break;
                            //--Уровень 3:
                            case "4>+b'":
                                currentUnity = resource.getBarracksLevel3Blue();
                                break;
                            case "4>-b'":
                                currentUnity = resource.getBarracksLevel3Red();
                                break;
                            case "3>-b'":
                                currentUnity = resource.getBarracksLevel3Red3HP();
                                break;
                            case "3>+b'":
                                currentUnity = resource.getBarracksLevel3Blue3HP();
                                break;
                            case "2>+b'":
                                currentUnity = resource.getBarracksLevel3Blue2HP();
                                break;
                            case "2>-b'":
                                currentUnity = resource.getBarracksLevel3Red2HP();
                                break;
                            case "1>+b'":
                                currentUnity = resource.getBarracksLevel3Blue1HP();
                                break;
                            case "1>-b'":
                                currentUnity = resource.getBarracksLevel3Red1HP();
                                break;
                            //Завод:
                            //--Уровень 1:
                            case "1^+f'":
                                currentUnity = resource.getFactoryLevel1Blue();
                                break;
                            case "1^-f'":
                                currentUnity = resource.getFactoryLevelRed();
                                break;
                            //--Уровень 2:
                            case "4<+f'":
                                currentUnity = resource.getFactoryLevel2Blue();
                                break;
                            case "4<-f'":
                                currentUnity = resource.getFactoryLevel2Red();
                                break;
                            case "1<+f'":
                                currentUnity = resource.getFactoryLevel2Blue1HP();
                                break;
                            case "2<+f'":
                                currentUnity = resource.getFactoryLevel2Blue2HP();
                                break;
                            case "3<+f'":
                                currentUnity = resource.getFactoryLevel2Blue3HP();
                                break;
                            case "1<-f'":
                                currentUnity = resource.getFactoryLevel2Red1HP();
                                break;
                            case "2<-f'":
                                currentUnity = resource.getFactoryLevel2Red2HP();
                                break;
                            case "3<-f'":
                                currentUnity = resource.getFactoryLevel2Red3HP();
                                break;
                            //--Уровень 3:
                            case "6>+f'":
                                currentUnity = resource.getFactoryLevel3Blue();
                                break;
                            case "5>+f'":
                                currentUnity = resource.getFactoryLevel3Blue5HP();
                                break;
                            case "4>+f'":
                                currentUnity = resource.getFactoryLevel3Blue4HP();
                                break;
                            case "3>+f'":
                                currentUnity = resource.getFactoryLevel3Blue3HP();
                                break;
                            case "2>+f'":
                                currentUnity = resource.getFactoryLevel3Blue2HP();
                                break;
                            case "1>+f'":
                                currentUnity = resource.getFactoryLevel3Blue1HP();
                                break;
                            case "6>-f'":
                                currentUnity = resource.getFactoryLevel3Red();
                                break;
                            case "1>-f'":
                                currentUnity = resource.getFactoryLevel3Red1HP();
                                break;
                            case "2>-f'":
                                currentUnity = resource.getFactoryLevel3Red2HP();
                                break;
                            case "3>-f'":
                                currentUnity = resource.getFactoryLevel3Red3HP();
                                break;
                            case "4>-f'":
                                currentUnity = resource.getFactoryLevel3Red4HP();
                                break;
                            case "5>-f'":
                                currentUnity = resource.getFactoryLevel3Red5HP();
                                break;
                            //Турель:
                            //--Уровень 1:
                            case "2^+t'":
                                currentUnity = resource.getTurretLevel1Blue();
                                break;
                            case "1^+t'":
                                currentUnity = resource.getTurretLevel1Blue1HP();
                                break;
                            case "2^-t'":
                                currentUnity = resource.getTurretLevel1Red();
                                break;
                            case "1^-t'":
                                currentUnity = resource.getTurretLevel1Red1HP();
                                break;
                            //--Уровень 2:
                            case "4<+t'":
                                currentUnity = resource.getTurretLevel2Blue();
                                break;
                            case "1<+t'":
                                currentUnity = resource.getTurretLevel2Blue1HP();
                                break;
                            case "2<+t'":
                                currentUnity = resource.getTurretLevel2Blue2HP();
                                break;
                            case "3<+t'":
                                currentUnity = resource.getTurretLevel2Blue3HP();
                                break;
                            case "4<-t'":
                                currentUnity = resource.getTurretLevel2Red();
                                break;
                            case "1<-t'":
                                currentUnity = resource.getTurretLevel2Red1HP();
                                break;
                            case "2<-t'":
                                currentUnity = resource.getTurretLevel2Red2HP();
                                break;
                            case "3<-t'":
                                currentUnity = resource.getTurretLevel2Red3HP();
                                break;
                            //Стена:
                            case "4^-w'":
                                currentUnity = resource.getWallRed();
                                break;
                            case "3^-w'":
                                currentUnity = resource.getWallRed3HP();
                                break;
                            case "2^-w'":
                                currentUnity = resource.getWallRed2HP();
                                break;
                            case "1^-w'":
                                currentUnity = resource.getWallRed1HP();
                                break;
                            case "4^+w'":
                                currentUnity = resource.getWallBlue();
                                break;
                            case "1^+w'":
                                currentUnity = resource.getWallBlue1HP();
                                break;
                            case "2^+w'":
                                currentUnity = resource.getWallBlue2HP();
                                break;
                            case "3^+w'":
                                currentUnity = resource.getWallBlue3HP();
                                break;
                            //Автоматчик:
                            case "1^+G'":
                                currentUnity = resource.getGunnerBlue();
                                break;
                            case "1^-G'":
                                currentUnity = resource.getGunnerRed();
                                break;
                            //Танк:
                            case "2^+T'":
                                currentUnity = resource.getTankBlue();
                                break;
                            case "1^+T'":
                                currentUnity = resource.getTankBlue1HP();
                                break;
                            case "2^-T'":
                                currentUnity = resource.getTankRed();
                                break;
                            case "1^-T'":
                                currentUnity = resource.getTankRed1HP();
                                break;
                            //Пустое поле:
                            case "    0":
                                currentUnity = resource.getEmptyField();
                                break;
                            case "XXXXX":
                                currentUnity = resource.getDestroyedField();
                                break;
                        }
                    }

                    //Бонусы:
                    switch (element){
                        case "2A!+G'":
                            currentUnity = resourceOfBonuses.getArmoredGunnerBlueReady();
                            isReadyBonusUnit = true;
                            break;
                        case "1A!+G'":
                            currentUnity = resourceOfBonuses.getArmoredGunnerBlueReady1HP();
                            isReadyBonusUnit = true;
                            break;
                        case "2A!-G'":
                            currentUnity = resourceOfBonuses.getArmoredGunnerRedReady();
                            isReadyBonusUnit = true;
                            break;
                        case "1A!-G'":
                            currentUnity = resourceOfBonuses.getArmoredGunnerRedReady1HP();
                            isReadyBonusUnit = true;
                            break;
                        case "1^!+H'":
                            currentUnity = resourceOfBonuses.getHeavyGunnerBlueReady();
                            isReadyBonusUnit = true;
                            break;
                        case "1^!-H'":
                            currentUnity = resourceOfBonuses.getHeavyGunnerRedReady();
                            isReadyBonusUnit = true;
                            break;
                        //Взрывчатка:
                        case "4^!+w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallBlue();
                            break;
                        case "3^!+w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallBlue3HP();
                            break;
                        case "2^!+w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallBlue2HP();
                            break;
                        case "1^!+w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallBlue1HP();
                            break;
                        case "4^!-w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallRed();
                            break;
                        case "3^!-w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallRed3HP();
                            break;
                        case "2^!-w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallRed2HP();
                            break;
                        case "1^!-w'":
                            currentUnity = resourceOfBonuses.getExplosiveWallRed1HP();
                            break;
                        //Боевой штаб:
                        case "8^!+h'":
                        case "8^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters();
                            break;
                        case "7^!+h'":
                        case "7^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters7HP();
                            break;
                        case "6^!+h'":
                        case "6^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters6HP();
                            break;
                        case "5^!+h'":
                        case "5^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters5HP();
                            break;
                        case "4^!+h'":
                        case "4^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters4HP();
                            break;
                        case "3^!+h'":
                        case "3^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters3HP();
                            break;
                        case "2^!+h'":
                        case "2^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters2HP();
                            break;
                        case "1^!+h'":
                        case "1^!-h'":
                            currentUnity = resourceOfBonuses.getFightingHeadquarters1HP();
                            break;
                        //Кластерный автоматчик:
                        case "1^!+C'":
                            currentUnity = resourceOfBonuses.getClusterArcherBlueReady();
                            isReadyBonusUnit = true;
                            break;
                        case "1^!-C'":
                            currentUnity = resourceOfBonuses.getClusterArcherRedReady();
                            isReadyBonusUnit = true;
                            break;
                    }
                    if (!isReadyBonusUnit){
                        switch (element.substring(0, 2) + element.substring(3)) {
                            //Заграждение:
                            case "1^+o'":
                                currentUnity = resourceOfBonuses.getObstacleBlue();
                                break;
                            case "1^-o'":
                                currentUnity = resourceOfBonuses.getObstacleRed();
                                break;
                            //Скорая помощь:
                            case "2A+G'":
                                currentUnity = resourceOfBonuses.getArmoredGunnerBlue();
                                break;
                            case "1A+G'":
                                currentUnity = resourceOfBonuses.getArmoredGunnerBlue1HP();
                                break;
                            case "2A-G'":
                                currentUnity = resourceOfBonuses.getArmoredGunnerRed();
                                break;
                            case "1A-G'":
                                currentUnity = resourceOfBonuses.getArmoredGunnerRed1HP();
                                break;
                            case "0^+G'":
                                currentUnity = resource.getGunnerReadyBlue();
                                break;
                            case "0^-G'":
                                currentUnity = resource.getGunnerReadyRed();
                                break;
                            case "0A+G'":
                                currentUnity = resourceOfBonuses.getArmoredGunnerBlueReady1HP();
                                break;
                            case "0A-G'":
                                currentUnity = resourceOfBonuses.getArmoredGunnerRedReady1HP();
                                break;
                            //Тяжелые снаряды:
                            case "1^+H'":
                                currentUnity = resourceOfBonuses.getHeavyGunnerBlue();
                                break;
                            case "1^-H'":
                                currentUnity = resourceOfBonuses.getHeavyGunnerRed();
                                break;
                            case "0^+H'":
                                currentUnity = resourceOfBonuses.getHeavyGunnerBlueReady();
                                break;
                            case "0^-H'":
                                currentUnity = resourceOfBonuses.getHeavyGunnerRedReady();
                                break;
                            //Енергетическая батарея:
                            case "1^+e'":
                                currentUnity = resourceOfBonuses.getEnergyBatteryBlue();
                                break;
                            case "1^-e'":
                                currentUnity = resourceOfBonuses.getEnergyBatteryRed();
                                break;
                            //Кластерный автоматчик:
                            case "1^+C'":
                                currentUnity = resourceOfBonuses.getClusterArcherBlue();
                                System.out.println("ddd");
                                break;
                            case "1^-C'":
                                currentUnity = resourceOfBonuses.getClusterArcherRed();
                                break;
                            case "0^+C'":
                                currentUnity = resourceOfBonuses.getClusterArcherBlueReady();
                                break;
                            case "0^-C'":
                                currentUnity = resourceOfBonuses.getClusterArcherRedReady();
                                break;
                        }
                    }
                    currentUnity.setLayoutX(33.5 * j);
                    currentUnity.setLayoutY(33.5 * i);
                    paneControlField.getChildren().add(currentUnity);
                }
            }

        } catch (Exception ignored) {
        }
    }
}
