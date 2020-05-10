package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PatternEditorViewModel implements Initializable
{
    @FXML
    private GridPane leftPane;

    private PatternTitleEditorViewModel titlesViewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            FXMLLoader titleEditorLoader = new FXMLLoader(getClass().getResource("/title_editor.fxml"));
            titlesViewModel = new PatternTitleEditorViewModel();
            titleEditorLoader.setController(titlesViewModel);

            leftPane.getChildren().setAll((GridPane) titleEditorLoader.load());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                      "Ein Fehler ist aufgetreten, der Editor konnte nicht geladen werden",
                      ButtonType.CLOSE).show();
        }
    }

    public void updateElements(List<ContactPattern> patternList){
        this.titlesViewModel.setEditable(false);
        this.titlesViewModel.updateElements(patternList.stream().map(ContactPattern::getInputPattern).collect(
                Collectors.toList()));
    }
}
