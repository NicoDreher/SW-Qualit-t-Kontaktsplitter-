package de.dhbw.kontaktsplitter.models;

import de.dhbw.kontaktsplitter.parser.InputParser;

/**
 * The patterns to splice contact information
 * @author Nico Dreher
 */
public class ContactPattern {
    /**
     * The language of the input and output
     */
    private String language;
    /**
     * The gender of the matching contact information
     */
    private Gender gender;
    /**
     * The input pattern to scan the salutation
     */
    private String inputPattern;
    /**
     * The output pattern to parse the greetings
     */
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
        return inputPattern.replaceAll(",", " , ").replaceAll("\\s+", " ");
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

    public String parseContact(Contact contact) {
        return outputPattern.replace(InputParser.TITLE, contact.getTitlesAsString()).replace(InputParser.FIRST_NAME, contact.getFirstName()).replace(InputParser.LAST_NAME, contact.getLastName()).trim().replaceAll("\\s+", " ");
    }
}
