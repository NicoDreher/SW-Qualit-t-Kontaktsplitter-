package de.dhbw.kontaktsplitter.models;

public class ContactPattern {
    private String language;
    private Gender gender;
    private String inputPattern;
    private String outputPattern;

    public ContactPattern(String language, Gender gender, String inputPattern, String outputPattern) {
        this.language = language;
        this.gender = gender;
        this.inputPattern = inputPattern;
        this.outputPattern = outputPattern;
    }

    public String getLanguage() {
        return language;
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

    public void setLanguage(String language) {
        this.language = language;
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
