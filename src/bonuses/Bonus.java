package bonuses;

import game.controllers.ControllerMatchMaking;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
/**
 * Абстрактный класс Bonus являтся бонусом поддержки для игроков.
 * У него есть единственный параметр - энергия, взамен на которую активируется бонус.
 */
// TODO: 16.12.2017 remove this extending!!! Create a subclass ButtonBonus which can be extend by button
public abstract class Bonus extends Button{
    private int energy;
    private ImageView sprite;
    private ImageView description;

    Bonus(int energy) {
        this.energy = energy;
    }

    Bonus(int energy, ImageView sprite,ImageView description) {
        this.energy = energy;
        sprite.setFitWidth(75);
        sprite.setFitHeight(75);
        this.sprite = sprite;

        //Описание
        this.description=description;
        this.description.setFitHeight(200);
        this.description.setFitHeight(200);
        this.description.setVisible(false);
        setOnMouseEntered(event -> {this.description.setVisible(true);});
        setOnMouseExited(event -> {this.description.setVisible(false);});
    }

    public abstract void run(ControllerMatchMaking controllerMatchMaking);

    int getEnergy() {
        return energy;
    }

    public ImageView getSprite(){
        return sprite;
    }
}
