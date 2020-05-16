package de.dhbw.kontaktsplitter.test.ui;

import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.testfx.api.FxRobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lukas Lautenschlager
 */
public class TestUtil {
    /**
     * Helper to check if the current displayed message is correct
     *
     * @param expectedHeader - expected header of the message
     * @param expectedContent - expected content of the message
     * @param robot - FxRobot instance
     */
    public static void alert_dialog_has_header_and_content(final String expectedHeader, final String expectedContent,
            FxRobot robot) {
        final Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final var dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(expectedHeader, dialogPane.getHeaderText());
        assertEquals(expectedContent, dialogPane.getContentText());
        assertTrue(actualAlertDialog.isShowing());
        robot.clickOn("OK");
        assertFalse(actualAlertDialog.isShowing());
    }

    /**
     * Returns the stage for error messages.
     *
     * @param robot - FxRobot instance
     * @return stage of current displayed error message
     */
    public static Stage getTopModalStage(FxRobot robot) {
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (Stage) allWindows
                .stream()
                .filter(window -> window instanceof Stage)
                .filter(window -> ((Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns stage of a new opened window
     *
     * @param robot - FxRobot instance
     * @return returns stage of the current displayed window
     */
    public static Stage getTopWindowModal(FxRobot robot) {
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (Stage) allWindows
                .stream()
                .filter(window -> window instanceof Stage)
                .filter(window -> ((Stage) window).getModality() == Modality.WINDOW_MODAL)
                .findFirst()
                .orElse(null);
    }
}
