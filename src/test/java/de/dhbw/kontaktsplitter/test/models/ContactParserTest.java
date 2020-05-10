package de.dhbw.kontaktsplitter.test.models;

import de.dhbw.kontaktsplitter.models.Contact;
import de.dhbw.kontaktsplitter.models.ContactPattern;
import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.test.StringArrayConverter;
import de.dhbw.kontaktsplitter.utils.PatternUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class ContactParserTest {
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/contact.csv")
    void testMatch(String input, Gender gender, String titles, String firstName, String lastName) {
        ContactPattern pattern = new ContactPattern(Locale.GERMANY, Gender.MALE, "Herr %TITEL %VORNAME %NACHNAME", "");
        Contact contact = PatternUtils.parse(pattern, input);
        assertNotNull(contact);
        assertEquals(gender, contact.getGender());
        assertEquals(titles, contact.getTitlesAsString());
        assertEquals(firstName, contact.getFistName());
        assertEquals(lastName, contact.getLastName());
    }
}
