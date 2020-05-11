package de.dhbw.kontaktsplitter.ui.components;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * A component that displays a pattern in detail and offers editing
 *
 * @author Daniel Bornbaum
 */
public class PatternDetailView
{
    private TitledPane pane;
    private Consumer<Boolean> validConsumer;
    private ContactPattern pattern = new ContactPattern(Locale.getDefault().getDisplayLanguage(), Gender.NONE,
                                                        "", "");

    TextField inputPatternTextField = new TextField();
    ChoiceBox<String> languageBox = new ChoiceBox<>();
    ChoiceBox<String> genderBox = new ChoiceBox<>();
    TextField outputPatternTextField = new TextField();

    /**
     * Creates the ui
     */
    public PatternDetailView()
    {
        languageBox.setItems(FXCollections.observableList(Configuration.getLanguages()));
        genderBox.setItems(
                FXCollections.observableList(Arrays.asList("männlich", "weiblich", "divers", "keine Angabe")));

        setPattern(new ContactPattern(Locale.getDefault().getDisplayLanguage(), Gender.NONE, "",
                                      ""));

        pane = new TitledPane();
        pane.setCollapsible(false);
        pane.setText("Muster-Detailansicht");

        GridPane gridPane = new GridPane();
        GridPane.setHgrow(gridPane, Priority.ALWAYS);

        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label inputPatternLabel = new Label("Eingabe-Muster:");
        Label languageLabel = new Label("Sprache:");
        Label genderLabel = new Label("Geschlecht:");
        Label outputLabel = new Label("Ausgabe");

        gridPane.add(inputPatternLabel, 0, 0);
        gridPane.add(languageLabel, 0, 1);
        gridPane.add(genderLabel, 0, 2);
        gridPane.add(outputLabel, 0, 3);

        gridPane.add(inputPatternTextField, 1, 0);
        gridPane.add(languageBox, 1, 1);
        gridPane.add(genderBox, 1, 2);
        gridPane.add(outputPatternTextField, 1, 3);

        inputPatternTextField.setOnKeyTyped(actionEvent -> {
            pattern.setInputPattern(inputPatternTextField.getText());
            validate();
        });

        outputPatternTextField.setOnKeyTyped(keyEvent -> {
            pattern.setOutputPattern(outputPatternTextField.getText());
            validate();
        });

        languageBox.setOnAction(actionEvent -> pattern.setLanguage(languageBox.getValue()));

        genderBox.setOnAction(actionEvent -> pattern.setGender(Gender.fromDisplayString(genderBox.getValue())));

        pane.setContent(gridPane);
    }

    /**
     * Sets additional behaviour on input validation
     * @param validConsumer consumer that accepts whether the input is valid
     */
    public void setOnValidate(Consumer<Boolean> validConsumer)
    {
        this.validConsumer = validConsumer;
    }

    /**
     * @return TitledPane from JavaFx
     */
    public TitledPane getPane()
    {
        return pane;
    }

    /**
     * Sets the pattern to be displayed inside this detail view
     *
     * @param pattern
     */
    public void setPattern(ContactPattern pattern)
    {
        this.pattern = pattern;
        inputPatternTextField.setText(pattern.getInputPattern());
        languageBox.setValue(pattern.getLanguage());
        genderBox.setValue(pattern.getGender().toDisplayString());
        outputPatternTextField.setText(pattern.getOutputPattern());
        validate();
    }

    /**
     * @return possibly edited pattern from this detail view
     */
    public ContactPattern getPattern()
    {
        return pattern;
    }

    /**
     * @return whether the given input is valid
     */
    private boolean validate()
    {
        boolean valid = !"".equals(inputPatternTextField.getText()) && !"".equals(outputPatternTextField.getText());

        if (validConsumer != null)
        {
            validConsumer.accept(valid);
        }

        return valid;
    }
}
