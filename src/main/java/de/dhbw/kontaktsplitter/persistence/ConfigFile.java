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
    private List<Title> titles;
    private List<ContactPattern> patterns;

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
