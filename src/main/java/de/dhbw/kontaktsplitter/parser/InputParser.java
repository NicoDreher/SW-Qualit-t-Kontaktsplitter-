package de.dhbw.kontaktsplitter.parser;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * All functions to splice a salutation and parse the heading
 * @author Nico Dreher
 */
public class InputParser {
    /**
     * The placeholder for academic and non academic titles
     */
    public static final String TITLE = "%TITEL";
    /**
     * The placeholder for forenames
     */
    public static final String FIRST_NAME = "%VORNAMEN";
    /**
     * The placeholder for Surnames
     */
    public static final String LAST_NAME = "%NACHNAMEN";

    /**
     * Scan a token array for titles
     * @param tokens
     * @param inputIndex The start index of the scan
     * @return The title first found
     */
    public static Title findTitle(String[] tokens, int inputIndex) {
        for(Title title : Configuration.getTitles()) {
            if(title.matches(tokens, inputIndex)) {
                return title;
            }
        }
        return null;
    }

    /**
     * Parse a {@link ContactPattern} and input text to contact details
     * @param pattern The pattern for the parsing
     * @param input The input string
     * @return The spliced contact details
     */
    public static Contact parse(ContactPattern pattern, String input) {
        String[] inputTokens = input.split(" ");
        String[] patternTokens = pattern.getInputPattern().split(" ");
        Gender inputGender = pattern.getGender();
        List<Title> titles = new ArrayList<>();
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        int inputIndex = 0;
        int first = 0;
        for(String token : patternTokens) {
            if(token.startsWith("%")) {
                if(token.equalsIgnoreCase(TITLE)) {
                    Title title;
                    while((title = InputParser.findTitle(inputTokens, inputIndex)) != null) {
                        inputIndex += title.getLength();
                        titles.add(title);
                    }
                }
                else if(token.equalsIgnoreCase(FIRST_NAME)) {
                    while(inputIndex < inputTokens.length && !inputTokens[inputIndex].contains(",") && (Configuration.isFirstName(inputTokens[inputIndex]) || firstNames.isEmpty() || first == 2)) {
                        firstNames.add(inputTokens[inputIndex]);
                        if(Gender.NONE.equals(inputGender)) {
                            inputGender = Configuration.getGender(inputTokens[inputIndex]);
                        }
                        inputIndex++;
                    }
                    if(first == 0) {
                        first = 1;
                    }
                }
                else if(token.equalsIgnoreCase(LAST_NAME)) {
                    while(inputIndex < inputTokens.length && !inputTokens[inputIndex].contains(",") && (!Configuration.isFirstName(inputTokens[inputIndex]) || lastNames.isEmpty() || first == 1)) {
                        lastNames.add(inputTokens[inputIndex]);
                        inputIndex++;
                    }
                    if(first == 0) {
                        first = 2;
                    }
                }
                else {
                    return null;
                }
            }
            else if(inputTokens.length > inputIndex && token.equalsIgnoreCase(inputTokens[inputIndex])) {
                inputIndex++;
            }
            else {
                return null;
            }
        }
        if(firstNames.isEmpty() && lastNames.size() >= 2) {
            firstNames.add(lastNames.get(lastNames.size() - 1));
            lastNames.remove(lastNames.size() - 1);
        }
        if(lastNames.isEmpty() && firstNames.size() >= 2) {
            lastNames.add(firstNames.get(firstNames.size() - 1));
            firstNames.remove(firstNames.size() - 1);
        }
        return new Contact(pattern.getLanguage(), inputGender, titles, String.join(" ", firstNames), parseLastNames(lastNames));
    }

    /**
     * Parse a input string to the spliced contact details. Using the first matching pattern.
     * @param input The input string
     * @return The spliced contact details
     */
    public static Contact parseInput(String input) {
        input = input.replaceAll(",", " , ").replaceAll("\\s+", " ");
        for(ContactPattern pattern : Configuration.getPatterns()) {
            Contact contact = parse(pattern, input);
            if(contact != null) {
                return contact;
            }
        }
        return new Contact("Deutsch", Gender.NONE, new ArrayList<>(), "", "");
    }

    /**
     * Generates a output out using the language and gender of the contact details
     * @param contact
     * @return A formatted output
     */
    public static String generateOutput(Contact contact) {
        Optional<ContactPattern> foundPattern = Configuration.getPatterns().stream().filter(pattern -> pattern.getLanguage().equalsIgnoreCase(contact.getLanguage()) && pattern.getGender().equals(contact.getGender())).findFirst();
        if(foundPattern.isPresent()) {
            return foundPattern.get().parseContact(contact);
        }
        return "";
    }

    /**
     * Parsing surnames comply with the prefixes and suffixes
     * @param lastNames A list of the surnames
     * @return The formatted surnames
     */
    public static String parseLastNames(List<String> lastNames) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < lastNames.size(); i++) {
            if(i < lastNames.size() - 1) {
                result.append(lastNames.get(i));
                if(Configuration.getPrefixesAndSuffixes().contains(lastNames.get(i)) || Configuration.getPrefixesAndSuffixes().contains(lastNames.get(i + 1))) {
                    result.append(" ");
                }
                else {
                    result.append("-");
                }
            }
            else {
                result.append(lastNames.get(i));
            }
        }
        return result.toString();
    }
}
