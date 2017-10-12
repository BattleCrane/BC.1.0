package ResourceInit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Класс Resources загружает графические объекты и делегирует над ними.
 */

public final class Resource {

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

    public final ImageView getDestroyedField(){
        ImageView field = new ImageView(new Image("file:src\\Resources\\BattleFields\\DestroyedField.png"));
        field.setFitWidth(33.5);
        field.setFitHeight(33.5);
        return field;
    }

    //Штабы:
    @NotNull
    public final ImageView getHeadquartersBlue() {
        return new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters.png"));
    }
    public final ImageView getHeadquartersBlue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters 1HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersBlue2HP() {
        ImageView unit =  new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters 2HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersBlue3HP() {
        ImageView unit =  new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters 3HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersBlue4HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters 4HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersBlue5HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters 5HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersBlue6HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters 6HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersBlue7HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Headquarters\\Headquarters 7HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }

    public final ImageView getHeadquartersRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersRed1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters 1HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersRed2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters 2HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersRed3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters 3HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersRed4HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters 4HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersRed5HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters 5HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersRed6HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters 6HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getHeadquartersRed7HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Headquarters\\Headquarters 7HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
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
    public final ImageView getGeneratorLevel2Blue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Generator\\Generator - Level 2 1HP.png"));
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
    public final ImageView getGeneratorLevel2Red1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Generator\\Generator - Level 2 1HP.png"));
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
    public final ImageView getGeneratorLevel3Blue1HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Generator\\Generator - Level 3 1HP.png"));
        wallBlue.setFitWidth(67.0);
        wallBlue.setFitHeight(67.0);
        return wallBlue;
    }
    public final ImageView getGeneratorLevel3Blue2HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Generator\\Generator - Level 3 2HP.png"));
        wallBlue.setFitWidth(67.0);
        wallBlue.setFitHeight(67.0);
        return wallBlue;
    }
    public final ImageView getGeneratorLevel3Blue3HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Generator\\Generator - Level 3 3HP.png"));
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
    public final ImageView getGeneratorLevel3Red1HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Generator\\Generator - Level 3 1HP.png"));
        wallBlue.setFitWidth(67.0);
        wallBlue.setFitHeight(67.0);
        return wallBlue;
    }
    public final ImageView getGeneratorLevel3Red2HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Generator\\Generator - Level 3 2HP.png"));
        wallBlue.setFitWidth(67.0);
        wallBlue.setFitHeight(67.0);
        return wallBlue;
    }
    public final ImageView getGeneratorLevel3Red3HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Generator\\Generator - Level 3 3HP.png"));
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
    public final ImageView getWallBlue1HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Wall\\Wall 1HP.png"));
        wallBlue.setFitWidth(33.5);
        wallBlue.setFitHeight(33.5);
        return wallBlue;
    }
    public final ImageView getWallBlue2HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Wall\\Wall 2HP.png"));
        wallBlue.setFitWidth(33.5);
        wallBlue.setFitHeight(33.5);
        return wallBlue;
    }
    public final ImageView getWallBlue3HP() {
        ImageView wallBlue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Wall\\Wall 3HP.png"));
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
    public final ImageView getWallRed3HP() {
        ImageView wallRed = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Wall\\Wall 3HP.png"));
        wallRed.setFitWidth(33.5);
        wallRed.setFitHeight(33.5);
        return wallRed;
    }

    public final ImageView getWallRed2HP() {
        ImageView wallRed = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Wall\\Wall 2HP.png"));
        wallRed.setFitWidth(33.5);
        wallRed.setFitHeight(33.5);
        return wallRed;
    }

    public final ImageView getWallRed1HP() {
        ImageView wallRed = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Wall\\Wall 1HP.png"));
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

    public final ImageView getBarracksLevel2Blue1HP() {
        ImageView barracksLevel2Blue1Hp = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 2 1HP.png"));
        barracksLevel2Blue1Hp.setFitWidth(67.0);
        barracksLevel2Blue1Hp.setFitHeight(33.5);
        return barracksLevel2Blue1Hp;
    }

    public final ImageView getBarracksLevel2Red1HP() {
        ImageView barracksLevel2Blue1Hp = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Barracks\\Barracks - Level 2 1HP.png"));
        barracksLevel2Blue1Hp.setFitWidth(67.0);
        barracksLevel2Blue1Hp.setFitHeight(33.5);
        return barracksLevel2Blue1Hp;
    }

    public final ImageView getBarracksLevel3Blue() {
        ImageView barracksLevel3Blue = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 3.png"));
        barracksLevel3Blue.setFitWidth(67.0);
        barracksLevel3Blue.setFitHeight(33.5);
        return barracksLevel3Blue;
    }

    public final ImageView getBarracksLevel3Red1HP() {
        ImageView barracksLevel3Blue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Barracks\\Barracks - Level 3 1HP.png"));
        barracksLevel3Blue.setFitWidth(67.0);
        barracksLevel3Blue.setFitHeight(33.5);
        return barracksLevel3Blue;
    }

    public final ImageView getBarracksLevel3Red2HP() {
        ImageView barracksLevel3Blue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Barracks\\Barracks - Level 3 2HP.png"));
        barracksLevel3Blue.setFitWidth(67.0);
        barracksLevel3Blue.setFitHeight(33.5);
        return barracksLevel3Blue;
    }

    public final ImageView getBarracksLevel3Red3HP() {
        ImageView barracksLevel3Blue = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Barracks\\Barracks - Level 3 3HP.png"));
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

    public final ImageView getBarracksLevel3Blue1HP() {
        ImageView barracksLevel2Blue1Hp = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 3 1HP.png"));
        barracksLevel2Blue1Hp.setFitWidth(67.0);
        barracksLevel2Blue1Hp.setFitHeight(33.5);
        return barracksLevel2Blue1Hp;
    }

    public final ImageView getBarracksLevel3Blue2HP() {
        ImageView barracksLevel2Blue1Hp = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 3 2HP.png"));
        barracksLevel2Blue1Hp.setFitWidth(67.0);
        barracksLevel2Blue1Hp.setFitHeight(33.5);
        return barracksLevel2Blue1Hp;
    }

    public final ImageView getBarracksLevel3Blue3HP() {
        ImageView barracksLevel2Blue1Hp = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Barracks\\Barracks - Level 3 3HP.png"));
        barracksLevel2Blue1Hp.setFitWidth(67.0);
        barracksLevel2Blue1Hp.setFitHeight(33.5);
        return barracksLevel2Blue1Hp;
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

    public final ImageView getFactoryLevel2Blue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 2 1HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel2Blue2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 2 2HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel2Blue3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 2 3HP.png"));
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
    public final ImageView getFactoryLevel2Red1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 2 1HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel2Red2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 2 2HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel2Red3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 2 3HP.png"));
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
    public final ImageView getFactoryLevel3Blue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 3 1HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Blue2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 3 2HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Blue3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 3 3HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Blue4HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 3 4HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Blue5HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Factory\\Factory - Level 3 5HP.png"));
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
    public final ImageView getFactoryLevel3Red1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3 1HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Red2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3 2HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Red3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3 3HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Red4HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3 4HP.png"));
        unit.setFitWidth(100.5);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFactoryLevel3Red5HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Factory\\Factory - Level 3 5HP.png"));
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
    public final ImageView getTurretLevel1Blue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Turret\\Turret - Level 1 1HP.png"));
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
    public final ImageView getTurretLevel1Red1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Turret\\Turret - Level 1 1HP.png"));
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
    public final ImageView getTurretLevel2Blue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Turret\\Turret - Level 2 1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getTurretLevel2Blue2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Turret\\Turret - Level 2 2HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getTurretLevel2Blue3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Buildings\\Turret\\Turret - Level 2 3HP.png"));
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
    public final ImageView getTurretLevel2Red1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Turret\\Turret - Level 2 1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getTurretLevel2Red2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Turret\\Turret - Level 2 2HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getTurretLevel2Red3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Buildings\\Turret\\Turret - Level 2 3HP.png"));
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
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Army\\Gunner\\GunnerReady.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getGunnerReadyRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Army\\Gunner\\GunnerReady.png"));
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
    public final ImageView getTankBlue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Army\\Tank\\Tank 1HP.png"));
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
    public final ImageView getTankRed1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Army\\Tank\\Tank 1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTankReadyBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Army\\Tank\\TankReady.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getTankReadyBlue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\BlueUnity\\Army\\Tank\\TankReady 1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTankReadyRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Army\\Tank\\TankReady.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getTankReadyRed1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\RedUnity\\Army\\Tank\\TankReady 1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
}