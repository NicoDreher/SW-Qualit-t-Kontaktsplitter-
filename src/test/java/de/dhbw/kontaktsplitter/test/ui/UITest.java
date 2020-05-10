package de.dhbw.kontaktsplitter.test.ui;

import de.dhbw.kontaktsplitter.models.Gender;
import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.Startup;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ComboBoxMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class UITest {
    @BeforeAll
    public static void before() {
        if("true".equalsIgnoreCase(System.getenv("headless"))) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Start
    public void start(Stage stage) throws Exception {
        new Startup().start(stage);
    }

    @Test
    public void test(FxRobot robot) throws InterruptedException {
        assertTrue(true);
    }

    @Test
    void initializeUI_correct_setup(FxRobot robot)
    {
        FxAssert.verifyThat("#lbl_salutation", LabeledMatchers.hasText(""));
        FxAssert.verifyThat("#cmb_gender", ComboBoxMatchers.containsItems(Gender.values()));
        FxAssert.verifyThat("#cmb_title", ComboBoxMatchers.containsItems(Configuration.getTitles()));
        FxAssert.verifyThat("#cmb_language", ComboBoxMatchers.containsItems(List.of("DE", "EN")));
    }
}
