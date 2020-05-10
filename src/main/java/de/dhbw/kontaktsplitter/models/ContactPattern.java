package de.dhbw.kontaktsplitter.models;

import java.util.Locale;

public class ContactPattern {
    private Locale locale;
    private Gender gender;
    private String inputPattern;
    private String outputPattern;

    public ContactPattern(Locale locale, Gender gender, String inputPattern, String outputPattern) {
        this.locale = locale;
        this.gender = gender;
        this.inputPattern = inputPattern;
        this.outputPattern = outputPattern;
    }

    public Locale getLocale() {
        return locale;
    }

    public Gender getGender() {
        return gender;
    }

    public String getInputPattern() {
        return inputPattern;
    }

    public String getOutputPattern() {
        return outputPattern;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setInputPattern(String inputPattern) {
        this.inputPattern = inputPattern;
    }

    public void setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern;
    }
}
