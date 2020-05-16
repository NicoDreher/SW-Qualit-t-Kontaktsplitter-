package de.dhbw.kontaktsplitter.ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

/**
 * Custom class that changes the appearance of a list cell with buttons
 *
 * @author Daniel Bornbaum
 */
public class CustomListCell extends HBox {
    private final TextField textInput = new TextField("");
    private final Button editButton = new Button("✎");

    private String value;
    private Consumer<String> valueConsumer;

    /**
     * @param title title of the list element, displayed in an editable element
     * @param onDelete method that is called to delete this element
     * @param onMoveUp method that is called to move this element up
     * @param onMoveDown method that is called to move this element down
     */
    public CustomListCell(String title, Consumer<CustomListCell> onDelete, Consumer<CustomListCell> onMoveUp,
            Consumer<CustomListCell> onMoveDown) {
        super();
        this.value = title;

        setMinWidth(10);
        textInput.setMinWidth(5);

        textInput.setText(title);
        textInput.setDisable(true);
        textInput.setBackground(null);
        textInput.getStyleClass().add("toggleable-text-input");

        textInput.setOnKeyReleased(event -> {
            editButton.setDisable("".equals(textInput.getText()));
        });

        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.getStyleClass().add("inline-button");
        editButton.getStyleClass().add("inline-button");
        Button upButton = new Button("▲");
        upButton.getStyleClass().add("inline-button");
        Button downButton = new Button("▼");
        downButton.getStyleClass().add("inline-button");

        getChildren().addAll(textInput, upButton, downButton, editButton, deleteButton);
        HBox.setHgrow(textInput, Priority.ALWAYS);

        editButton.setOnAction(actionEvent -> {
            if(valueConsumer != null) {
                valueConsumer.accept(value);
                return;
            }

            if(editButton.getText().equals("✎")) {
                editButton.setText("✓");
                textInput.setDisable(false);
                textInput.requestFocus();
                this.value = textInput.getText();
            }
            else {
                editButton.setText("✎");
                textInput.setDisable(true);
            }
        });

        deleteButton.setOnAction(event -> onDelete.accept(this));
        upButton.setOnAction(event -> onMoveUp.accept(this));
        downButton.setOnAction(event -> onMoveDown.accept(this));
    }

    /**
     * @return the value of this element
     */
    public String getValue() {
        return value;
    }

    /**
     * A method to overwrite the default edit behaviour, which is enabling the title for editing
     *
     * @param valueConsumer code that is executed when the edit button is clicked
     */
    public void overwriteEditCommand(Consumer<String> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }

    /**
     * Method that returns the overwritten edit behaviour or null, if none specified
     *
     * @return edit behaviour as consumer or null
     */
    public Consumer<String> getOverwrittenEditCommand() {
        return valueConsumer;
    }
}
