package rwilk.mediaplayer;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class VideoPlayer {

    private static Player player;
    private static FileChooser fileChooser;
    private static String currentVideo = "";
    private static List<String> listOfVideosName = new ArrayList<>();
    private static Stage primaryStage = new Stage();
    private static Scene scene;
    private static boolean load = false;
    public static String pathToDirectory = "C:\\KeyLoggerVideo";

    /**
     * Odpowiednik metody start(). Budujemy graficzne okno odtwarzacza video.
     * Automatycznie zaczynamy odtwarzac pierwszy film z folderu.
     *
     * @param pathToDirectory ścieżka do katalogu, z którego mamy odtwarzać filmy.
     */
    public static void display(String pathToDirectory) {
        if (player != null)
            player.player.stop();
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Close");
        MenuBar menu = new MenuBar();
        file.getItems().add(open);
        file.getItems().add(exit);
        menu.getMenus().add(file);
        fileChooser = new FileChooser();
        primaryStage.setTitle("Video Player");
        listOfVideosName = setListOfVideoInDirectory(pathToDirectory);
        int count = 0;
        if (!load) {
            if (listOfVideosName.size() >= 1 && currentVideo.isEmpty()) {
                currentVideo = listOfVideosName.get(0);
            } else
                for (int i = 0; i < listOfVideosName.size(); i++) {
                    if (currentVideo.equals(listOfVideosName.get(i)) && i + 1 < listOfVideosName.size()) {
                        currentVideo = listOfVideosName.get(i + 1);
                        break;
                    } else {
                        count++;
                    }
                }
        } else load = false;
        if (!currentVideo.isEmpty() && count != listOfVideosName.size()) player = new Player("file:///" + currentVideo);
        else player = new Player();
        player.setTop(menu);
        scene = new Scene(player, 800, 600, Color.BLACK);

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        exit.setOnAction(e -> {
            primaryStage.close();
            if (player.player != null)
                player.player.pause();
        });

        open.setOnAction(e -> {
            if (player.player != null)
                player.player.pause();
            File videoFile = fileChooser.showOpenDialog(primaryStage);
            if (videoFile != null) {
                currentVideo = videoFile.getAbsolutePath();
                currentVideo = currentVideo.replace("\\", "/");
                setPath(currentVideo);
                listOfVideosName = setListOfVideoInDirectory(pathToDirectory);
                //player = new Player(currentVideo);
                load = true;
                display(pathToDirectory);
            }
        });
    }

    /**
     * Metoda kierunek slashy w ścieżce do katalogu z filmami.
     * Np. C:\KeyLogger zamienia na C:/KeyLogger.
     *
     * @param path ścieżka do katalogu z filmami.
     */
    private static void setPath(String path) {
        path = path.replace("\\", "/");
        int index = path.lastIndexOf("/");
        path = path.substring(0, index + 1);
        pathToDirectory = path;
    }

    /**
     * Metoda odczytuje wszystkie pliki w danym katalogu i zapisuje ich nazwy w Liście
     *
     * @param path ścieżka do katalogu
     * @return List<String> lista zawierająca nazwy wszystkich plików w katalogu.
     */
    private static List<String> setListOfVideoInDirectory(String path) {
        List<String> listOfVideosName = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            List<Path> list = paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (int i = 0; i < list.size(); i++) {
                String replace = list.get(i).toString();
                replace = replace.replace("\\", "/");
                listOfVideosName.add(replace);
                System.out.println(listOfVideosName.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfVideosName;
    }
}
