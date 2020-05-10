package de.dhbw.kontaktsplitter.persistence;

import de.dhbw.kontaktsplitter.models.Title;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private static List<Title> titles;

    static {
        titles.add(new Title("Dr. rer. nat."));
        titles.add(new Title("Dr."));
    }

    public static List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public static boolean isFirstName(String name) {
        return false;
    }
}
