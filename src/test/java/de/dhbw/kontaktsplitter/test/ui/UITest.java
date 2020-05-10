package de.dhbw.kontaktsplitter.test.ui;

import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.Startup;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ComboBoxMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.MenuItemMatchers;
import org.testfx.robot.Motion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class UITest {
    private Stage stage;

    @BeforeAll
    public static void before() {
        if ("true".equalsIgnoreCase(System.getenv("headless"))) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Start
    public void start(Stage stage) throws Exception {
        new Startup().start(stage);
        this.stage = stage;
    }

    @Test
    public void test(FxRobot robot) throws InterruptedException {
        assertTrue(true);
    }

    /**
     * Test for the correct initialization of the main window.
     * Checks if the combo boxes and the label of the salutation is removed.
     *
     * @param robot
     */
    @Test
    void initializeUI_correct_setup(FxRobot robot) {
        FxAssert.verifyThat("#lbl_salutation", LabeledMatchers.hasText(""));
        FxAssert.verifyThat("#cmb_gender", ComboBoxMatchers.containsItems(Gender.values()));
        FxAssert.verifyThat("#cmb_title", ComboBoxMatchers.containsItems(Configuration.getTitles().toArray()));
        FxAssert.verifyThat("#cmb_language", ComboBoxMatchers.containsExactlyItems(Configuration.getLanguages().toArray()));
    }

    /**
     * Test for opening the adding title window
     */
    @Test
    void btnDuplicate_opensInfoWindow(FxRobot robot) {
        robot.clickOn(stage.getScene().lookup("#btn_duplicate"), Motion.DIRECT, MouseButton.PRIMARY);
        alert_dialog_has_header_and_content("Keine Verbindung möglich", "CRM-System nicht verknüpft.", robot);
    }

    /**
     *
     */
    @Test
    void menuItem_addTitle(FxRobot robot) throws InterruptedException {
        robot.clickOn(stage.getScene().lookup("#menu"), Motion.DIRECT, MouseButton.PRIMARY);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
    }

    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/ui/input.csv")
    void differentParameters(String input, String firstName, String surName, String gender, String titles, String language) throws InterruptedException {
        FxRobot robot = new FxRobot();
        robot.clickOn(stage.getScene().lookup("#txt_salutation"));
        var charArray = input.toUpperCase().toCharArray();
        for (char ch : charArray) {
            Thread.sleep(150);
            if (ch == ' ')
                robot.type(KeyCode.SPACE);
            else if (ch == '.')
                robot.type(KeyCode.STOP);
            else
                robot.type(KeyCode.getKeyCode(String.valueOf(ch)));
        }
        assertEquals(firstName, stage.getScene().lookup("#txt_firstName").getAccessibleText());
    }

    public void alert_dialog_has_header_and_content(final String expectedHeader, final String expectedContent, FxRobot robot) {
        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(expectedHeader, dialogPane.getHeaderText());
        assertEquals(expectedContent, dialogPane.getContentText());
        actualAlertDialog.close();
    }

    private javafx.stage.Stage getTopModalStage(FxRobot robot) {
        // Get a list of windows but ordered from top[0] to bottom[n] ones.
        // It is needed to get the first found modal window.
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (javafx.stage.Stage) allWindows
                .stream()
                .filter(window -> window instanceof javafx.stage.Stage)
                .filter(window -> ((javafx.stage.Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }
}
