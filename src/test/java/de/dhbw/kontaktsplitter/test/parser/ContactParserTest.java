package de.dhbw.kontaktsplitter.test.parser;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.parser.InputParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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
        assertEquals(firstName, contact.getFistName());
        assertEquals(lastName, contact.getLastName());
    }
}
