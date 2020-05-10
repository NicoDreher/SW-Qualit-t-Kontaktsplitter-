package de.dhbw.kontaktsplitter.persistence;

import de.dhbw.kontaktsplitter.models.Title;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Configuration {

    private static List<Title> titles;

    private static final List<String> LANGUAGES = Arrays.stream(Locale.getISOLanguages())
            .map(Locale::new)
            .map(Locale::getDisplayLanguage)
            .collect(Collectors.toList());


    static {
        titles = new ArrayList<>();
        titles.add(new Title("Prof."));
        titles.add(new Title("Dr. rer. nat."));
        titles.add(new Title("Dr."));
        titles.add(new Title("Dipl. Ing."));
        titles.add(new Title("Dipl."));
    }

    public static List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public static boolean isFirstName(String name) {
        return false;
    }

    public static List<String> getLanguages() {
        return new ArrayList<>(LANGUAGES);
    }

}
