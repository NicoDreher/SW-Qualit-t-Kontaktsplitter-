package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.parser.InputParser;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * ViewModel of the Main Window.
 * Implements all controls and communicates with the model and logic classes.
 *
 * @author Lukas Lautenschlager
 */
public class MainViewModel implements Initializable {

    @FXML
    GridPane gridPane;

    @FXML
    Label lbl_salutation;

    @FXML
    ComboBox<Gender> cmb_gender;

    @FXML
    ComboBox<String> cmb_language;

    @FXML
    CheckComboBox<Title> cmb_title = new CheckComboBox<>();

    @FXML
    TextField txt_salutation;

    @FXML
    TextField txt_firstName;

    @FXML
    TextField txt_surname;

    private Contact contact = new Contact();

    /**
     * Initializier of the ViewModel
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_salutation.setText("");
        updateTitles();
        updateLanguages();
        cmb_gender.setItems(FXCollections.observableList(Arrays.asList(Gender.values())));
        cmb_title.getCheckModel().getCheckedItems()
                .addListener((ListChangeListener<Title>) change -> manuallyChangedTitle());
    }

    /**
     * Splits the given input to the form and checks for any errors.
     *
     * @param actionEvent
     */
    public void split(ActionEvent actionEvent) {
        if(contact.getFirstName() == null || contact.getFirstName().isEmpty() || contact.getFirstName().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte tragen Sie den Vornamen manuell ein.");
            alert.setHeaderText("Vorname nicht erkannt");
            alert.showAndWait();
        }
        else if(contact.getLastName() == null || contact.getLastName().isEmpty() || contact.getLastName().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte tragen Sie den Nachnamen manuell ein.");
            alert.setHeaderText("Nachname nicht erkannt");
            alert.showAndWait();
        }
        else if(contact.getLanguage() == null || contact.getLanguage().isBlank() || contact.getLanguage().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte tragen Sie die Sprache manuell ein.");
            alert.setHeaderText("Sprache nicht erkannt");
            alert.showAndWait();
        }
        else {
            String output = InputParser.generateOutput(contact);
            if(!output.isEmpty()) {
                lbl_salutation.setText(output);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Für diese Sprache und das Geschlecht ist keine Ausgabe definiert. \nDas kann in den Einstellungen -> Anrede Hinzufügen gemacht werden");
                alert.setHeaderText("Ausgabe nicht definiert");
                alert.showAndWait();
            }
        }
    }

    /**
     * Method binding to the ChangedListener of the Gender combo box
     */
    public void manuallyChanged_Gender() {
        contact.setGender(cmb_gender.getSelectionModel().getSelectedItem());
    }

    /**
     * Method binding to the ChangedListener of the Title CheckCombobox
     */
    public void manuallyChangedTitle() {
        contact.setTitles(cmb_title.getCheckModel().getCheckedItems());
    }

    /**
     * Method binding to the ChangedListener of the first name text field
     */
    public void manuallyChangedFirstName() {
        contact.setFirstName(txt_firstName.getText());
    }

    /**
     * Method binding to the ChangedListener of the last name text field
     */
    public void manuallyChangedLastName() {
        contact.setLastName(txt_surname.getText());
    }

    /**
     * Method binding to the ChangedListener of the language combo box
     */
    public void manuallyChangedLanguage() {
        contact.setLanguage(cmb_language.getSelectionModel().getSelectedItem());
    }

    /**
     * Method binding to provide a interface to the CRM-system.
     * Currently not implemented and only shows a information message.
     */
    public void duplicate() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "CRM-System nicht verknüpft.");
        alert.setHeaderText("Keine Verbindung möglich");
        alert.showAndWait();
    }

    /**
     * Method binding to the menu item to add titles to the application.
     * Shows a dialog to add and remove titles.
     *
     * @param actionEvent
     */
    public void addTitle(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/title_editor.fxml"));

            var root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Titel verwalten");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gridPane.getScene().getWindow());
            if(root instanceof Region) {
                Region region = (Region) root;
                if(region.getMinHeight() > 0) {
                    stage.setMinHeight(region.getMinHeight());
                }
                if(region.getMaxHeight() > 0) {
                    stage.setMaxHeight(region.getMaxHeight());
                }
                if(region.getMinWidth() > 0) {
                    stage.setMinWidth(region.getMinWidth());
                }
                if(region.getMaxWidth() > 0) {
                    stage.setMaxWidth(region.getMaxWidth());
                }
            }
            stage.setOnCloseRequest(event -> {
                Configuration.setTitles(((TitleEditorViewModel) fxmlLoader.getController()).getTitles());
                Configuration.saveConfig();
                updateTitles();
            });

            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("Cannot load new window");
        }
    }

    /**
     * Method binding to the menu item to add salutation patterns.
     * Opens a new dialog to configure and delete patterns.
     *
     * @param actionEvent
     */
    public void addSalutation(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pattern_editor.fxml"));

            var root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Anreden verwalten");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gridPane.getScene().getWindow());
            if(root instanceof Region) {
                Region region = (Region) root;
                if(region.getMinHeight() > 0) {
                    stage.setMinHeight(region.getMinHeight());
                }
                if(region.getMaxHeight() > 0) {
                    stage.setMaxHeight(region.getMaxHeight());
                }
                if(region.getMinWidth() > 0) {
                    stage.setMinWidth(region.getMinWidth());
                }
                if(region.getMaxWidth() > 0) {
                    stage.setMaxWidth(region.getMaxWidth());
                }
            }
            stage.setOnCloseRequest(event -> {
                Configuration.setPatterns(((PatternEditorViewModel) fxmlLoader.getController()).getPatterns());
                Configuration.saveConfig();
            });

            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("Cannot load new window");
        }
    }

    /**
     * Method binding to the ChangedListener of the input field.
     * Actualizes the user interface on the fly
     */
    public void changedSalutation() {
        clear();
        contact = InputParser.parseInput(txt_salutation.getText());
        txt_firstName.clear();
        txt_surname.clear();
        txt_firstName.setText(contact.getFirstName());
        txt_surname.setText(contact.getLastName());
        cmb_gender.getSelectionModel().clearAndSelect(contact.getGender().ordinal());
        cmb_language.getSelectionModel().select(contact.getLanguage());
        contact.getTitles().forEach(e -> cmb_title.getCheckModel().check(e));
    }

    /**
     * Helper method to clear the CheckComboBox to ensure the function of the user interface
     */
    private void clear() {
        cmb_title.getCheckModel().clearChecks();
        cmb_language.getSelectionModel().clearSelection();
    }

    private void updateTitles() {
        cmb_title.getItems().removeIf(e -> true);
        cmb_title.getItems().addAll(FXCollections.observableList(Configuration.getTitles()));
    }

    private void updateLanguages() {
        cmb_language.setItems(FXCollections.observableList(
                Configuration.getPatterns().stream().map(ContactPattern::getLanguage).distinct().sorted().collect(
                        Collectors.toList())));
    }
}
