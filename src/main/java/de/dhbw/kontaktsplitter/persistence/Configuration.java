package de.dhbw.kontaktsplitter.persistence;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static de.dhbw.kontaktsplitter.parser.InputParser.*;

public class Configuration {

    private static List<Title> titles;

    private static final List<String> LANGUAGES = Arrays.stream(Locale.getISOLanguages())
            .map(Locale::new)
            .map(Locale::getDisplayLanguage)
            .collect(Collectors.toList());

    private static List<ContactPattern> patterns;

    static {
        titles = new ArrayList<>();
        titles.add(new Title("Prof."));
        titles.add(new Title("Dr. rer. nat."));
        titles.add(new Title("Dr."));
        titles.add(new Title("Dipl. Ing."));
        titles.add(new Title("Dipl."));

        patterns = new ArrayList<>();
        patterns.add(new ContactPattern("Deutsch", Gender.MALE, "Herr " + TITLE + " " + FIRST_NAME + " " + LAST_NAME, "Sehr geehrter Herr " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Deutsch", Gender.FEMALE, "Frau " + TITLE + " " + FIRST_NAME + " " + LAST_NAME, "Sehr geehrte Frau " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
        patterns.add(new ContactPattern("Deutsch", Gender.NONE, TITLE + " " + FIRST_NAME + " " + LAST_NAME, "Sehr geehrter Damen und Herren"));
        patterns.add(new ContactPattern("Deutsch", Gender.DIVERS, TITLE + " " + FIRST_NAME + " " + LAST_NAME, "Guten Tag " + TITLE + " " + FIRST_NAME + " " + LAST_NAME));
    }

    public static List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public static List<ContactPattern> getPatterns() {
        return new ArrayList<>(patterns);
    }

    public static boolean isFirstName(String name) {
        return false;
    }

    public static List<String> getLanguages() {
        return new ArrayList<>(LANGUAGES);
    }

}
