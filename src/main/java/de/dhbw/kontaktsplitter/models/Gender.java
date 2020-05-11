package de.dhbw.kontaktsplitter.models;

/**
 * @author Nico Dreher
 */
public enum Gender {
    /**
     * No specific gender
     */
    NONE,
    MALE,
    FEMALE,
    DIVERS;

    public String toDisplayString(){
        switch (this){
            case DIVERS:
                return "divers";
            case FEMALE:
                return "weiblich";
            case MALE:
                return "männlich";
            default:
                return "keine Angabe";
        }
    }

    public static Gender fromDisplayString(String displayString){
        switch (displayString){
            case "divers":
                return Gender.DIVERS;
            case "weiblich":
                return Gender.FEMALE;
            case "männlich":
                return Gender.MALE;
            default:
                return Gender.NONE;
        }
    }
}
