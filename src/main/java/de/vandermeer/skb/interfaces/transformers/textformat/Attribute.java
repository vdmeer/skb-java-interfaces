package de.vandermeer.skb.interfaces.transformers.textformat;

public enum Attribute {
    NORMAL("0"), BOLD("1"), UNDERLINE("4"), BLINK("5"), REVERSE("7"), HIDDEN("8"), NONE("");
    private final String code; 
    Attribute(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
