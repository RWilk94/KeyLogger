package rwilk.mediaplayer;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import rwilk.view.AlertBox;

public class Player extends BorderPane {

    private Media media;
    public MediaPlayer player;
    private MediaView view;
    private Pane mpane;
    MediaBar bar;

    /**
     * Konstruktor odtwarzacza filmów. W przypadku podania ścieżki do katalogu, w którym są pliki filmowe, automatycznie zaczyna odtwarzanie.
     *
     * @param file ścieżka do katalogu z filmami
     */
    public Player(String file) {
        try {
            media = new Media(file);
            player = new MediaPlayer(media);
            view = new MediaView(player);
        } catch (MediaException mex) {
            AlertBox.display("Nieobsługiwane pliki...", "W folderze nie może być innych plików niż pliki video!");
            return;
        }
        mpane = new Pane();
        mpane.getChildren().add(view);
        setCenter(mpane);
        bar = new MediaBar(player);
        setBottom(bar);
        setStyle("-fx-background-color: #bfc2c7");
        player.play();
    }

    /**
     * Bezparametrowy konstuktor. Reprezentuje puste okno odtwarzacza filmów.
     */
    public Player() {
    }
}
