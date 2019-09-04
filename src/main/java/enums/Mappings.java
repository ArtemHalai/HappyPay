package enums;

public enum Mappings {

    REGISTRATION_PAGE("/WEB-INF/view/registration.jsp"),
    LOGIN_PAGE("/WEB-INF/view/login.jsp"),
    CLIENT_ACCOUNTS_PAGE("/WEB-INF/view/client_accounts.jsp"),
    CREDIT_REQUEST_PAGE("/WEB-INF/view/credit_request.jsp"),
    CREDIT_REQUEST_ADMIN_PAGE("/WEB-INF/view/credit_request_admin.jsp"),
    BILL_PAYMENT_PAGE("/WEB-INF/view/bill_payment.jsp"),
    DEPOSIT_PAGE("/WEB-INF/view/deposit.jsp"),
    CREDIT_PAGE("/WEB-INF/view/credit.jsp"),
    TRANSFER_PAGE("/WEB-INF/view/transfer.jsp"),
    REFILL_PAGE("/WEB-INF/view/refill.jsp"),
    INDEX_PAGE("/index.jsp"),
    LOGGED_IN_PAGE("/WEB-INF/view/logged_in.jsp"),
    SUCCESSFUL_PAGE("/WEB-INF/view/successful_payment.jsp"),
    UNSUCCESSFUL_PAGE("/WEB-INF/view/unsuccessful_payment.jsp"),
    SUCCESSFUL("successful"),
    UNSUCCESSFUL("unsuccessful"),
    BILL_PAYMENT("bill_payment"),
    CLIENT_DETAILS("client_details"),
    CLIENT_ACCOUNTS("client_accounts"),
    CREDIT_ACCOUNT("credit_account"),
    CREDIT_APPROVEMENT("credit_approvement"),
    CREDIT_REQUEST("credit_request"),
    CREDIT_REQUEST_ADMIN("credit_request_admin"),
    DEPOSIT_ACCOUNT("deposit_account"),
    DEPOSIT("deposit"),
    CREDIT("credit"),
    REFILL("refill"),
    TRANSFER("transfer"),
    USER_ACCOUNT("user_account"),
    HOME("home"),
    HOME_ADMIN("home_admin"),
    HOME_ADMIN_PAGE("/WEB-INF/view_admin/home_admin.jsp"),
    LOGIN_VIEW("login"),
    LOGOUT("logout"),
    REGISTRATION_VIEW("registration"),
    LOGGED_IN("logged_in"),
    ERROR("errors");

    private String name;

    Mappings(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
