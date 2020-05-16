package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.parser.InputParser;
import de.dhbw.kontaktsplitter.ui.components.PatternDetailView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * View model for the popup window that is displayed when one presses the add pattern button
 *
 * @author Daniel Bornbaum
 */
public class AddPatternPopupViewModel implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private GridPane topBox;

    @FXML
    private Label unsupportedPlaceholderLabel;

    private PatternDetailView detailView;

    /**
     * Overwrites the initialize method from Initializable, sets ui handlers
     *
     * @param url see package javafx.fxml.Initializable
     * @param resourceBundle see package javafx.fxml.Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailView = new PatternDetailView();
        unsupportedPlaceholderLabel.setVisible(false);

        detailView.setOnValidate(valid -> {
            addButton.setDisable(!valid);

            String validPatterns = String
                    .format("(%s|%s|%s)", InputParser.FIRST_NAME, InputParser.LAST_NAME, InputParser.TITLE);
            String inputPatternWithoutValidPlaceholders = detailView.getPattern().getInputPattern()
                    .replaceAll(validPatterns, "");
            String outputPatternWithoutValidPlaceholders = detailView.getPattern().getOutputPattern()
                    .replaceAll(validPatterns, "");

            if(inputPatternWithoutValidPlaceholders.contains("%") || outputPatternWithoutValidPlaceholders
                    .contains("%")) {
                Pattern pattern = Pattern.compile("%[A-Za-z]*");
                Matcher matcher = pattern
                        .matcher(inputPatternWithoutValidPlaceholders.concat(outputPatternWithoutValidPlaceholders));
                if(matcher.find()) {
                    unsupportedPlaceholderLabel
                            .setText("⚠ Achtung: Nicht unterstützter Platzhalter: ".concat(matcher.group(0)));
                    unsupportedPlaceholderLabel.setVisible(true);
                }
                else {
                    unsupportedPlaceholderLabel.setVisible(false);
                }
            }
            else {
                unsupportedPlaceholderLabel.setText("");
                unsupportedPlaceholderLabel.setVisible(false);
            }
        });

        addButton.setDisable(true);
        topBox.add(detailView.getPane(), 0, 0);
    }

    /**
     * Sets the pattern to be edited in this popup window
     *
     * @param pattern to edit in this window
     */
    public void setPattern(ContactPattern pattern) {
        detailView.setPattern(pattern);
    }

    /**
     * Sets the submit command for this window
     *
     * @param onSubmitConsumer code that is executed on submit
     */
    public void setSubmitCommand(Consumer<ContactPattern> onSubmitConsumer) {
        addButton.setOnMouseClicked(event -> onSubmitConsumer.accept(detailView.getPattern()));
    }

    /**
     * Overwrites the default add button text
     *
     * @param text, text to set for the add button
     */
    public void setAddButtonText(String text) {
        addButton.setText(text);
    }
}
