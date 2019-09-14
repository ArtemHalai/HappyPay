package enums;

public enum Mappings {

    REGISTRATION_PAGE("/WEB-INF/view/registration.jsp"),
    LOGIN_PAGE("/WEB-INF/view/login.jsp"),
    CLIENT_ACCOUNTS_PAGE("/WEB-INF/view/client_accounts.jsp"),
    CREDIT_REQUEST_PAGE("/WEB-INF/view/credit_request.jsp"),
    CREDIT_REQUEST_ADMIN_PAGE("/WEB-INF/view_admin/credit_request_admin.jsp"),
    BILL_PAYMENT_PAGE("/WEB-INF/view/bill_payment.jsp"),
    DEPOSIT_PAGE("/WEB-INF/view/deposit.jsp"),
    CREDIT_PAGE("/WEB-INF/view/credit.jsp"),
    TRANSFER_PAGE("/WEB-INF/view/transfer.jsp"),
    REFILL_PAGE("/WEB-INF/view/refill.jsp"),
    REFILL_LIST_CLIENT_PAGE("/WEB-INF/view/refill_list_client.jsp"),
    OPERATION_LIST_CLIENT_PAGE("/WEB-INF/view/operation_list_client.jsp"),
    INDEX_PAGE("/index.jsp"),
    LOGGED_IN_PAGE("/WEB-INF/view/logged_in.jsp"),
    SUCCESSFUL_PAGE("/WEB-INF/view/successful.jsp"),
    OPEN_DEPOSIT_PAGE("/WEB-INF/view/open_deposit.jsp"),
    LIMIT_REQUEST_PAGE("/WEB-INF/view/limit_request.jsp"),
    LIMIT_REQUEST_ADMIN_PAGE("/WEB-INF/view_admin/limit_request_admin.jsp"),
    SUCCESSFUL("successful"),
    OPEN_DEPOSIT("open_deposit"),
    BILL_PAYMENT("bill_payment"),
    CLIENT_ACCOUNTS("client_accounts"),
    CREDIT_ACCOUNT("credit_account"),
    CREDIT_REQUEST("credit_request"),
    CREDIT_REQUEST_ADMIN("credit_request_admin"),
    CREDIT_REQUEST_ADMIN_REDIRECT("credit_request_admin"),
    DEPOSIT_ACCOUNT("deposit_account"),
    LIMIT_REQUEST("limit_request"),
    LIMIT_REQUEST_ADMIN("limit_request_admin"),
    UPDATE_TERM("update_term"),
    LIMIT_REQUEST_ADMIN_REDIRECT("limit_request_admin"),
    DEPOSIT("deposit"),
    CREDIT("credit"),
    REFILL("refill"),
    TRANSFER("transfer"),
    HOME("home"),
    HOME_ADMIN("home_admin"),
    HOME_ADMIN_PAGE("/WEB-INF/view_admin/home_admin.jsp"),
    LOGIN_VIEW("login"),
    REFILL_LIST_CLIENT("refill_list_client"),
    OPERATION_LIST_CLIENT("operation_list_client"),
    LOGOUT("logout"),
    REGISTRATION_VIEW("registration"),
    LOGGED_IN("logged_in"),
    PAY_ARREARS_PAGE("/WEB-INF/view/pay_arrears.jsp"),
    PAY_INTEREST_CHARGES_PAGE("/WEB-INF/view/pay_interest_charges.jsp"),
    PAY_ARREARS("pay_arrears"),
    PAY_INTEREST_CHARGES("pay_interest_charges"),
    ERROR("error");

    private String name;

    Mappings(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
