package de.dhbw.kontaktsplitter.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Contact {
    private String language;
    private Gender gender;
    private List<Title> titles;
    private String firstName;
    private String lastName;

    public Contact(){}

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

    public Gender getGender() {
        return gender;
    }

    public List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLanguage(String Language) {
        this.language = language;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public String getTitlesAsString() {
        return titles.stream().map(Title::getTitle).collect(Collectors.joining(" "));
    }
}
