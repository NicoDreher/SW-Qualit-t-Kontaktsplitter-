package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.components.ElementEditor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Daniel Bornbaum
 */
public class TitleEditorViewModel implements Initializable {
    private final ElementEditor editor = new ElementEditor();
    @FXML
    private VBox topBox;
    @FXML
    private TextField newEntryField;
    @FXML
    private Button newEntryButton;
    @FXML
    private ToggleButton helpButton;
    @FXML
    private GridPane helpSplitGrid;
    @FXML
    private ScrollPane helpScrollPane;
    private boolean helpVisible = true;

    /**
     * Overwrites the initialize method from Initializable, sets ui handlers, extends ui from fxml
     *
     * @param url see package javafx.fxml.Initializable
     * @param resourceBundle see package javafx.fxml.Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBox.getChildren().setAll(editor.getElementsView());
        editor.updateElements(Configuration.getTitles().stream().map(Title::getTitle).collect(Collectors.toList()));

        newEntryButton.setOnAction(event -> {
            editor.addListElement(newEntryField.getText());
            newEntryField.clear();
        });
        newEntryButton.setDisable(true);
        newEntryField.setOnKeyReleased(keyEvent -> {
            newEntryButton.setDisable("".equals(newEntryField.getText()));
        });

        toggleHelp();

        helpButton.setOnAction(event -> this.toggleHelp());
    }

    /**
     * @return potentially modified titles in this element
     */
    public List<Title> getTitles() {
        return editor.getElements().stream().map(Title::new).collect(Collectors.toList());
    }

    private void toggleHelp() {
        if(helpVisible) {
            helpSplitGrid.getChildren().remove(2);
            helpSplitGrid.getColumnConstraints().get(1).setMinWidth(0);
            helpSplitGrid.getColumnConstraints().get(1).setPrefWidth(0);
            helpButton.setTooltip(new Tooltip("Hilfe öffnen"));
        }
        else {
            helpSplitGrid.add(helpScrollPane, 1, 0, 1, 2);
            helpSplitGrid.getColumnConstraints().get(1).setMinWidth(100);
            helpSplitGrid.getColumnConstraints().get(1).setPrefWidth(200);
            helpButton.setTooltip(new Tooltip("Hilfe schließen"));
        }

        helpVisible = !helpVisible;
    }
}
