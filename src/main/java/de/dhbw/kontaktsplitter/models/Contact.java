package de.dhbw.kontaktsplitter.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Contact {
    private Locale locale;
    private Gender gender;
    private List<Title> titles;
    private String fistName;
    private String lastName;

    public Contact(Locale locale, Gender gender, List<Title> titles, String firstName, String lastName) {
        this.locale = locale;
        this.gender = gender;
        this.titles = titles;
        this.fistName = firstName;
        this.lastName = lastName;
    }

    public Locale getLocale() {
        return locale;
    }

    public Gender getGender() {
        return gender;
    }

    public List<Title> getTitles() {
        return new ArrayList<>(titles);
    }

    public String getFistName() {
        return fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
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
