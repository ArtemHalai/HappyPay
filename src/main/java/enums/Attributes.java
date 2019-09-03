package enums;

public enum Attributes {
    ERRORS("errors"),
    USER("user"),
    PAGE("page"),
    COUNT("count"),
    PAGE_SIZE("10"),
    NAME("name"),
    SURNAME("surname"),
    USERNAME("username"),
    PASSWORD("password"),
    PHONE_NUMBER("phone_number"),
    BIRTHDAY("birthday"),
    CARD_NUMBER("card_number");

    private String name;

    Attributes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
