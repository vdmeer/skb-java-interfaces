package de.vandermeer.skb.interfaces.transformers.textformat;

public enum FgColor {
    BLACK("30"), RED("31"), GREEN("32"), YELLOW("33"), BLUE("34"), MAGENTA("35"), CYAN("36"), WHITE("37"), NONE("");
    private final String code; 
    FgColor(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}