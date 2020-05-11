package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.ui.components.PatternDetailView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AddPatternPopupViewModel implements Initializable
{
    @FXML
    private Button addButton;

    @FXML
    private GridPane topBox;

    private PatternDetailView detailView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        detailView = new PatternDetailView();
        detailView.setOnValidate(valid -> addButton.setDisable(!valid));
        addButton.setDisable(true);
        topBox.add(detailView.getPane(), 0, 0);
    }

    public void setPattern(ContactPattern pattern){
        detailView.setPattern(pattern);
    }

    public void setSubmitCommand(Consumer<ContactPattern> onSubmitConsumer){
        addButton.setOnMouseClicked(event -> onSubmitConsumer.accept(detailView.getPattern()));
    }

    public void setAddButtonText(String text)
    {
        addButton.setText(text);
    }
}
