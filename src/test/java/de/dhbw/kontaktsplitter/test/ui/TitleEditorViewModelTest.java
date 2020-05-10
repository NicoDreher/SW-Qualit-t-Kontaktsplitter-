package de.dhbw.kontaktsplitter.test.ui;

import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.Startup;
import de.dhbw.kontaktsplitter.ui.components.CustomListCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TitleEditorViewModelTest
{
    private static Scene scene;

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

    @Start
    public void start(Stage stage) throws Exception
    {
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

    @Test
    public void initializeTest()
    {
        Button newTitleButton = (Button) scene.lookup("#newTitleButton");
        TextField newTitleField = (TextField) scene.lookup("#newTitleField");

        @SuppressWarnings("unchecked")
        ListView<CustomListCell> titlesView = (ListView<CustomListCell>) scene.lookup("#titlesView");

        assertTrue(newTitleButton.disableProperty().get());
        assertEquals("", newTitleField.getText());

        List<String> expectedTitles = Configuration.getTitles().stream().map(Title::getTitle).collect(
                Collectors.toList());
        List<String> displayedTitles = titlesView.getItems().stream().map(CustomListCell::getValue)
                .collect(Collectors.toList());

        expectedTitles.forEach(title -> assertTrue(displayedTitles.contains(title)));
    }

}
