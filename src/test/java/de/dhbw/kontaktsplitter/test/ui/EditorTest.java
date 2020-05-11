package de.dhbw.kontaktsplitter.test.ui;

import de.dhbw.kontaktsplitter.ui.Startup;
import de.dhbw.kontaktsplitter.ui.components.CustomListCell;
import de.dhbw.kontaktsplitter.ui.components.ElementEditor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Bornbaum
 */
@ExtendWith(ApplicationExtension.class)
public class EditorTest {
    private Stage stage;
    private ElementEditor editor;
    private List<String> expectedElements = new ArrayList<>(
            Arrays.asList("Element1", "Element2", "Element3", "Element4", "Element5"));

    /**
     * Sets System Properties before all tests
     */
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
     * Initialize a new stage
     *
     * @param stage - instance of Stage
     * @throws Exception
     */
    @Start
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        new Startup().start(stage);

        editor = new ElementEditor();
        editor.updateElements(expectedElements);
        stage.setScene(new Scene(editor.getElementsView()));
        this.stage.show();
    }

    /**
     * Tests whether the Selection inside the ElementEditor component works properly
     */
    @Test
    public void elementEditorSelectTest() {
        editor.select(expectedElements.get(1));

        for(String expectedElement : expectedElements) {
            boolean found = false;

            for(CustomListCell item : editor.getElementsView().getItems()) {
                TextField field = (TextField) item.getChildren().get(0);

                if(expectedElement.equals(field.getText())) {
                    found = true;
                }
            }

            assertTrue(found);

            assertEquals(expectedElements.get(1),
                    ((TextField) editor.getElementsView().getSelectionModel().getSelectedItem().getChildren()
                            .get(0)).getText());
        }
    }

    /**
     * Tests whether the remove button inside a list element works properly
     *
     * @param robot object that simulates a user
     */
    @Test
    public void removeButtonTest(FxRobot robot) {
        CustomListCell cell = editor.getElementsView().getItems().get(0);
        Button deleteButton = (Button) cell.getChildren().get(4);

        robot.clickOn(deleteButton);
        assertEquals(expectedElements.get(1),
                ((TextField) editor.getElementsView().getItems().get(0).getChildren().get(0))
                        .getText());
        expectedElements.remove(0);
    }

    /**
     * Tests that moving down an element in the list works properly
     *
     * @param robot object that simulates a user
     */
    @Test
    public void moveDownElementTest(FxRobot robot) {
        CustomListCell cell = editor.getElementsView().getItems().get(0);
        Button downButton = (Button) cell.getChildren().get(2);

        robot.clickOn(downButton);
        assertEquals(expectedElements.get(0),
                ((TextField) editor.getElementsView().getItems().get(1).getChildren().get(0))
                        .getText());

        String expectedElement = expectedElements.get(0);
        expectedElements.remove(0);
        expectedElements.add(1, expectedElement);
    }

    /**
     * Tests that moving up an element in the list works properly
     *
     * @param robot object that simulates a user
     */
    @Test
    public void moveUpElementTest(FxRobot robot) {
        CustomListCell cell = editor.getElementsView().getItems().get(1);
        Button upButton = (Button) cell.getChildren().get(1);

        robot.clickOn(upButton);
        assertEquals(expectedElements.get(1),
                ((TextField) editor.getElementsView().getItems().get(0).getChildren().get(0))
                        .getText());

        String expectedElement = expectedElements.get(1);
        expectedElements.remove(1);
        expectedElements.add(0, expectedElement);
    }

    /**
     * Tests that the edit button allows text input and when clicked again, the text is set
     *
     * @param robot object that simulates a user
     */
    @Test
    public void editButtonTest(FxRobot robot) {
        CustomListCell cell = editor.getElementsView().getItems().get(0);
        Button editButton = (Button) cell.getChildren().get(3);

        robot.clickOn(editButton);
        robot.type(KeyCode.A, 3);
        robot.clickOn(editButton);

        assertEquals("aaa",
                ((TextField) editor.getElementsView().getItems().get(0).getChildren().get(0))
                        .getText());
        expectedElements.remove(0);
        expectedElements.add(0, "aaa");
    }

    /**
     * Closes a stage after a test finished
     */
    @Stop
    public void stop() {
        stage.close();
    }
}
