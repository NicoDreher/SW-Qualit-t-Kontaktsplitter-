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

public class ContactParserTest {
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

    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/lastnames.csv")
    void testLastNames(@ConvertWith(StringArrayConverter.class) String[] lastNames, String expectedOutput) {
        assertEquals(expectedOutput, InputParser.parseLastNames(List.of(lastNames)));
    }

    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/outputs.csv")
    void testOutput(String input, String expectedOutput) {
        assertEquals(expectedOutput, InputParser.generateOutput(InputParser.parseInput(input)));
    }
}
