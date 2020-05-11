package de.dhbw.kontaktsplitter.ui.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A class that allows for sorting, deleting and editing elements in a listview
 *
 * @author Daniel Bornbaum
 */
public class ElementEditor {
    private ListView<CustomListCell> elementsView;
    private ObservableList<CustomListCell> observableElements = FXCollections.observableArrayList();
    private Consumer<String> onDeleteConsumer;

    public ElementEditor() {
        this.elementsView = new ListView<>();
    }

    /**
     * Updates elements in list view
     *
     * @param elements as string to update
     */
    public void updateElements(List<String> elements) {
        observableElements.clear();

        for(String element : elements) {
            observableElements.add(new CustomListCell(element, this::removeElement, this::moveElementUp,
                    this::moveElementDown));
        }

        update();

        if(elements.size() > 0) {
            elementsView.scrollTo(0);
            elementsView.getSelectionModel().select(0);
        }
    }

    /**
     * Passes the editing behaviour down to each created list element
     *
     * @param valueConsumer behaviour that should be executed instead of the default behaviour
     */
    public void overwriteElementsEditCommand(Consumer<String> valueConsumer) {
        for(CustomListCell cell : observableElements) {
            cell.overwriteEditCommand(valueConsumer);
        }

        update();
    }

    /**
     * Sets an additional behaviour on item deletion
     *
     * @param deleteConsumer code to be executed on item delete
     */
    public void setOnDelete(Consumer<String> deleteConsumer) {
        onDeleteConsumer = deleteConsumer;
    }

    /**
     * Selects an element by its title
     *
     * @param element title to select
     */
    public void select(String element) {
        for(CustomListCell cell : elementsView.getItems()) {
            if(cell.getValue().equals(element)) {
                elementsView.getSelectionModel().select(cell);
                break;
            }
        }
    }

    /**
     * Handles when an element is selected, passes element or null to the consumer, if none is selected
     *
     * @param eventConsumer code to be executed on selected element
     */
    public void setSelectionHandler(Consumer<String> eventConsumer) {
        elementsView.setOnMouseClicked(event -> {
            if(observableElements.size() > 0) {
                eventConsumer.accept(elementsView.getSelectionModel().getSelectedItems().get(0).getValue());
            }
            else {
                eventConsumer.accept(null);
            }
        });
    }

    /**
     * @return the ListView Object from JavaFx
     */
    public ListView<CustomListCell> getElementsView() {
        return elementsView;
    }

    /**
     * @return elements titles as strings
     */
    public List<String> getElements() {
        return elementsView.getItems().stream().map(CustomListCell::getValue).collect(Collectors.toList());
    }

    /**
     * Adds a list element to the list, shows an alert, if the element is already present
     *
     * @param displayTitle element to add to the list
     */
    public void addListElement(String displayTitle) {
        for(CustomListCell element : observableElements) {
            if(element.getValue().equals(displayTitle)) {
                new Alert(Alert.AlertType.INFORMATION, "Ein solches Element besteht schon.", ButtonType.OK).show();
                return;
            }
        }

        CustomListCell cell = new CustomListCell(displayTitle, this::removeElement, this::moveElementUp,
                this::moveElementDown);
        observableElements.add(cell);
        update();

        elementsView.getSelectionModel().select(cell);
        elementsView.scrollTo(cell);
    }

    /**
     * Finds an element, if present, inside the listview and replaces it
     *
     * @param oldElement old value of the element
     * @param newElement new value of the element
     */
    public void replaceListElement(String oldElement, String newElement) {
        //Avoiding java.util.ConcurrentModificationException
        List<CustomListCell> newElements = new ArrayList<>();

        observableElements.forEach(cell ->
        {
            if(cell.getValue().equals(oldElement)) {
                CustomListCell newCell = new CustomListCell(newElement, this::removeElement,
                        this::moveElementUp,
                        this::moveElementDown);
                newCell.overwriteEditCommand(cell.getOverwrittenEditCommand());
                newElements
                        .add(newCell);
            }
            else {
                newElements.add(cell);
            }
        });

        observableElements = FXCollections.observableList(newElements);
        update();
    }

    /**
     * Moves the element up one step inside the list
     *
     * @param cell element to move up
     */
    private void moveElementUp(CustomListCell cell) {
        int index = observableElements.indexOf(cell);

        if(index != 0) {
            observableElements.remove(cell);
            observableElements.add(index - 1, cell);
        }
    }

    /**
     * Moves the element down one step inside the list
     *
     * @param cell element to move up
     */
    private void moveElementDown(CustomListCell cell) {
        int index = observableElements.indexOf(cell);

        if(index != observableElements.size() - 1) {
            observableElements.remove(cell);
            observableElements.add(index + 1, cell);
        }
    }

    /**
     * Removes an element from this list view
     *
     * @param cell element to remove
     */
    private void removeElement(CustomListCell cell) {
        if(onDeleteConsumer != null) {
            onDeleteConsumer.accept(cell.getValue());
        }

        observableElements.remove(cell);

        update();
    }

    /**
     * Helper method to update the list view
     */
    private void update() {
        elementsView.setItems(observableElements);
    }
}
