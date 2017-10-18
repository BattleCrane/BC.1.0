package ResourceInit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by мсиайнина on 12.10.2017.
 */
public final class ResourceOfBonuses {
    // Бонус "Преграда"
    public final ImageView getObstacleBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Obstacle\\BlueUnity\\Obstacle.png" ));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getObstacleRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Obstacle\\RedUnity\\Obstacle.png" ));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Бонус "Скорая помощь"
    //Синие:
    public final ImageView getArmoredGunnerBlueReady() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\BlueUnity\\ArmoredGunner1HPReady.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getArmoredGunnerBlueReady1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\BlueUnity\\ArmoredGunnerReady.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getArmoredGunnerBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\BlueUnity\\ArmoredGunner.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getArmoredGunnerBlue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\BlueUnity\\ArmoredGunner 1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Красные:
    public final ImageView getArmoredGunnerRedReady() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\RedUnity\\ArmoredGunner Ready.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getArmoredGunnerRedReady1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\RedUnity\\ArmoredGunner 1HP Ready.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getArmoredGunnerRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\RedUnity\\ArmoredGunner.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getArmoredGunnerRed1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1Ambulance\\RedUnity\\ArmoredGunner 1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Бонус "Тяжелые снаряды"
    //Синие:
    public final ImageView getHeavyGunnerBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1HeavyShells\\BlueUnity\\HeavyGunner.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getHeavyGunnerBlueReady() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1HeavyShells\\BlueUnity\\HeavyGunnerReady.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Красные:
    public final ImageView getHeavyGunnerRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1HeavyShells\\RedUnity\\HeavyGunner.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getHeavyGunnerRedReady() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1HeavyShells\\RedUnity\\HeavyGunnerReady.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Бонус "Энергетическая батарея"
    //Синие:
    public final ImageView getEnergyBatteryBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1EnergyBlock\\BlueUnity\\EnergyBlock.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Красные:
    public final ImageView getEnergyBatteryRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\1EnergyBlock\\RedUnity\\EnergyBlock.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
}
