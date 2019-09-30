package enums;

/**
 *  Mappings that can be used
 *  <li>{@link #REGISTRATION_PAGE}</li>
 *  <li>{@link #LOGIN_PAGE}</li>
 *  <li>{@link #CLIENT_ACCOUNTS_PAGE}</li>
 *  <li>{@link #CREDIT_REQUEST_PAGE}</li>
 *  <li>{@link #CREDIT_REQUEST_ADMIN_PAGE}</li>
 *  <li>{@link #BILL_PAYMENT_PAGE}</li>
 *  <li>{@link #DEPOSIT_PAGE}</li>
 *  <li>{@link #CREDIT_PAGE}</li>
 *  <li>{@link #TRANSFER_PAGE}</li>
 *  <li>{@link #REFILL_PAGE}</li>
 *  <li>{@link #REFILL_LIST_CLIENT_PAGE}</li>
 *  <li>{@link #OPERATION_LIST_CLIENT_PAGE}</li>
 *  <li>{@link #INDEX_PAGE}</li>
 *  <li>{@link #LOGGED_IN_PAGE}</li>
 *  <li>{@link #SUCCESSFUL_PAGE}</li>
 *  <li>{@link #OPEN_DEPOSIT_PAGE}</li>
 *  <li>{@link #LIMIT_REQUEST_PAGE}</li>
 *  <li>{@link #LIMIT_REQUEST_ADMIN_PAGE}</li>
 *  <li>{@link #SUCCESSFUL}</li>
 *  <li>{@link #OPEN_DEPOSIT}</li>
 *  <li>{@link #BILL_PAYMENT}</li>
 *  <li>{@link #CLIENT_ACCOUNTS}</li>
 *  <li>{@link #CREDIT_ACCOUNT}</li>
 *  <li>{@link #CREDIT_REQUEST}</li>
 *  <li>{@link #CREDIT_REQUEST_ADMIN}</li>
 *  <li>{@link #CREDIT_REQUEST_ADMIN_REDIRECT}</li>
 *  <li>{@link #DEPOSIT_ACCOUNT}</li>
 *  <li>{@link #LIMIT_REQUEST}</li>
 *  <li>{@link #LIMIT_REQUEST_ADMIN}</li>
 *  <li>{@link #UPDATE_TERM}</li>
 *  <li>{@link #LIMIT_REQUEST_ADMIN_REDIRECT}</li>
 *  <li>{@link #DEPOSIT}</li>
 *  <li>{@link #CREDIT}</li>
 *  <li>{@link #REFILL}</li>
 *  <li>{@link #TRANSFER}</li>
 *  <li>{@link #HOME}</li>
 *  <li>{@link #HOME_ADMIN}</li>
 *  <li>{@link #HOME_ADMIN_PAGE}</li>
 *  <li>{@link #LOGIN_VIEW}</li>
 *  <li>{@link #REFILL_LIST_CLIENT}</li>
 *  <li>{@link #OPERATION_LIST_CLIENT}</li>
 *  <li>{@link #LOGOUT}</li>
 *  <li>{@link #REGISTRATION_VIEW}</li>
 *  <li>{@link #LOGGED_IN}</li>
 *  <li>{@link #PAY_ARREARS_PAGE}</li>
 *  <li>{@link #PAY_INTEREST_CHARGES_PAGE}</li>
 *  <li>{@link #PAY_ARREARS}</li>
 *  <li>{@link #PAY_INTEREST_CHARGES}</li>
 *  <li>{@link #ERROR}</li>
 */
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

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param name The name of enum constant, which is the identifier used to declare it.
     */
    Mappings(String name) {
        this.name = name;
    }

    /**
     * Gets the value of {@link #name}.
     *
     * @return the value of {@link #name}.
     */
    public String getName() {
        return name;
    }
}
