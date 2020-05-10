package de.dhbw.kontaktsplitter.test.models;

import de.dhbw.kontaktsplitter.models.Title;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TitleTests {
    @Test
    void testMatch() {
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("Dr. rer. nat."));
        titles.add(new Title("Dr."));
        String[] input = "Herr Dr. rer. Hans Wurst".split(" ");
        int inputIndex = 1;
        List<Title> matches = new ArrayList<>();
        for(Title title : titles) {
            if(title.matches(input, inputIndex)) {
                int add = title.getLength();
                inputIndex += add;
                if(add > 0) {
                    matches.add(title);
                }
            }
        }
        System.out.println(matches.toString());
    }
}
