package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.components.ElementEditor;
import de.dhbw.kontaktsplitter.ui.components.PatternDetailView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Daniel Bornbaum
 */
public class PatternEditorViewModel implements Initializable
{
    @FXML
    private GridPane leftSide;

    @FXML
    private GridPane rightSide;

    @FXML
    private Button addPopupButton;

    private HashMap<String, ContactPattern> patterns = new HashMap<>();
    private String currentPatternKey;
    private PatternDetailView view;

    private ElementEditor editor = new ElementEditor();

    /**
     * Overwrites the initialize method from Initializable, sets ui handlers, extends ui from fxml
     * @param url see package javafx.fxml.Initializable
     * @param resourceBundle see package javafx.fxml.Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        view = new PatternDetailView();
        view.getPane().setDisable(true);
        rightSide.getChildren().setAll(view.getPane());

        leftSide.getChildren().setAll(editor.getElementsView());
        editor.setSelectionHandler(this::selectElement);

        updateElements(Configuration.getPatterns());
        addPopupButton.setOnMouseClicked(event -> addElementPopup(false));

        editor.setOnDelete(element -> patterns.remove(element));
    }

    /**
     * Updates the elements inside the pattern editor
     * @param patternList list of ContactPatterns to set for this element
     */
    public void updateElements(List<ContactPattern> patternList)
    {
        editor.updateElements(patternList.stream().map(ContactPattern::toString).collect(
                Collectors.toList()));
        patterns.clear();
        patternList.forEach(contactPattern -> patterns.put(contactPattern.toString(), contactPattern));

        if (patternList.size() > 0)
        {
            selectElement(patternList.get(0).toString());
        }

        editor.overwriteElementsEditCommand(element -> {
            currentPatternKey = element;
            addElementPopup(true);
            editor.select(element);
        });
    }

    /**
     * @return potentially edited patterns for this element
     */
    public List<ContactPattern> getPatterns(){
        List<ContactPattern> outputPatterns = new ArrayList<>();

        for (String element : editor.getElements()){
            outputPatterns.add(patterns.get(element));
        }

        return outputPatterns;
    }

    /**
     * Selects a given element by its title
     * @param selected pattern title to select
     */
    private void selectElement(String selected)
    {
        currentPatternKey = selected;
        if (selected != null)
        {
            view.setPattern(patterns.get(selected));
        }
    }

    /**
     * Creates popup to edit or create a new pattern
     * @param edit, whether the current pattern should be added or if a pattern is edited
     */
    private void addElementPopup(boolean edit){

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/add_pattern_popup.fxml"));
        Scene scene;
        try
        {
            scene = new Scene(loader.load());
        }
        catch (IOException e)
        {
            new Alert(Alert.AlertType.ERROR,
                      "Ein Fehler ist aufgetreten. Das Element der Benutzeroberfläche konnte nicht geladen werden.",
                      ButtonType.OK);
            return;
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(edit ? "Anrede bearbeiten" : "Anrede hinzufügen");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        AddPatternPopupViewModel popupViewModel = loader.getController();

        if (edit)
        {
            popupViewModel.setPattern(patterns.get(currentPatternKey));
            popupViewModel.setAddButtonText("Speichern");
        }
        popupViewModel.setSubmitCommand(contactPattern -> {

            if (edit){
                patterns.remove(currentPatternKey);
            }

            if (patterns.containsKey(contactPattern.toString()))
            {
                new Alert(Alert.AlertType.INFORMATION, "Ein solches Pattern existiert bereits").show();
                return;
            }

            patterns.put(contactPattern.toString(), contactPattern);

            if (!edit)
            {
                editor.addListElement(contactPattern.toString());
            } else {
                editor.replaceListElement(currentPatternKey, contactPattern.toString());
            }
            stage.close();
        });
    }
}
