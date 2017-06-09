package rwilk.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rwilk.logic.FileOperations;

import java.io.File;

/**
 * Created by Rafal Wilk
 */
public class ImageSettings {

    private static Button browse = new Button("Change");
    private static Label info = new Label("Images saves in: ");
    private static TextField path = new TextField();
    private static Stage window;

    public static void display() {
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Images Settings");
        window.setMinWidth(400);
        window.setMinHeight(100);

        path.setText(FileOperations.readFile("image.txt"));

        browse.setOnAction(e -> pathImageChosen());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        GridPane.setConstraints(info, 0, 0);
        GridPane.setConstraints(path, 1, 0);
        GridPane.setConstraints(browse, 2, 0);

        gridPane.getChildren().addAll(info, path, browse);

        path.setDisable(true);
        path.setPrefWidth(path.getText().length() * 7);
        Scene scene = new Scene(gridPane, 400, 100);

        window.setScene(scene);
        window.showAndWait();
    }

    private static void pathImageChosen() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose where saves screenshot...");
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        File selectedDirectory = directoryChooser.showDialog(window);
        try {
            String filePath = selectedDirectory.getAbsolutePath();
            path.setText(filePath);
            path.setPrefWidth(path.getText().length() * 7);
            FileOperations.writeToFile("image.txt", filePath);
        } catch (NullPointerException nex) {
            System.out.println("ImagesSettings + pathChosen = ERROR");
            //logger.error("Main + pathChosen ");
        }
        //path.setText(FileOperations.readFile("image.txt"));
    }

    public static String readFile(){
        String pathFromFile = FileOperations.readFile("image.txt");
        path.setText(pathFromFile);
        return path.getText();
    }
}