package de.dhbw.kontaktsplitter.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Nico Dreher
 */
public enum Gender {
    /**
     * No specific gender
     */
    @SerializedName("NONE")
    NONE("Keine Angabe"),
    @SerializedName("MALE")
    MALE("Männlich"),
    @SerializedName("FEMALE")
    FEMALE("Weiblich"),
    @SerializedName("DIVERS")
    DIVERS("Divers");

    String label;

    Gender(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }
}
