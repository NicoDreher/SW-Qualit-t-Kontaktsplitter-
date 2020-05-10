package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Title;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class Startup extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Kontaktsplitter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(350);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
