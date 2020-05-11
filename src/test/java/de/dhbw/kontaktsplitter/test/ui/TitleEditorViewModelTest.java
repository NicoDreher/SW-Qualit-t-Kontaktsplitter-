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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Bornbaum
 */
@ExtendWith(ApplicationExtension.class)
public class TitleEditorViewModelTest {
    private static Scene scene;
    private Stage stage;
    private Stage mainStage;

    @BeforeAll
    public static void before() {
        if("true".equalsIgnoreCase(System.getenv("headless"))) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    /**
     * Initialize stages
     *
     * @param stage main stage
     * @throws Exception
     */
    @Start
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        new Startup().start(stage);

        GridPane gridPane = FXMLLoader.load(getClass().getResource("/title_editor.fxml"));
        scene = new Scene(gridPane);

        this.stage = new Stage();
        this.stage.initOwner(stage);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setTitle("UnitTests");
        this.stage.setScene(scene);
        this.stage.setMinWidth(650);
        this.stage.setMinHeight(350);
        this.stage.show();
    }

    /**
     * Tests if initial state of the window is ok
     *
     * @param robot object simulating user
     */
    @Test
    public void initializeTest(FxRobot robot) {
        TextField newEntryField = (TextField) scene.lookup("#newEntryField");
        Button newEntryButton = (Button) scene.lookup("#newEntryButton");

        VBox topBox = (VBox) scene.lookup("#topBox");

        @SuppressWarnings("unchecked")
        ListView<CustomListCell> listView = (ListView<CustomListCell>) topBox.getChildren().get(0);

        assertTrue(newEntryButton.disableProperty().get());
        assertEquals("", newEntryField.getText());

        //Titles from the configuration
        List<String> expectedTitles = Configuration.getTitles().stream().map(Title::getTitle).collect(
                Collectors.toList());

        //Titles given by the value variables
        List<String> displayedVariableTitles = listView.getItems().stream().map(CustomListCell::getValue)
                .collect(Collectors.toList());
        //Titles actually displayed in the ui
        List<String> displayedTitles = listView.getItems().stream().map(customListCell -> {
            TextField textField = (TextField) customListCell.getChildren().get(0);
            return textField.getText();
        }).collect(Collectors.toList());

        expectedTitles.forEach(title -> assertTrue(displayedVariableTitles.contains(title)));
        expectedTitles.forEach(title -> assertTrue(displayedTitles.contains(title)));

        robot.clickOn(newEntryField);
        robot.type(KeyCode.A);
        robot.type(KeyCode.BACK_SPACE);

        assertTrue(newEntryButton.isDisabled());

        robot.type(KeyCode.A);
        assertFalse(newEntryButton.isDisabled());
    }

    /**
     * Tests that an element, that is not yet present can be added
     *
     * @param robot, simulates user input
     */
    @Test
    public void addAllowedValueTest(FxRobot robot) {
        TextField newEntryField = (TextField) scene.lookup("#newEntryField");
        Button newEntryButton = (Button) scene.lookup("#newEntryButton");

        VBox topBox = (VBox) scene.lookup("#topBox");

        @SuppressWarnings("unchecked")
        ListView<CustomListCell> listView = (ListView<CustomListCell>) topBox.getChildren().get(0);

        robot.clickOn(newEntryField);
        robot.type(KeyCode.A);
        robot.clickOn(newEntryButton);

        boolean found = false;

        for(CustomListCell listElement : listView.getItems()) {
            TextField textField = (TextField) listElement.getChildren().get(0);

            if(textField.getText().equals("a")) {
                found = true;
            }
        }

        assertTrue(found);
    }

    /**
     * Tests that a message is thrown when a duplicate element is inserted
     *
     * @param robot
     */
    @Test
    public void addReplicaValueTest(FxRobot robot) {
        TextField newEntryField = (TextField) scene.lookup("#newEntryField");
        Button newEntryButton = (Button) scene.lookup("#newEntryButton");

        VBox topBox = (VBox) scene.lookup("#topBox");

        @SuppressWarnings("unchecked")
        ListView<CustomListCell> listView = (ListView<CustomListCell>) topBox.getChildren().get(0);

        robot.clickOn(newEntryField);
        robot.type(KeyCode.A);
        robot.clickOn(newEntryButton);

        robot.clickOn(newEntryField);
        robot.type(KeyCode.A);
        robot.clickOn(newEntryButton);

        TestUtil.alert_dialog_has_header_and_content("Meldung",
                "Ein solches Element besteht schon.", robot);

        int asFound = 0;

        for(CustomListCell item : listView.getItems()) {
            TextField textFeld = (TextField) item.getChildren().get(0);

            if(textFeld.getText().equals("a")) {
                asFound++;
            }
        }

        assertEquals(1, asFound);
    }

    /**
     * Closes a stage after a test finished
     */
    @Stop
    public void stop() {
        stage.close();
        mainStage.close();
    }

}
