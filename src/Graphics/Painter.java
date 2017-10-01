package Graphics;

import BattleFields.ControlBattler;
import ResourceInit.Resource;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public final class Painter {
    public static void drawGraphic(ControlBattler controlBattler, Resource resource, Pane paneControlField){
        try {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    boolean isReadyUnit = false;
                    String element = controlBattler.getBattleField().getMatrix().get(i).get(j);
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
                            System.out.println("Found");
                            break;
                        case "2^!-T'":
                            currentUnity = resource.getTankReadyRed();
                            System.out.println("Found");
                            isReadyUnit = true;
                            break;
                    }
                    if (!isReadyUnit){
                        switch (element.substring(0, 2) + element.substring(3)) {
                            //Штаб:
                            case "8^+h'":
                                currentUnity = resource.getHeadquartersBlue();
                                break;
                            case "8^-h'":
                                currentUnity = resource.getHeadquartersRed();
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
                            case "2<-g'":
                                currentUnity= resource.getGeneratorLevel2Red();
                                break;
                            //--Уровень 3:
                            case "4>+g'":
                                currentUnity = resource.getGeneratorLevel3Blue();
                                break;
                            case "4>-g'":
                                currentUnity = resource.getGeneratorLevel3Red();
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
                            //--Уровень 3:
                            case "4>+b'":
                                currentUnity = resource.getBarracksLevel3Blue();
                                break;
                            case "4>-b'":
                                currentUnity = resource.getBarracksLevel3Red();
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
                            //--Уровень 3:
                            case "6>+f'":
                                currentUnity = resource.getFactoryLevel3Blue();
                                break;
                            case "6>-f'":
                                currentUnity = resource.getFactoryLevel3Red();
                                break;
                            //Турель:
                            //--Уровень 1:
                            case "2^+t'":
                                currentUnity = resource.getTurretLevel1Blue();
                                break;
                            case "2^-t'":
                                currentUnity = resource.getTurretLevel1Red();
                                break;
                            //--Уровень 2:
                            case "4<+t'":
                                currentUnity = resource.getTurretLevel2Blue();
                                break;
                            case "4<-t'":
                                currentUnity = resource.getTurretLevel2Red();
                                break;
                            //Стена:
                            case "4^-w'":
                                currentUnity = resource.getWallRed();
                                break;
                            case "4^+w'":
                                currentUnity = resource.getWallBlue();
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
                            case "2^-T'":
                                currentUnity = resource.getTankRed();
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
                    currentUnity.setLayoutX(33.5 * j);
                    currentUnity.setLayoutY(33.5 * i);
                    paneControlField.getChildren().add(currentUnity);
                }
            }

        } catch (Exception ignored) {
        }
    }
}
