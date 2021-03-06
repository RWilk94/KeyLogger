package rwilk.mediaplayer;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MediaBar extends HBox implements ActionListener {

    private Slider time = new Slider();
    private Slider vol = new Slider();
    private Button playButton = new Button("||");
    private Label volume = new Label("Volume: ");
    private MediaPlayer player;
    private Timer timer = new Timer(500, this);

    /**
     * Metoda odpowiada za graficzne zbudowanie i obsługę przycisków w dolnym pasku odtwarzacza video.
     * Obsługuje przyciski: play, pause, pasek postępu filmu, pasek głośności.
     *
     * @param play odtwarzacz video, w którym umieszczamy belkę z przyciskami odtwarzacza
     */
    public MediaBar(MediaPlayer play) {
        System.out.println("Player = play");
        player = play;
        timer.start();
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 10, 5, 10));
        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);
        HBox.setHgrow(time, Priority.ALWAYS);
        playButton.setPrefWidth(30);
        getChildren().addAll(playButton, time, volume, vol);

        playButton.setOnAction(e -> {
            Status status = player.getStatus();
            if (status == Status.PLAYING) {
                if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {
                    player.seek(player.getStartTime());
                    player.play();
                } else {
                    player.pause();
                    playButton.setText(">");
                }
            }
            if (status == Status.PAUSED || status == Status.HALTED || status == Status.STOPPED) {
                player.play();
                playButton.setText("||");
            }
        });

        player.currentTimeProperty().addListener(observable -> updateValue());

        time.valueProperty().addListener(observable -> {
            if (time.isPressed()) {
                player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
            }
        });

        vol.valueProperty().addListener(observable -> {
            if (vol.isPressed()) {
                player.setVolume(vol.getValue() / 100);
            }
        });
    }

    /**
     * Metoda aktualizuje pasek pokazujący postęp filmu.
     */
    private void updateValue() {
        Platform.runLater(() -> time.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100));
    }

    public static boolean change = false;

    /**
     * Metoda obsłuchuje zdarzenia od słuchacza zdarzeń.
     *
     * @param e zdarzenie od słuchacza zdarzeń.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer && player.getCurrentTime().toMillis() == player.getTotalDuration().toMillis()) {
            VideoPlayer.display(VideoPlayer.pathToDirectory);
            change = true;
            timer.stop();
            player.stop();
            player.seek(player.getStartTime());
            player.play();
        }
    }
}
