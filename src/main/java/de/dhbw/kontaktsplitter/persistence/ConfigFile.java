package de.dhbw.kontaktsplitter.persistence;

import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Title;

import java.util.List;

/**
 * An object for the configuration entries
 *
 * @author Nico Dreher
 */
public class ConfigFile {
    private final List<Title> titles;
    private final List<ContactPattern> patterns;

    public ConfigFile(List<Title> titles, List<ContactPattern> patterns) {
        this.titles = titles;
        this.patterns = patterns;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public List<ContactPattern> getPatterns() {
        return patterns;
    }
}
