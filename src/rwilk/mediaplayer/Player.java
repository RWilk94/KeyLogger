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
    public  MediaPlayer player;
    private MediaView view;
    private Pane mpane;

    MediaBar bar;

    public Player(String file){
        try {
            media = new Media(file);
            player = new MediaPlayer(media);
            view = new MediaView(player);
        } catch (MediaException mex){
            //AlertBox alertBox = new AlertBox();
            AlertBox.display("Nieobsługiwane pliki...","W folderze nie może być innych plików niż pliki video!");
            //media = null;
            //player = new MediaPlayer();
            //view = new MediaView(player);
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

    public Player(){}


}
