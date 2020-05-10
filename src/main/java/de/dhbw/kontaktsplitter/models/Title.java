package de.dhbw.kontaktsplitter.models;

public class Title {
    private String title;

    public Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean matches(String[] inputTokens, int startIndex) {
        String[] titleTokens = title.split(" ");
        if(startIndex + titleTokens.length < inputTokens.length) {
            for(String token : titleTokens) {
                if(token.equalsIgnoreCase(inputTokens[startIndex])) {
                    startIndex++;
                }
                else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    public int getLength() {
        return title.split(" ").length;
    }

    @Override
    public String toString() {
        return title;
    }
}
