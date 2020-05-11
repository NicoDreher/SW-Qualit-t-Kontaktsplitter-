package de.dhbw.kontaktsplitter.test.models;

import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.parser.InputParser;
import de.dhbw.kontaktsplitter.test.StringArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nico Dreher
 */
public class TitleTests {
    /**
     * Testing the detection of titles in a input string
     *
     * @param input The input string
     * @param inputIndex The start index of the titles
     * @param expectedTitles The expected titles
     * @param finalIndex The index after all titles
     */
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/titles.csv")
    void testMatch(String input, int inputIndex, @ConvertWith(StringArrayConverter.class) String[] expectedTitles,
            int finalIndex) {
        String[] inputTokens = input.split(" ");
        List<Title> matches = new ArrayList<>();
        Title title;
        while((title = InputParser.findTitle(inputTokens, inputIndex)) != null) {
            inputIndex += title.getLength();
            matches.add(title);
        }
        assertEquals(finalIndex, inputIndex);
        assertArrayEquals(matches.stream().map(Title::getTitle).toArray(String[]::new), expectedTitles);
    }
}
