package de.dhbw.kontaktsplitter.test.ui;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.Startup;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.CheckComboBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.ComboBoxMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.MenuItemMatchers;
import org.testfx.robot.Motion;
import org.w3c.dom.Text;

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

    /**
     * Initialize a new stage
     * @param stage - instance of Stage
     * @throws Exception
     */
    @Start
    public void start(Stage stage) throws Exception {
        new Startup().start(stage);
        this.stage = stage;
    }

    /**
     * Closes a stage after a test finished
     */
    @Stop
    public void stop()
    {
        stage.close();
    }

    /**
     * Test for the correct initialization of the main window.
     * Checks if the combo boxes and the label of the salutation is removed.
     * @param robot - FxRobot instance
     */
    @Test
    void initializeUI_correct_setup(FxRobot robot) {
        FxAssert.verifyThat("#lbl_salutation", LabeledMatchers.hasText(""));
        FxAssert.verifyThat("#cmb_gender", ComboBoxMatchers.containsItems(Gender.values()));
        FxAssert.verifyThat("#cmb_language", ComboBoxMatchers.containsExactlyItems(Configuration.getPatterns().stream().map(ContactPattern::getLanguage).distinct().toArray()));
        var checkComboBox = (CheckComboBox)stage.getScene().lookup("#cmb_title");
        var titles = Configuration.getTitles();
        titles.forEach(e -> assertTrue(checkComboBox.getItems().contains(e)));
    }

    /**
     * Test for opening and closing the check duplicate interface
     * @param robot - FxRobot instance
     */
    @Test
    void btnDuplicate_opensAndClosesInfoWindow(FxRobot robot) {
        robot.clickOn(stage.getScene().lookup("#btn_duplicate"), Motion.DIRECT, MouseButton.PRIMARY);
        alert_dialog_has_header_and_content("Keine Verbindung möglich", "CRM-System nicht verknüpft.", robot);
    }

    /**
     * Test for opening and closing the add title dialog
     * @param robot - FxRobot instance
     */
    @Test
    void menuItem_openAndCloseAddTitle(FxRobot robot){
        robot.clickOn(stage.getScene().lookup("#menu"), Motion.DIRECT, MouseButton.PRIMARY);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        var actualStage = getTopWindowModal(robot);
        assertNotNull(actualStage);
        assertTrue(actualStage.getTitle().equalsIgnoreCase("Titel hinzufügen"));
        assertTrue(actualStage.isFocused());
    }

    /**
     * Test if menu item for adding a pattern open and closes correctly
     * @param robot - FxRobot instance
     */
    @Test
    void menuItem_openAndCloseAddPattern(FxRobot robot)
    {
        robot.clickOn(stage.getScene().lookup("#menu"), Motion.DIRECT, MouseButton.PRIMARY);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        var actualStage = getTopWindowModal(robot);
        assertNotNull(actualStage);
        assertTrue(actualStage.getTitle().equalsIgnoreCase("Anredenmuster hinzufügen"));
        assertTrue(actualStage.isFocused());;
    }

    /**
     * Test if a missing first name displays an error message
     * @param robot - FxRobot instance
     */
    @Test
    void missingFirstName_opensErrorsMessage(FxRobot robot)
    {
        var scene = stage.getScene();
        robot.clickOn(scene.lookup("#txt_surname"));
        robot.write("Mustermann");
        robot.clickOn("#cmb_language");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#cmb_gender");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn(scene.lookup("#btn_split"));
        alert_dialog_has_header_and_content("Vorname nicht erkannt", "Bitte tragen Sie den Vornamen manuell ein.", robot);
    }

    /**
     * Test if missing last name displays an error message.
     * @param robot - FxRobot instance
     */
    @Test
    void missingLastName_opensErrorsMessage(FxRobot robot)
    {
        var scene = stage.getScene();
        robot.clickOn(scene.lookup("#txt_firstName"));
        robot.write("Max");
        robot.clickOn("#cmb_language");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#cmb_gender");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn(scene.lookup("#btn_split"));
        alert_dialog_has_header_and_content("Nachname nicht erkannt", "Bitte tragen Sie den Nachnamen manuell ein.", robot);
    }

    /**
     * Test if missing Language opens a Error Message
     * @param robot - FxRobot instance
     */
    @Test
    void missingLanguage_opensErrorMessage(FxRobot robot)
    {
        var scene = stage.getScene();
        robot.clickOn(scene.lookup("#txt_firstName"));
        robot.write("Max");
        robot.clickOn(scene.lookup("#txt_surname"));
        robot.write("Mustermann");
        robot.clickOn("#cmb_gender");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn(scene.lookup("#btn_split"));
        alert_dialog_has_header_and_content("Sprache nicht erkannt", "Bitte tragen Sie die Sprache manuell ein.", robot);
    }

    /**
     * Test if a given input provides the correct salutation
     * @param input - input
     * @param expected - salutation
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/ui/correctSalutation.csv")
    void correctSalutation(String input, String expected)
    {
        FxRobot robot = new FxRobot();
        robot.clickOn(stage.getScene().lookup("#txt_salutation"));
        robot.write(input);
        robot.clickOn(stage.getScene().lookup("#btn_split"));
        assertEquals(expected, ((Label)stage.getScene().lookup("#lbl_salutation")).getText());
    }



    /**
     * Test if a given salutation fills the correct values into the form.
     * @param input - salutation
     * @param firstName - expected firstname
     * @param surName - expected surname
     * @param gender - expected gender
     * @param titles - expected list of titles
     * @param language - expected language
     * @throws InterruptedException
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/ui/input.csv")
    void differentParameters(String input, String firstName, String surName, String gender, String titles, String language) throws InterruptedException {
        FxRobot robot = new FxRobot();
        robot.clickOn(stage.getScene().lookup("#txt_salutation"));
        robot.write(input);
        assertEquals(firstName, ((TextField) (stage.getScene().lookup("#txt_firstName"))).getText());
        assertEquals(surName, ((TextField) (stage.getScene().lookup("#txt_surname"))).getText());
        FxAssert.verifyThat("#cmb_gender", ComboBoxMatchers.hasSelectedItem(Gender.valueOf(gender)));
        FxAssert.verifyThat("#cmb_language", ComboBoxMatchers.hasSelectedItem(language));
        ObservableList resultList = ((CheckComboBox)stage.getScene().lookup("#cmb_title")).getCheckModel().getCheckedItems();
        assertEquals(titles != null ? titles.split(";").length : 0,resultList.size());
    }

    /**
     * Helper to check if the current displayed message is correct
     * @param expectedHeader - expected header of the message
     * @param expectedContent - expected content of the message
     * @param robot - FxRobot instance
     */
    private void alert_dialog_has_header_and_content(final String expectedHeader, final String expectedContent, FxRobot robot) {
        final Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(expectedHeader, dialogPane.getHeaderText());
        assertEquals(expectedContent, dialogPane.getContentText());
        assertTrue(actualAlertDialog.isFocused());
        robot.type(KeyCode.ESCAPE);
        assertFalse(actualAlertDialog.isFocused());
    }

    /**
     * Returns the stage for error messages.
     * @param robot - FxRobot instance
     * @return stage of current displayed error message
     */
    private Stage getTopModalStage(FxRobot robot) {
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
     * @param robot - FxRobot instance
     * @return returns stage of the current displayed window
     */
    private Stage getTopWindowModal(FxRobot robot) {
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
