package de.dhbw.kontaktsplitter.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Startup extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main_view.fxml"));
        primaryStage.setTitle("Kontaktsplitter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(350);
        primaryStage.show();
    }
}
