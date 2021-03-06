package hangman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Create GUI and game engine
        new GameEngine();
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        root.setStyle("-fx-background-color: #454545;");
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(new Scene(root, 800, 475));
        primaryStage.show();
    }//end start


    public static void main(String[] args) {
        launch(args);
    }
}
