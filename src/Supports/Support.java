package Supports;

import Controllers.ControllerMatchMaking;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс Support являтся бонусом поддержки для игроков.
 * У него есть единственный параметр - энергия, взамен на которую активируется бонус.
 */

public abstract class Support {
    private int energy;
    private ImageView sprite;
    private List<ImageView> listUnities;

    Support(int energy) {
        this.energy = energy;
    }

    Support(int energy, ImageView sprite) {
        this.energy = energy;
        this.sprite = sprite;
    }

    Support(int energy, ImageView sprite, List<ImageView> listUnities, double width,  double height) {
        this.energy = energy;
        sprite.setFitWidth(50);
        sprite.setFitHeight(50);
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
