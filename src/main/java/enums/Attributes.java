package enums;

public enum Attributes {
    ERRORS("errors"),
    USER("user"),
    PAGE("page"),
    COUNT("count"),
    PAGE_SIZE("10"),
    NAME("name"),
    ROOT("root"),
    SURNAME("surname"),
    USERNAME("username"),
    PASSWORD("password"),
    PHONE_NUMBER("phone_number"),
    BIRTHDAY("birthday"),
    ACCOUNT("account"),
    TOTAL("total"),
    NOT_ENOUGH_AMOUNT("not_enough_amount");

    private String name;

    Attributes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
