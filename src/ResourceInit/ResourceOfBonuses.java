package ResourceInit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by мсиайнина on 12.10.2017.
 */
public class ResourceOfBonuses {
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
}
