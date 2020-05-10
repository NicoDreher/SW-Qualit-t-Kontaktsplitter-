package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Label lbl_salutation;

    @FXML
    ComboBox<Gender> cmb_gender;

    @FXML
    ComboBox<Title> cmb_title;

    @FXML
    ComboBox<String> cmb_language;

    @FXML
    TextField txt_salutation;

    @FXML
    TextField txt_firstName;

    @FXML
    TextField txt_surname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_salutation.setText("");
        cmb_gender.setItems(FXCollections.observableList(Arrays.asList(Gender.values())));
        cmb_title.setItems(FXCollections.observableList(Configuration.getTitles()));
        cmb_language.setItems(FXCollections.observableList(Configuration.getLanguages()));
    }

    public void split(ActionEvent actionEvent)
    {

    }

    public void duplicate(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "CRM-System nicht verknüpft.");
        alert.showAndWait().filter(response -> response == ButtonType.CLOSE)
                .ifPresent(response -> alert.close());
    }

    public void addTitle(ActionEvent actionEvent)
    {

    }

    public void addSalutation(ActionEvent actionEvent)
    {

    }
}
