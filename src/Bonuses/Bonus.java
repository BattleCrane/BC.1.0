package Bonuses;

import Controllers.ControllerMatchMaking;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
/**
 * Абстрактный класс Bonus являтся бонусом поддержки для игроков.
 * У него есть единственный параметр - энергия, взамен на которую активируется бонус.
 */

public abstract class Bonus {
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
        this.description = description;
        description.setOnMouseClicked(event -> {

        });
    }

    public abstract void run(ControllerMatchMaking controllerMatchMaking);

    int getEnergy() {
        return energy;
    }

    public ImageView getSprite(){
        return sprite;
    }
}
