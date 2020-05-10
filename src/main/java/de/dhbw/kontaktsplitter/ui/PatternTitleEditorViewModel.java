package de.dhbw.kontaktsplitter.ui;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.persistence.Configuration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PatternTitleEditorViewModel extends TitleEditorViewModel
{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        super.initialize(url, resourceBundle);
        updateElements(Configuration.getPatterns().stream().map(ContactPattern::getInputPattern).collect(
                Collectors.toList()));
    }
}
