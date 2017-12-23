package de.vandermeer.skb.interfaces.transformers.textformat;

public enum BgColor {
    BLACK("40"), RED("41"), GREEN("42"), YELLOW("43"), BLUE("44"), MAGENTA("45"), CYAN("46"), WHITE("47"), NONE("");
    private final String code;
    BgColor(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}


