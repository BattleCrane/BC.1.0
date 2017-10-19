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

    //Бонус "Взрывчатка"
    //Синие:
    public final ImageView getExplosiveBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\BlueUnity\\Explosive.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getExplosiveWallBlue() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\BlueUnity\\Wall4HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getExplosiveWallBlue3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\BlueUnity\\Wall3HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getExplosiveWallBlue2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\BlueUnity\\Wall2HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getExplosiveWallBlue1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\BlueUnity\\Wall1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Красные:
    public final ImageView getExplosiveRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\RedUnity\\Explosive.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getExplosiveWallRed() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\RedUnity\\Wall4HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    public final ImageView getExplosiveWallRed3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\RedUnity\\Wall3HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getExplosiveWallRed2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\RedUnity\\Wall2HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }
    public final ImageView getExplosiveWallRed1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2Explosive\\RedUnity\\Wall1HP.png"));
        unit.setFitWidth(33.5);
        unit.setFitHeight(33.5);
        return unit;
    }

    //Бонус "Боевой штаб"
    public final ImageView getFightingHeadquarters() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters8HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFightingHeadquarters7HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters7HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFightingHeadquarters6HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters6HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFightingHeadquarters5HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters5HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFightingHeadquarters4HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters4HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFightingHeadquarters3HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters3HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFightingHeadquarters2HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters2HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }
    public final ImageView getFightingHeadquarters1HP() {
        ImageView unit = new ImageView(new Image("file:src\\Resources\\Bonuses\\2FightingHeadquarters\\Unity\\FightingHeadquarters1HP.png"));
        unit.setFitWidth(67.0);
        unit.setFitHeight(67.0);
        return unit;
    }

}
