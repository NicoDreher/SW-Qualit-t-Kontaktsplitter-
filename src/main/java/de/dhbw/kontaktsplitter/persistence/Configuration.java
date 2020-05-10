package de.dhbw.kontaktsplitter.persistence;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static de.dhbw.kontaktsplitter.parser.InputParser.*;

public class Configuration {

    private static List<Title> titles;

    private static final List<String> LANGUAGES = Arrays.stream(Locale.getISOLanguages())
            .map(Locale::new)
            .map(Locale::getDisplayLanguage)
            .collect(Collectors.toList());

    private static List<ContactPattern> patterns;

    private static List<String> prefixesAndSuffixes = List.of("van", "von", "zu", "vom");

    private static Map<String, Gender> names;

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

        names = new HashMap<>();
        try(InputStream stream = Configuration.class.getResourceAsStream("/names/names.txt"); InputStreamReader isr = new InputStreamReader(stream); BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.startsWith("M") || line.startsWith("F") || line.startsWith("?")) {
                    String[] parts = line.split("\\s+");
                    if(parts.length > 2) {
                        Gender gender;
                        switch(parts[0]) {
                            case "M": //Intended Fall through
                            case "?M":
                                gender = Gender.MALE;
                                break;
                            case "F":
                            case "?F":
                                gender = Gender.FEMALE;
                                break;
                            case "?":
                                gender = names.getOrDefault(parts[1], Gender.NONE);
                                break;
                            default:
                                gender = null;
                                break;
                        }
                        if(gender != null) {
                            names.put(parts[1], gender);
                        }
                    }
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public static List<ContactPattern> getPatterns() {
        return new ArrayList<>(patterns);
    }

    public static boolean isFirstName(String name) {
        return names.containsKey(name);
    }

    public static List<String> getLanguages() {
        return new ArrayList<>(LANGUAGES);
    }

    public static List<String> getPrefixesAndSuffixes() {
        return new ArrayList<>(prefixesAndSuffixes);
    }
}
