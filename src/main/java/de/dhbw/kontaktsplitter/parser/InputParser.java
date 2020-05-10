package de.dhbw.kontaktsplitter.parser;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;

import java.util.ArrayList;
import java.util.List;

public class InputParser {
    public static final String TITLE = "%TITEL";
    public static final String FIRST_NAME = "%FIRST_NAME";
    public static final String LAST_NAME = "%LAST_NAME";

    public static Title findTitle(String[] tokens, int inputIndex) {
        for(Title title : Configuration.getTitles()) {
            if(title.matches(tokens, inputIndex)) {
                return title;
            }
        }
        return null;
    }

    public static Contact parse(ContactPattern pattern, String input) {
        String[] inputTokens = input.split(" ");
        String[] patternTokens = pattern.getInputPattern().split(" ");
        Gender inputGender = pattern.getGender();
        List<Title> titles = new ArrayList<>();
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        int inputIndex = 0;
        for(String token : patternTokens) {
            if(token.startsWith("%")) {
                if(token.equalsIgnoreCase("%TITEL")) {
                    Title title;
                    while((title = InputParser.findTitle(inputTokens, inputIndex)) != null) {
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
                inputIndex++;
            }
            else {
                return null;
            }
        }
        return new Contact(pattern.getLanguage(), inputGender, titles, String.join(" ", firstNames), String.join("-", lastNames));
    }

    public static Contact parseInput(String input) {
        for(ContactPattern pattern : Configuration.getPatterns()) {
            Contact contact = parse(pattern, input);
            if(contact != null) {
                return contact;
            }
        }
        return null;
    }
}
