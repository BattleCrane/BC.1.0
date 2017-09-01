package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    //Статическое окно, которое можно переключать
    public static Stage BattleCraneStage = new Stage();

    @Override
    public void start(Stage unused) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlBattleFieldWindow.fxml"));
        BattleCraneStage.getIcons().add(new Image("file:src\\Resourses\\Icon.png"));
        BattleCraneStage.setResizable(false);
        BattleCraneStage.setTitle("BattleCrane");
        BattleCraneStage.setScene(new Scene( root));
        BattleCraneStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
