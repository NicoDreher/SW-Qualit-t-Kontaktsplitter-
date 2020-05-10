package de.dhbw.kontaktsplitter;

import de.dhbw.kontaktsplitter.persistence.Configuration;
import de.dhbw.kontaktsplitter.ui.Startup;

public class Kontaktsplitter {
    public static void main(String[] args) {
        Configuration.loadConfig();
        Startup.main(args);
    }
}
