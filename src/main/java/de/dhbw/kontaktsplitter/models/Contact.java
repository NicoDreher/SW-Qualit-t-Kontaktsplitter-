package de.dhbw.kontaktsplitter.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The datatype for the spliced contact information
 *
 * @author Nico Dreher
 */
public class Contact {
    private String language;
    private Gender gender;
    private List<Title> titles;
    private String firstName;
    private String lastName;

    public Contact() {
    }

    public Contact(String language, Gender gender, List<Title> titles, String firstName, String lastName) {
        this.language = language;
        this.gender = gender;
        this.titles = titles;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitlesAsString() {
        return titles.stream().map(Title::getTitle).collect(Collectors.joining(" "));
    }

    public void addTitle(Title title)
    {
        this.titles.add(title);
    }

    public void removeTitle(Title title)
    {
        this.titles.removeAll(titles.stream().filter(e -> e.equals(title)).collect(Collectors.toList()));
    }
}
