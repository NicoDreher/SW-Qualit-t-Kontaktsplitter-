package de.dhbw.kontaktsplitter.test.parser;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.parser.InputParser;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.test.StringArrayConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nico Dreher
 */
public class ContactParserTest {

    /**
     * Testing the result of {@link InputParser#parseInput(String)} when no pattern matches
     */
    @Test
    void testNoPatternFound() {
        List<ContactPattern> patterns = Configuration.getPatterns();
        Configuration.setPatterns(new ArrayList<>());
        Contact contact = InputParser.parseInput("XXXXX");
        Configuration.setPatterns(patterns);
        assertNotNull(contact);
        assertEquals("Deutsch", contact.getLanguage());
        assertEquals("", contact.getFirstName());
        assertEquals("", contact.getLastName());
        assertEquals(Gender.NONE, contact.getGender());
        assertEquals("", contact.getTitlesAsString());
    }

    /**
     * Testing the parsing using a created pattern
     *
     * @param patternLanguage The language of the pattern
     * @param patternGender The gender of the pattern
     * @param inputPattern The input pattern
     * @param input The input string
     * @param gender The expected gender
     * @param titles The expected titles
     * @param firstName The forenames
     * @param lastName The surnames
     * @param isNull Should the result be null
     */
    @ParameterizedTest(name = "[{index}] Input: {3}")
    @CsvFileSource(resources = "/parser/contact.csv")
    void testMatch(String patternLanguage, Gender patternGender, String inputPattern, String input, Gender gender,
            String titles, String firstName, String lastName, boolean isNull) {
        ContactPattern pattern = new ContactPattern(patternLanguage, patternGender, inputPattern, "");
        Contact contact = InputParser.parse(pattern, input);
        if(!isNull) {
            assertNotNull(contact);
            assertEquals(gender, contact.getGender());
            assertEquals(titles, contact.getTitlesAsString());
            assertEquals(firstName, contact.getFirstName());
            assertEquals(lastName, contact.getLastName());
        }
        else {
            assertNull(contact);
        }
    }

    /**
     * Testing the pattern detection using the default patterns
     *
     * @param input The input string
     * @param language The expected language
     * @param gender The expected gender
     * @param titles The expected titles
     * @param firstName The expected forenames
     * @param lastName The expected surnames
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/parser/defaultPatterns.csv")
    void testMatchDefault(String input, String language, Gender gender, String titles, String firstName,
            String lastName) {
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
     *
     * @param lastNames An array of surnames
     * @param expectedOutput The expected output
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/parser/lastnames.csv")
    void testLastNames(@ConvertWith(StringArrayConverter.class) String[] lastNames, String expectedOutput) {
        assertEquals(expectedOutput, InputParser.parseLastNames(List.of(lastNames)));
    }

    /**
     * Testing the parsing of the output
     *
     * @param input An input string
     * @param expectedOutput The expected output
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/ui/correctSalutation.csv")
    void testOutput(String input, String expectedOutput) {
        assertEquals(expectedOutput, InputParser.generateOutput(InputParser.parseInput(input)));
    }

    /**
     * Testing a pattern with an invalid token
     */
    @Test
    void testInvalidToken() {
        ContactPattern pattern = new ContactPattern("Deutsch", Gender.MALE, "Hallo %INVALID", "");
        Contact contact = InputParser.parse(pattern, "Hallo Hans");
        assertNull(contact);
    }

    /**
     * Test the generated output of a contact with no matching pattern
     */
    @Test
    void generateTokenWithoutPattern() {
        assertEquals("", InputParser.generateOutput(new Contact(null, null, null, null, null)));
        assertEquals("", InputParser.generateOutput(new Contact(null, Gender.NONE, null, null, null)));
        assertEquals("", InputParser.generateOutput(new Contact("Not a language", null, null, null, null)));
        assertEquals("", InputParser.generateOutput(new Contact("Not a language", Gender.NONE, null, null, null)));
    }

    /**
     * Test the name regex pattern for all known names
     */
    @Test
    void testNamePattern() {
        for(String name : Configuration.getNames()) {
            String inputName = name.replaceAll("[+'\\-]", "");
            boolean matches = inputName.matches(InputParser.NAME_PATTERN);
            if(!matches) {
                System.out.println(name);
                name.chars().forEach(i -> System.out.println("\\u" + Integer.toHexString(i | 0x10000).substring(1)));
            }
            assertTrue(matches);
        }
    }
}
