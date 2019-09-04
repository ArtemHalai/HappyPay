package enums;

public enum Regex {
    NAME_REGEX("^[a-zA-Zа-яА-Я]{2,}$"),
    USERNAME_REGEX("^[a-zA-Z0-9]{5,}$"),
    PHONE_NUMBER_REGEX("^\\+\\d{12}$"),
    PASSWORD_REGEX("^.{8,}$"),
    ACCOUNT_NUMBER_REGEX("^\\d{16,30}$");

    private String name;

    Regex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
