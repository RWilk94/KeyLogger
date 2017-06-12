package rwilk.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rwilk.logic.CaptureScreen;
import rwilk.logic.FileOperations;

import java.io.File;

/**
 * Created by Rafał Wilk
 */
public class VideoSettings  {

    private static Button browse = new Button("Change");
    private static Label info = new Label("Video saves in: ");
    private static TextField path = new TextField();
    private static Stage window;

    /**
     * Metoda wyświetla graficzne okno ustawień filmów.
     * Można ustawić ścieżkę do folderu, w którym będziemy zapisywać filmy.
     * Można ustawić długość nagrywanych filmów.
     */
    public static void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Video Settings");
        window.setMinWidth(400);
        window.setMinHeight(100);

        browse.setOnAction(e -> pathChosen());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        GridPane.setConstraints(info, 0, 0);
        GridPane.setConstraints(path, 1, 0);
        GridPane.setConstraints(browse, 2,0);

        Label duration = new Label("Video duration:");
        GridPane.setConstraints(duration, 0, 1);
        Label minutes = new Label("minutes");
        GridPane.setConstraints(minutes, 3, 1);

        ChoiceBox<Integer> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                1, 5, 10, 20, 30, 60
        ));
        choiceBox.getSelectionModel().select(CaptureScreen.durationInMinutes);
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> CaptureScreen.durationInMinutes = newValue);
        GridPane.setConstraints(choiceBox, 2, 1);

        gridPane.getChildren().addAll(info, path, browse, duration, choiceBox, minutes);

        path.setDisable(true);
        path.setPrefWidth(path.getText().length() * 7);
        Scene scene = new Scene(gridPane, 400, 100);

        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Metoda uruchamia okienko wyboru folderu, gdzie będziemy zapisywać filmy.
     */
    private static void pathChosen() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose where save video...");
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        File selectedDirectory = directoryChooser.showDialog(window);
        try {
            String filePath = selectedDirectory.getAbsolutePath();
            path.setText(filePath);
            path.setPrefWidth(path.getText().length() * 7);
            //writeToFile(filePath);
            FileOperations.writeToFile("video.txt", filePath);
        } catch (NullPointerException nex) {
            System.out.println("VideoSettings + pathChosen = ERROR");
            //logger.error("Main + pathChosen ");
        }
    }

    /**
     * Metoda wczytuje z pliku scieżkę, gdzie zapisujemy filmy.
     * @return ścieżka do katalogu, gdzie program zapisuje filmy.
     */
    public static String readFile(){
        String pathFromFile = FileOperations.readFile("video.txt", "C:\\KeyLoggerVideo");
        path.setText(pathFromFile);
        return path.getText();
    }

}
