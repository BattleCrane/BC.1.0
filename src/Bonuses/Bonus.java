package Bonuses;

import Controllers.ControllerMatchMaking;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс Bonus являтся бонусом поддержки для игроков.
 * У него есть единственный параметр - энергия, взамен на которую активируется бонус.
 */

public abstract class Bonus {
    private int energy;
    private ImageView sprite;
    private List<ImageView> listUnities;

    Bonus(int energy) {
        this.energy = energy;
    }

    Bonus(int energy, ImageView sprite) {
        this.energy = energy;
        sprite.setFitWidth(75);
        sprite.setFitHeight(75);
        this.sprite = sprite;
    }

    Bonus(int energy, ImageView sprite, List<ImageView> listUnities, double width, double height) {
        this.energy = energy;
        sprite.setFitWidth(75);
        sprite.setFitHeight(75);
        this.sprite = sprite;
        List<ImageView> list = new ArrayList<>();
        for (ImageView imageView: listUnities){
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            list.add(imageView);
        }
        this.listUnities = list;
    }

    public abstract void run(ControllerMatchMaking controllerMatchMaking);

    public int getEnergy() {
        return energy;
    }

    public ImageView getSprite(){
        return sprite;
    }

    public List<ImageView> getUnities() {
        return listUnities;
    }
}
