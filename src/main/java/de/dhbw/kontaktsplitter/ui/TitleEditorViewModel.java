package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.components.CustomListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TitleEditorViewModel implements Initializable
{
    @FXML
    private ListView<CustomListCell> titlesView;

    @FXML
    private TextField newEntryField;

    @FXML
    private Button newEntryButton;

    private ObservableList<CustomListCell> observableTitles = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        newEntryButton.setOnAction(event -> this.addTitle());
        newEntryButton.setDisable(true);
        newEntryField.setOnKeyReleased(keyEvent -> {
            if ("".equals(newEntryField.getText()))
            {
                newEntryButton.setDisable(true);
            }
            else
            {
                newEntryButton.setDisable(false);
            }
        });
    }

    protected void updateElements(List<String> elements)
    {
        observableTitles.clear();

        for (String element : elements)
        {
            observableTitles.add(new CustomListCell(element, this::removeTitle, this::moveElementUp,
                                                    this::moveElementDown));
        }

        update();
    }

    private void addTitle()
    {
        String newProposedTitle = newEntryField.getText();

        if (!"".equals(newProposedTitle))
        {
            boolean titleExists = observableTitles.stream()
                    .map(CustomListCell::getValue)
                    .collect(Collectors.toList())
                    .contains(newProposedTitle);

            if (titleExists)
            {
                new Alert(Alert.AlertType.WARNING, "Dieser Titel existiert bereits").show();
                return;
            }
            ;

            observableTitles.add(new CustomListCell(newProposedTitle, this::removeTitle, this::moveElementUp,
                                                    this::moveElementDown));
            update();
        }
    }

    private void moveElementUp(CustomListCell cell)
    {
        int index = observableTitles.indexOf(cell);

        if (index != 0)
        {
            observableTitles.remove(cell);
            observableTitles.add(index - 1, cell);
        }
    }

    private void moveElementDown(CustomListCell cell)
    {
        int index = observableTitles.indexOf(cell);

        if (index != observableTitles.size() - 1)
        {
            observableTitles.remove(cell);
            observableTitles.add(index + 1, cell);
        }
    }

    private void removeTitle(CustomListCell cell)
    {
        observableTitles.remove(cell);
        update();
    }

    private void update()
    {
        titlesView.setItems(observableTitles);
    }
}
