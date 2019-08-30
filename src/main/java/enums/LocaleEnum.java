package enums;

public enum LocaleEnum {

    RU("ru"),
    UA("ua"),
    EN("en");

    private String name;

    LocaleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
