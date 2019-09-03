package enums;

public enum Mappings {

    REGISTRATION_PAGE("/WEB-INF/view/registration.jsp"),
    LOGIN_PAGE("/WEB-INF/view/login.jsp"),
    INDEX_PAGE("/index.jsp"),
    LOGGED_IN_PAGE("/WEB-INF/view/logged_in.jsp"),
    SUCCESSFUL_PAGE("/WEB-INF/view/successful_payment.jsp"),
    UNSUCCESSFUL_PAGE("/WEB-INF/view/unsuccessful_payment.jsp"),
    SUCCESSFUL("successful"),
    UNSUCCESSFUL("unsuccessful"),
    BILL_PAYMENT("bill_payment"),
    CLIENT_DETAILS("client_details"),
    CREDIT_ACCOUNT("credit_account"),
    CREDIT_APPROVEMENT("credit_approvement"),
    DEPOSIT_ACCOUNT("deposit_account"),
    REFILL("refill"),
    TRANSFER("transfer"),
    USER_ACCOUNT("user_account"),
    HOME("home"),
    LOGIN_VIEW("login"),
    REGISTRATION_VIEW("registration"),
    LOGGED_IN("logged_in");

    private String name;

    Mappings(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
