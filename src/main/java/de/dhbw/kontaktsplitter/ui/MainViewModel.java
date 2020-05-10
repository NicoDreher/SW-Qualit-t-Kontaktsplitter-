package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Contact;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_salutation.setText("");
        cmb_gender.setItems(FXCollections.observableList(Arrays.asList(Gender.values())));
        cmb_language.setItems(FXCollections.observableList(Configuration.getLanguages()));
        cmb_title.getItems().addAll(FXCollections.observableList(Configuration.getTitles()));
        cmb_title.getCheckModel().getCheckedItems().addListener((ListChangeListener<Title>) change -> manuallyChangedTitle());
    }

    public void split(ActionEvent actionEvent)
    {
        if(contact.getFirstName() == null || contact.getFirstName().isEmpty() || contact.getFirstName().isBlank())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte tragen Sie den Vornamen manuell ein.");
            alert.setHeaderText("Vorname nicht erkannt");
            alert.showAndWait();
        }
        else if(contact.getLastName() == null || contact.getLastName().isEmpty() || contact.getLastName().isBlank())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte tragen Sie den Nachnamen manuell ein.");
            alert.setHeaderText("Nachname nicht erkannt");
            alert.showAndWait();
        }
        else if(contact.getLanguage() == null ||contact.getLanguage().isBlank() || contact.getLanguage().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte tragen Sie die Sprache manuell ein.");
            alert.setHeaderText("Sprache nicht erkannt");
            alert.showAndWait();
        }
        else
            lbl_salutation.setText(InputParser.generateOutput(contact));
    }

    public void manuallyChanged_Gender()
    {
        contact.setGender(cmb_gender.getSelectionModel().getSelectedItem());
    }

    public void manuallyChangedTitle()
    {
        contact.setTitles(cmb_title.getCheckModel().getCheckedItems());
    }

    public void manuallyChangedFirstName()
    {
        contact.setFirstName(txt_firstName.getText());
    }

    public void manuallyChangedLastName()
    {
        contact.setLastName(txt_surname.getText());
    }

    public void manuallyChangedLanguage()
    {
        contact.setLanguage(cmb_language.getSelectionModel().getSelectedItem());
    }

    public void duplicate(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "CRM-System nicht verknüpft.");
        alert.setHeaderText("Keine Verbindung möglich");
        alert.showAndWait();
    }

    public void addTitle(ActionEvent actionEvent)
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/title_editor.fxml"));
            TitleEditorViewModel viewModel = new TitleEditorViewModel();
            fxmlLoader.setController(viewModel);

            var root = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Titel hinzufügen");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gridPane.getScene().getWindow());
            stage.show();

            viewModel.updateElements(Configuration.getTitles().stream().map(Title::getTitle).collect(
                    Collectors.toList()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Cannot load new window");
        }
    }

    public void addSalutation(ActionEvent actionEvent)
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pattern_editor.fxml"));

            var root = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Anredenmuster hinzufügen");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gridPane.getScene().getWindow());
            stage.show();

            ((PatternEditorViewModel) fxmlLoader.getController()).updateElements(Configuration.getPatterns());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Cannot load new window");
        }
    }

    public void changedSalutation()
    {
        clear();
        contact = InputParser.parseInput(txt_salutation.getText());
        if(contact != null)
        {
            txt_firstName.clear();
            txt_surname.clear();
            cmb_language.getSelectionModel().clearSelection();
            txt_firstName.setText(contact.getFirstName());
            txt_surname.setText(contact.getLastName());
            cmb_gender.getSelectionModel().clearAndSelect(contact.getGender().ordinal());
            cmb_language.getSelectionModel().select(contact.getLanguage());
            contact.getTitles().forEach(e -> cmb_title.getCheckModel().check(e));
        }
    }

    private void clear()
    {
        cmb_title.getCheckModel().clearChecks();
    }
}
