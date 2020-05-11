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

public class ElementEditor
{
    private ListView<CustomListCell> elementsView;
    private ObservableList<CustomListCell> observableElements = FXCollections.observableArrayList();
    private Consumer<String> onDeleteConsumer;

    public ElementEditor()
    {
        this.elementsView = new ListView<>();
    }

    public void updateElements(List<String> elements)
    {
        observableElements.clear();

        for (String element : elements)
        {
            observableElements.add(new CustomListCell(element, this::removeElement, this::moveElementUp,
                                                      this::moveElementDown));
        }

        update();

        if (elements.size() > 0)
        {
            elementsView.scrollTo(0);
            elementsView.getSelectionModel().select(0);
        }
    }

    public void overwriteElementsEditCommand(Consumer<String> valueConsumer)
    {
        for (CustomListCell cell : observableElements)
        {
            cell.overwriteEditCommand(valueConsumer);
        }

        update();
    }

    public void setOnDelete(Consumer<String> deleteConsumer)
    {
        onDeleteConsumer = deleteConsumer;
    }

    public void select(String element)
    {
        for (CustomListCell cell : elementsView.getItems())
        {
            if (cell.getValue().equals(element))
            {
                elementsView.getSelectionModel().select(cell);
                break;
            }
        }
    }

    public void setSelectionHandler(Consumer<String> eventConsumer)
    {
        elementsView.setOnMouseClicked(event -> {
            if (observableElements.size() > 0)
            {
                eventConsumer.accept(elementsView.getSelectionModel().getSelectedItems().get(0).getValue());
            }
            else
            {
                eventConsumer.accept(null);
            }
        });
    }

    public ListView<CustomListCell> getElementsView()
    {
        return elementsView;
    }

    public List<String> getElements()
    {
        return elementsView.getItems().stream().map(CustomListCell::getValue).collect(Collectors.toList());
    }

    public void addListElement(String displayTitle)
    {
        for (CustomListCell element : observableElements)
        {
            if (element.getValue().equals(displayTitle))
            {
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

    public void replaceListElement(String oldElement, String newElement)
    {
        //Avoiding java.util.ConcurrentModificationException
        List<CustomListCell> newElements = new ArrayList<>();

        observableElements.forEach(cell ->
                                   {
                                       if (cell.getValue().equals(oldElement))
                                       {
                                           CustomListCell newCell = new CustomListCell(newElement, this::removeElement,
                                                                                       this::moveElementUp,
                                                                                       this::moveElementDown);
                                           newCell.overwriteEditCommand(cell.getOverwrittenEditCommand());
                                           newElements
                                                   .add(newCell);
                                       }
                                       else
                                       {
                                           newElements.add(cell);
                                       }
                                   });

        observableElements = FXCollections.observableList(newElements);
        update();
    }

    private void moveElementUp(CustomListCell cell)
    {
        int index = observableElements.indexOf(cell);

        if (index != 0)
        {
            observableElements.remove(cell);
            observableElements.add(index - 1, cell);
        }
    }

    private void moveElementDown(CustomListCell cell)
    {
        int index = observableElements.indexOf(cell);

        if (index != observableElements.size() - 1)
        {
            observableElements.remove(cell);
            observableElements.add(index + 1, cell);
        }
    }

    private void removeElement(CustomListCell cell)
    {
        if (onDeleteConsumer != null)
        {
            onDeleteConsumer.accept(cell.getValue());
        }

        observableElements.remove(cell);

        update();
    }

    private void update()
    {
        elementsView.setItems(observableElements);
    }
}
