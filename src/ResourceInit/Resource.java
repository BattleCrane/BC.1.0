package ResourceInit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Класс Resources загружает графические объекты и делегирует над ними.
 */

public class Resource {

    public Resource() {

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Пустая клетка
    public final ImageView getEmptyField() {
        ImageView field = new ImageView(new Image("file:src\\Resources\\BattleFields\\Cell.png"));
        field.setFitWidth(33.5);
        field.setFitHeight(33.5);
        return field;
    }

    //Штабы:
    public final ImageView getHeadquartersBlue() {
        return new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters.png"));
    }

    public final ImageView getHeadquartersRed() {
        return new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters.png"));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Генераторы:
    public final ImageView getGeneratorLevel1Blue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Generator\\Generator - Level 1.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getGeneratorLevel1Red() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Generator\\Generator - Level 1.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getGeneratorLevel2Blue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Generator\\Generator - Level 2.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getGeneratorLevel2Red() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Generator\\Generator - Level 2.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getGeneratorLevel3Blue() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Generator\\Generator - Level 3.png"));
        wallBlue.setFitWidth(67.0);
        wallBlue.setFitHeight(67.0);
        return wallBlue;
    }

    public final ImageView getGeneratorLevel3Red() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Generator\\Generator - Level 3.png"));
        wallBlue.setFitWidth(67.0);
        wallBlue.setFitHeight(67.0);
        return wallBlue;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Стены: (по умолчанию горизонтальные, то есть два блока идут вдоль)
    public final ImageView getWallBlue() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Wall\\Wall.png"));
        wallBlue.setFitWidth(33.5);
        wallBlue.setFitHeight(33.5);
        return wallBlue;
    }

    public final ImageView getWallRed() {
        ImageView wallRed = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Wall\\Wall.png"));
        wallRed.setFitWidth(33.5);
        wallRed.setFitHeight(33.5);
        return wallRed;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Бараки: (по умолчанию горизонтальные)
    public final ImageView getBarracksLevel1Blue() {
        ImageView barracksLevel1Blue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 1.png"));
        barracksLevel1Blue.setFitWidth(67.0);
        barracksLevel1Blue.setFitHeight(33.5);
        return barracksLevel1Blue;
    }

    public final ImageView getBarracksLevel1Red() {
        ImageView barracksLevel1Blue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Barracks\\Barracks - Level 1.png"));
        barracksLevel1Blue.setFitWidth(67.0);
        barracksLevel1Blue.setFitHeight(33.5);
        return barracksLevel1Blue;
    }

    public final ImageView getBarracksLevel2Blue() {
        ImageView barracksLevel2Blue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 2.png"));
        barracksLevel2Blue.setFitWidth(67.0);
        barracksLevel2Blue.setFitHeight(33.5);
        return barracksLevel2Blue;
    }

    public final ImageView getBarracksLevel2Red() {
        ImageView barracksLevel2Blue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Barracks\\Barracks - Level 2.png"));
        barracksLevel2Blue.setFitWidth(67.0);
        barracksLevel2Blue.setFitHeight(33.5);
        return barracksLevel2Blue;
    }

    public final ImageView getBarracksLevel3Blue() {
        ImageView barracksLevel3Blue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 3.png"));
        barracksLevel3Blue.setFitWidth(67.0);
        barracksLevel3Blue.setFitHeight(33.5);
        return barracksLevel3Blue;
    }

    public final ImageView getBarracksLevel3Red() {
        ImageView barracksLevel3Blue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Barracks\\Barracks - Level 3.png"));
        barracksLevel3Blue.setFitWidth(67.0);
        barracksLevel3Blue.setFitHeight(33.5);
        return barracksLevel3Blue;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Заводы: (по умолчанию горизонтальные)
    public final ImageView getFactoryLevel1Blue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 1.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getFactoryLevelRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 1.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getFactoryLevel2Blue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 2.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getFactoryLevel2Red() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 2.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getFactoryLevel3Blue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 3.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getFactoryLevel3Red() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Турели:
    public final ImageView getTurretLevel1Blue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Turret\\Turret - Level 1.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTurretLevel1Red() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Turret\\Turret - Level 1.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTurretLevel2Blue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Turret\\Turret - Level 2.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTurretLevel2Red() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Turret\\Turret - Level 2.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Армия:
    public final ImageView getGunnerBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Army\\Gunner\\Gunner.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getGunnerRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Army\\Gunner\\Gunner.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getGunnerReadyBlue() {
        ImageView unit = new ImageView(new Image("file: src\\Resources\\BlueUnity\\Army\\Gunner\\Gunner-Ready.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getGunnerReadyRed() {
        ImageView unit = new ImageView(new Image("file: src\\Resources\\RedUnity\\Army\\Gunner\\Gunner-Ready.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTankBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Army\\Tank\\Tank.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTankRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Army\\Tank\\Tank.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTankReadyBlue() {
        ImageView unit = new ImageView(new Image("file: src\\Resources\\BlueUnity\\Army\\Tank\\Tank-Ready.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTankReadyRed() {
        ImageView unit = new ImageView(new Image("file: src\\Resources\\RedUnity\\Army\\Tank\\Tank-Ready.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
}