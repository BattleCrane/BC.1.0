package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Класс BGame является стартом приложения.
 * У него существует один статический экземпляр Stage, который не является аргументом метода start.
 * Но в реализации этого метода, BattleCraneStage ведет себя как аргумент,
 * а реальный объект на входе Stage unused игнорируется
 */

// TODO: 16.12.2017 make this injectable & set maven project
public class BGame extends Application {
    //Статическое окно, которое можно переключать
    private static Stage BattleCraneStage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage unused) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlBattleFieldWindow.fxml"));
        BattleCraneStage.getIcons().add(new Image("file:src\\Resources\\Icon.png"));
        BattleCraneStage.setResizable(false);
        BattleCraneStage.setTitle("BattleCrane");
        BattleCraneStage.setScene(new Scene(root));
        BattleCraneStage.show();
    }
}
