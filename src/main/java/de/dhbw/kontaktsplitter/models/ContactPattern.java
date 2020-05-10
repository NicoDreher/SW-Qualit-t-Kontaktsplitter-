package de.dhbw.kontaktsplitter.models;

import de.dhbw.kontaktsplitter.persistence.Configuration;

import java.util.ArrayList;
import java.util.List;
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

    private Title findTitle(String[] tokens, int inputIndex) {
        for(Title title : Configuration.getTitles()) {
            if(title.matches(tokens, inputIndex)) {
                return title;
            }
        }
        return null;
    }

    public Contact parse(String input) {
        String[] inputTokens = input.split(" ");
        String[] patternTokens = inputPattern.split(" ");
        Gender inputGender = gender;
        List<Title> titles = new ArrayList<>();
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        int inputIndex = 0;
        for(String token : patternTokens) {
            if(token.startsWith("%")) {
                if(token.equalsIgnoreCase("%TITLE")) {
                    Title title = null;
                    while((title = findTitle(inputTokens, inputIndex)) != null) {
                        inputIndex += title.getLength();
                        titles.add(title);
                    }
                }
                else if(token.equalsIgnoreCase("%VORNAME")) {
                    while(inputIndex < inputTokens.length && (Configuration.isFirstName(inputTokens[inputIndex]) || firstNames.isEmpty())) {
                        firstNames.add(inputTokens[inputIndex]);
                        inputIndex++;
                    }
                }
                else if(token.equalsIgnoreCase("%NACHNAME")) {
                    while(inputIndex < inputTokens.length && (!Configuration.isFirstName(inputTokens[inputIndex]) || lastNames.isEmpty())) {
                        lastNames.add(inputTokens[inputIndex]);
                        inputIndex++;
                    }
                }
                else {
                    return null;
                }
            }
            else if(token.equalsIgnoreCase(inputTokens[inputIndex])) {

            }
            else {
                return null;
            }
        }
        return null;
    }
}
