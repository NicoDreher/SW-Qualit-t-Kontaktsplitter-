package de.dhbw.kontaktsplitter.test.models;

import de.dhbw.kontaktsplitter.models.Title;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.test.StringArrayConverter;
import de.dhbw.kontaktsplitter.utils.PatternUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TitleTests {
    @ParameterizedTest(name = "[{index}] Input {0}")
    @CsvFileSource(resources = "/models/titles.csv")
    void testMatch(String input, int inputIndex, @ConvertWith(StringArrayConverter.class) String[] expectedTitles, int finalIndex) {
        String[] inputTokens = input.split(" ");
        List<Title> matches = new ArrayList<>();
        Title title;
        while((title = PatternUtils.findTitle(inputTokens, inputIndex)) != null) {
            inputIndex += title.getLength();
            matches.add(title);
        }
        assertEquals(finalIndex, inputIndex);
        assertArrayEquals(matches.stream().map(Title::getTitle).toArray(String[]::new), expectedTitles);
    }
}
