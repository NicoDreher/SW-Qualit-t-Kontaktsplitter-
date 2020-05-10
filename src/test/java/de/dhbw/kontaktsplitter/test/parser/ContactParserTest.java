package de.dhbw.kontaktsplitter.test.parser;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.parser.InputParser;
import de.dhbw.kontaktsplitter.test.StringArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nico Dreher
 */
public class ContactParserTest {
    /**
     * Testing the parsing using a created pattern
     * @param patternLanguage The language of the pattern
     * @param patternGender The gender of the pattern
     * @param inputPattern The input pattern
     * @param input The input string
     * @param gender The expected gender
     * @param titles The expected titles
     * @param firstName The forenames
     * @param lastName The surnames
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/contact.csv")
    void testMatch(String patternLanguage, Gender patternGender, String inputPattern, String input, Gender gender, String titles, String firstName, String lastName) {
        ContactPattern pattern = new ContactPattern(patternLanguage, patternGender, inputPattern, "");
        Contact contact = InputParser.parse(pattern, input);
        assertNotNull(contact);
        assertEquals(gender, contact.getGender());
        assertEquals(titles, contact.getTitlesAsString());
        assertEquals(firstName, contact.getFirstName());
        assertEquals(lastName, contact.getLastName());
    }

    /**
     * Testing the pattern detection using the default patterns
     * @param input The input string
     * @param language The expected language
     * @param gender The expected gender
     * @param titles The expected titles
     * @param firstName The expected forenames
     * @param lastName The expected surnames
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/defaultPatterns.csv")
    void testMatchDefault(String input, String language, Gender gender, String titles, String firstName, String lastName) {
        Contact contact = InputParser.parseInput(input);
        assertNotNull(contact);
        assertEquals(language, contact.getLanguage());
        assertEquals(gender, contact.getGender());
        assertEquals(titles, contact.getTitlesAsString());
        assertEquals(firstName, contact.getFirstName());
        assertEquals(lastName, contact.getLastName());
    }

    /**
     * Testing the parsing of the surnames
     * @param lastNames A array of surnames
     * @param expectedOutput The expected output
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/lastnames.csv")
    void testLastNames(@ConvertWith(StringArrayConverter.class) String[] lastNames, String expectedOutput) {
        assertEquals(expectedOutput, InputParser.parseLastNames(List.of(lastNames)));
    }

    /**
     * Testing the parsing of the output
     * @param input A input string
     * @param expectedOutput The expected output
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/outputs.csv")
    void testOutput(String input, String expectedOutput) {
        assertEquals(expectedOutput, InputParser.generateOutput(InputParser.parseInput(input)));
    }
}
