package de.dhbw.kontaktsplitter.test.ui;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.Startup;
import de.dhbw.kontaktsplitter.ui.components.CustomListCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link de.dhbw.kontaktsplitter.ui.PatternEditorViewModel}
 *
 * @author Lukas Lautenschlager
 */
@ExtendWith(ApplicationExtension.class)
class PatternEditorViewModelTest {
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
     * Initializes scenes
     *
     * @param inputStage - stage
     * @throws Exception
     */
    @Start
    public void start(Stage inputStage) throws Exception {
        mainStage = inputStage;
        new Startup().start(inputStage);

        SplitPane gridPane = FXMLLoader.load(getClass().getResource("/pattern_editor.fxml"));
        scene = new Scene(gridPane);

        stage = new Stage();
        stage.initOwner(inputStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("UnitTests");
        stage.setScene(scene);
        stage.setMinWidth(650);
        stage.setMinHeight(350);
        stage.show();
    }

    /**
     * Closes the stages after the test
     */
    @Stop
    public void stop() {
        stage.close();
        mainStage.close();
    }

    /**
     * Test if view is correctly initialized
     *
     * @param robot - FxRobot instance
     */
    @Test
    void initialize(FxRobot robot) {
        var childs = (ListView) ((GridPane) scene.lookup("#leftSide")).getChildren().get(0);
        var pattern = Configuration.getPatterns().stream().map(ContactPattern::toString).collect(Collectors.toList());
        childs.getItems().stream().map(e -> ((CustomListCell) e).getValue())
                .forEach(val -> assertTrue(pattern.contains(val)));
        assertEquals(pattern.size(), childs.getItems().size());
    }

    /**
     * Test if the add button opens the correct window
     *
     * @param robot - FxRobot instance
     */
    @Test
    void buttonOpensAddWindow(FxRobot robot) {
        robot.clickOn(scene.lookup("#addPopupButton"));
        var stage = TestUtil.getTopModalStage(robot);
        assertNotNull(stage);
        assertEquals("Anrede hinzufügen", stage.getTitle());
        assertTrue(stage.isFocused());
    }

    /**
     * Test if invalid leads to an error message.
     *
     * @param robot - FxRobot instance
     * @throws InterruptedException
     */
    @Test
    void buttonAddsSalutationPattern(FxRobot robot) throws InterruptedException {
        robot.clickOn(scene.lookup("#addPopupButton"));
        var stage = TestUtil.getTopModalStage(robot);
        var button = stage.getScene().lookup("#addButton");
        assertTrue(button.isDisabled());
        var patter = Configuration.getPatterns().get(0);
        var childs = (TitledPane) ((GridPane) stage.getScene().lookup("#topBox")).getChildren().get(0);
        var inputField = (GridPane) (childs.getContent());
        Node input = inputField.getChildren().get(4);
        var input2 = (ChoiceBox) inputField.getChildren().get(5);
        Node input3 = inputField.getChildren().get(6);
        Node input4 = inputField.getChildren().get(7);

        robot.clickOn(input);
        robot.write(patter.getInputPattern());
        input2.getSelectionModel().select(patter.getLanguage());
        robot.clickOn(input3);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn(input4);
        robot.write(patter.getOutputPattern());

        robot.clickOn(button);
        TestUtil.alert_dialog_has_header_and_content("Meldung", "Ein solches Pattern existiert bereits", robot);
    }

    /**
     * Test if pattern gets added to the view after saving
     *
     * @param robot - FxRobot instance
     */
    @Test
    void savedSuccesfully(FxRobot robot) {
        var pattern = Configuration.getPatterns().size();
        robot.clickOn(scene.lookup("#addPopupButton"));
        var stage = TestUtil.getTopModalStage(robot);
        var button = stage.getScene().lookup("#addButton");
        assertTrue(button.isDisabled());
        var patter = Configuration.getPatterns().get(0);
        var childs = (TitledPane) ((GridPane) stage.getScene().lookup("#topBox")).getChildren().get(0);
        var inputField = (GridPane) (childs.getContent());
        Node input = inputField.getChildren().get(4);
        var input2 = (ChoiceBox) inputField.getChildren().get(5);
        Node input3 = inputField.getChildren().get(6);
        Node input4 = inputField.getChildren().get(7);

        robot.clickOn(input);
        robot.write(patter.getInputPattern());
        input2.getSelectionModel().select(patter.getLanguage());
        robot.clickOn(input3);
        robot.type(KeyCode.ENTER);
        robot.clickOn(input4);
        robot.write(patter.getOutputPattern() + "Test123");

        robot.clickOn(button);
        var window = TestUtil.getTopWindowModal(robot);
        var listView = (ListView) ((GridPane) scene.lookup("#leftSide")).getChildren().get(0);
        assertEquals(listView.getItems().size(), pattern + 1);
    }
}