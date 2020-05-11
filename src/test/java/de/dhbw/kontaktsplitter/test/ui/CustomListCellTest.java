package de.dhbw.kontaktsplitter.test.ui;

import de.dhbw.kontaktsplitter.ui.Startup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class CustomListCellTest {

    private static Scene scene;
    @Start
    public void start(Stage stage) throws Exception {
        new Startup().start(stage);

        GridPane gridPane = FXMLLoader.load(getClass().getResource("/element_editor.fxml"));
        scene = new Scene(gridPane);

        Stage titleEditorStage = new Stage();
        titleEditorStage.initOwner(stage);
        titleEditorStage.initModality(Modality.WINDOW_MODAL);
        titleEditorStage.setTitle("UnitTests");
        titleEditorStage.setScene(scene);
        titleEditorStage.setMinWidth(650);
        titleEditorStage.setMinHeight(350);
        titleEditorStage.show();
    }

    @BeforeAll
    public static void before()
    {
        if ("true".equalsIgnoreCase(System.getenv("headless")))
        {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }
}
