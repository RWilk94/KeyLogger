package rwilk.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import rwilk.logger.RegisterNativeHook;
import rwilk.logic.CaptureAudio;
import rwilk.logic.CaptureScreen;
import rwilk.logic.FileOperations;
import rwilk.mediaplayer.VideoPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main extends Application implements ActionListener {

    private static final DateFormat dateFormatForPath = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    private Stage window;
    private Button startRecording;
    private Button stopRecording;
    private static CaptureScreen capture;
    private Timer checkTime = new Timer(500, this);
    public static boolean newVideo = false;
    private SystemTray tray;
    private TrayIcon trayIcon;

    /**
     * Metoda buduje graficzne okno aplikacji.
     * @param primaryStage główne okno aplikacji.
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("KeyLogger");

        Platform.setImplicitExit(false);// instructs the javafx system not to exit implicitly when the last application window is shut.
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);// sets up the tray icon (using awt code run on the swing thread).

        Menu fileMenu = new Menu("File");
        MenuItem hide = new MenuItem("Hide");
        hide.setOnAction(e -> window.close());
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            closeProgram();
            window.close();
        });
        fileMenu.getItems().addAll(hide, exit);

        Menu settingsMenu = new Menu("Settings");
        MenuItem videosSettings = new MenuItem("Video Settings");
        MenuItem imagesSettings = new MenuItem("Images Settings");
        CheckMenuItem recordAudio = new CheckMenuItem("Record Audio");
        recordAudio.setOnAction( e -> {
            CaptureAudio.recordAudio = recordAudio.isSelected();
        });
        recordAudio.setSelected(false);
        videosSettings.setOnAction(e -> VideoSettings.display());
        imagesSettings.setOnAction(e -> ImageSettings.display());
        settingsMenu.getItems().addAll(videosSettings, imagesSettings, recordAudio);

        Menu aboutMenu = new Menu("About");
        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> {
            AboutWindow.display();
        });
        aboutMenu.getItems().add(about);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, settingsMenu, aboutMenu);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setId("pane");
        startRecording = new Button("Start recording");
        stopRecording = new Button("Stop recording");
        stopRecording.setDisable(true);
        startRecording.setOnAction(e -> new Thread(this::startRecording).start());
        stopRecording.setOnAction(e -> new Thread(this::stopRecording).start());

        Button videoPlayer = new Button("Video Player");

        videoPlayer.setOnAction( e -> {
            String pathToVideo = FileOperations.readFile("video.txt", "C:\\KeyLoggerVideo");
            pathToVideo = pathToVideo.replace("\\", "/");
            System.out.println(pathToVideo);
            VideoPlayer.display(pathToVideo);
            stopRecording();
        });

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.getChildren().addAll(startRecording, stopRecording, videoPlayer);
        borderPane.setCenter(hBox);

        Scene scene = new Scene(borderPane, 400, 400);
        scene.getStylesheets().add("rwilk/view/style.css");
        window.setScene(scene);
        window.show();
    }

    /**
     * Metoda zamyka aplikację i usuwa ikonkę z systemowego tray'a.
     */
    private void closeProgram() {
        stopRecording();
        Platform.exit();
        tray.remove(trayIcon);
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
        if (capture != null && capture.running) capture.stopCapture();
    }

    /**
     * Metoda rozpoczyna nagrywanie obrazu. W przypadku podłączonych do komputera większej ilości ekranów - nagrywane są wszystkie.
     */
    private static void launchRecording() {
        String path = VideoSettings.readFile();
        int width = 0;
        int height = 0;
        for (int i = 0; i < Screen.getScreens().size(); i++) {
            width += (int) Screen.getScreens().get(i).getVisualBounds().getWidth();
            if ((int) Screen.getScreens().get(i).getVisualBounds().getHeight() > height)
                height = (int) Screen.getScreens().get(i).getVisualBounds().getHeight();
        }
        capture = new CaptureScreen(path + "\\" + dateFormatForPath.format(Calendar.getInstance().getTime()) + ".mp4",
                width, height);
        try {
            capture.startCapture();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda przypisana do przycisku. Uruchamia nagrywanie i dezaktywuje przycisk nagrywania.
     */
    private void startRecording() {
        launchRecording();
        checkTime.start();
        startRecording.setDisable(true);
        stopRecording.setDisable(false);
    }

    /**
     * Metoda przypisana do przycisku. Zatrzymuje nagrywanie i aktywuje przycisk nagrywania.
     */
    private void stopRecording() {
        if (capture != null && capture.running)
            capture.stopCapture();
        checkTime.stop();
        startRecording.setDisable(false);
        stopRecording.setDisable(true);
    }

    /**
     * Metoda obsługująca słuchacza zdarzeń.
     * @param e zdarzenie od słuchacza zdarzeń.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == checkTime) && newVideo) {
            newVideo = false;
            if (capture.running) capture.stopCapture();
            launchRecording();
            System.out.println("Saved and created new video.");
        }
    }

    /**
     * Metoda tworzy ikonę w systemowym tray'u.
     */
    private void addAppToTray() {
        try {
            java.awt.Toolkit.getDefaultToolkit();// ensure awt toolkit is initialized.
            if (!java.awt.SystemTray.isSupported()) { // app requires system tray support, just exit if there is no support.
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }
            tray = java.awt.SystemTray.getSystemTray();
            Image image = ImageIO.read(Main.class.getResourceAsStream("spy1.png"));
            trayIcon = new java.awt.TrayIcon(image);
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));
            java.awt.MenuItem openItem = new java.awt.MenuItem("Show");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> closeProgram());
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    /**
     * Metoda z menu kontekstowego tray'a. Pokazuje okno programu.
     */
    private void showStage() {
        if (window != null) {
            window.show();
            window.toFront();
        }
    }

    /**
     * Metoda main uruchamia okno aplikacji.
     * @param args argumenty metody main
     */
    public static void main(String[] args) {
        RegisterNativeHook.registerNativeHook(); //uruchamia przechwytywanie keyloggera
        launch(args);      //uruchamia okno
    }

}
