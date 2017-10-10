package Graphics;

import Players.Player;
import Supports.Support;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by мсиайнина on 10.10.2017.
 */
public class Bonuser {
    public static void showBonuses(Player player, Pane paneControlSupport){
        int x = 0;
        int y = 0;
        for (Support support: player.getListOfBonuses()){
            support.getSprite().setLayoutX(x);
            support.getSprite().setLayoutX(y);
            paneControlSupport.getChildren().add(support.getSprite());
            x += 50;
        }
    }
}
