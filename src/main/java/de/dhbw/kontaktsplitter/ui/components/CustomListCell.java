package de.dhbw.kontaktsplitter.ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

public class CustomListCell extends HBox
{
    private TextField textInput = new TextField("");
    private Button editButton = new Button("✎");

    private String value;
    private Consumer<String> valueConsumer;

    public CustomListCell(String title, Consumer<CustomListCell> onDelete, Consumer<CustomListCell> onMoveUp,
                          Consumer<CustomListCell> onMoveDown)
    {
        super();
        this.value = title;

        setMinWidth(10);
        textInput.setMinWidth(5);

        textInput.setText(title);
        textInput.setDisable(true);
        textInput.setBackground(null);
        textInput.getStyleClass().add("toggleable-text-input");

        textInput.setOnKeyReleased(event -> {
            if ("".equals(textInput.getText()))
            {
                editButton.setDisable(true);
            }
            else
            {
                editButton.setDisable(false);
            }
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
            if (valueConsumer != null)
            {
                valueConsumer.accept(value);
                return;
            }

            if (editButton.getText().equals("✎"))
            {
                editButton.setText("✓");
                textInput.setDisable(false);
                textInput.requestFocus();
                this.value = textInput.getText();
            }
            else
            {
                editButton.setText("✎");
                textInput.setDisable(true);
            }
        });

        deleteButton.setOnAction(event -> onDelete.accept(this));
        upButton.setOnAction(event -> onMoveUp.accept(this));
        downButton.setOnAction(event -> onMoveDown.accept(this));
    }

    public String getValue()
    {
        return value;
    }

    public void overwriteEditCommand(Consumer<String> valueConsumer)
    {
        this.valueConsumer = valueConsumer;
    }
}
