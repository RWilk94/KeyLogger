package rwilk.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rwilk.logger.RegisterNativeHook;

/**
 * Created by Rafal Wilk
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**
     * Glowna klasa programu.
     * Rejestrujemy sluchasza zdarzen i wystwietlamy okno.
     * @param args argumenty
     */
    public static void main(String[] args) {
        RegisterNativeHook.registerNativeHook();
        launch(args);
    }
}
