package rwilk.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rwilk.logic.HardwareInfo;

import java.io.File;

/**
 * Created by Rafał Wilk
 */
public class AboutWindow {


    public static void display(){

        Stage window = new Stage();

        HardwareInfo hardwareInfo = new HardwareInfo();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Photo Album");
        window.setMinWidth(400);
        window.setMinHeight(100);


        VBox vbox = new VBox(8);

        vbox.setPadding(new Insets(10));
        //Label label = new Label("Logs from keyboard:");
        //label.setFont(Font.font("Calibri", 22));

        //String keylogger = ReadKeyLogger.readKeyLogger();

        //Text text = new Text(keylogger);
        //text.setFont(Font.font("Calibri", 12));

        TextField myIpAddressT = new TextField();
        Label myIpAddressL = new Label("Your public IP address: ");
        myIpAddressT.setText(hardwareInfo.getPublicIpAddress());
        myIpAddressT.setDisable(true);

        TextField osNameT = new TextField();
        Label osNameL = new Label("Your OS name: ");
        osNameT.setText(hardwareInfo.getNameOS());
        osNameT.setDisable(true);

        TextField osVersionT = new TextField();
        Label osVersionL = new Label("Your OS version: ");
        osVersionT.setText(hardwareInfo.getVersionOS());
        osVersionT.setDisable(true);

        TextField osArchitectureT = new TextField();
        Label osArchitectureL = new Label("Your OS architecture: ");
        osArchitectureT.setText(hardwareInfo.getArchitectureOS());
        osArchitectureT.setDisable(true);

        TextField ramT = new TextField();
        Label ramL = new Label("Count your RAM memory: ");
        ramT.setText(hardwareInfo.getMemorySize().toString());
        ramT.setDisable(true);

        Label discsL = new Label("Info about your discs: ");
        File[] roots = File.listRoots();

        String infoAboutDiscs = "";

        Text discsT = new Text();
        for (File root : roots) {
            infoAboutDiscs += "File system root: " + root.getAbsolutePath() +"\n";
            infoAboutDiscs += "Total space (GB): " + root.getTotalSpace()/hardwareInfo.GIGA_BYTES  + "\n";
            infoAboutDiscs += "Free space (GB): " + root.getFreeSpace()/hardwareInfo.GIGA_BYTES + "\n\n";
        }
        discsT.setText(infoAboutDiscs);


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        GridPane.setConstraints(myIpAddressL, 0, 0);
        GridPane.setConstraints(myIpAddressT, 1, 0);

        GridPane.setConstraints(osNameL, 0, 1);
        GridPane.setConstraints(osNameT, 1, 1);

        GridPane.setConstraints(osVersionL, 0, 2);
        GridPane.setConstraints(osVersionT, 1, 2);

        GridPane.setConstraints(osArchitectureL, 0, 3);
        GridPane.setConstraints(osArchitectureT, 1, 3);

        GridPane.setConstraints(ramL, 0, 4);
        GridPane.setConstraints(ramT, 1, 4);

        GridPane.setConstraints(discsL, 0, 5);
        GridPane.setConstraints(discsT, 1, 5);

        gridPane.getChildren().addAll(myIpAddressL, myIpAddressT, osNameL, osNameT, osVersionL, osVersionT,
                osArchitectureL, osArchitectureT, ramL, ramT, discsL, discsT);


        vbox.getChildren().addAll(gridPane);
        //vbox.getChildren().add(text);

        ScrollPane sp = new ScrollPane();
        sp.setContent(vbox);

        Scene scene = new Scene(sp, 400, 400);
        window.setScene(scene);
        window.showAndWait();
    }

}
