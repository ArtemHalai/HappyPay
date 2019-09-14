package enums;

public enum  DepositEnum {

    YEAR("YEAR");

    private String name;

    DepositEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
