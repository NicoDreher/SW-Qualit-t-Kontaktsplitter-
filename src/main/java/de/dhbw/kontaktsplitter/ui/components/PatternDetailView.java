package de.dhbw.kontaktsplitter.ui.components;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;

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

    public PatternDetailView(){

        languageBox.setItems(FXCollections.observableList(Configuration.getLanguages()));
        genderBox.setItems(FXCollections.observableList(Arrays.asList("männlich", "weiblich", "divers", "keine Angabe")));

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

        inputPatternTextField.setOnKeyPressed(keyEvent -> {
            if (!"".equals(inputPatternTextField.getText())){
                pattern.setInputPattern(inputPatternTextField.getText());
            }
            validate();
        });

        outputPatternTextField.setOnKeyPressed(keyEvent -> {
            if (!"".equals(outputPatternTextField.getText())){
                pattern.setOutputPattern(outputPatternTextField.getText());
            }
            validate();
        });

        languageBox.setOnAction(actionEvent -> pattern.setLanguage(languageBox.getValue()));

        genderBox.setOnAction(actionEvent -> pattern.setGender(genderFromDisplayText(genderBox.getValue())));

        pane.setContent(gridPane);
    }

    public void setOnValidate(Consumer<Boolean> valid){
        validConsumer = valid;
    }

    public TitledPane getPane(){
        return pane;
    }

    public void setPattern(ContactPattern pattern){
        inputPatternTextField.setText(pattern.getInputPattern());
        languageBox.setValue(pattern.getLanguage());
        genderBox.setValue(displayTextFromGender(pattern.getGender()));
        outputPatternTextField.setText(pattern.getOutputPattern());
        validate();
    }

    public ContactPattern getPattern(){
        return pattern;
    }

    private String displayTextFromGender(Gender gender){
        switch (gender){
            case MALE:
                return "männlich";
            case FEMALE:
                return "weiblich";
            case DIVERS:
                return "divers";
            default:
                return "keine Angabe";
        }
    }

    private Gender genderFromDisplayText(String genderText){
        switch (genderText){
            case "männlich":
                return Gender.MALE;
            case "weiblich":
                return Gender.FEMALE;
            case "divers":
                return Gender.DIVERS;
            default:
                return Gender.NONE;
        }
    }

    private boolean validate(){
        boolean valid = !"".equals(inputPatternTextField.getText()) && !"".equals(outputPatternTextField.getText());

        if (validConsumer != null){
            validConsumer.accept(valid);
        }

        return valid;
    }
}
