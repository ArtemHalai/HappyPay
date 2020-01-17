package factories;

import service.*;

public class ServiceFactory {

    private static BillPaymentService billPaymentService;
    private static ClientDetailsService clientDetailsService = null;
    private static CreditAccountService creditAccountService = null;
    private static CreditApprovementService creditApprovementService = null;
    private static DepositAccountService depositAccountService = null;
    private static RefillService refillService = null;
    private static TransferService transferService = null;
    private static UserAccountService userAccountService = null;
    private static UserService userService = null;
    private static LimitRequestService limitRequestService = null;

    private ServiceFactory() {

    }

    public static BillPaymentService getBillPaymentService() {
        if (billPaymentService == null)
            billPaymentService = new BillPaymentService();
        return billPaymentService;
    }

    public static LimitRequestService getLimitRequestService() {
        if (limitRequestService == null)
            limitRequestService = new LimitRequestService();
        return limitRequestService;
    }

    public static ClientDetailsService getClientDetailsService() {
        if (clientDetailsService == null)
            clientDetailsService = new ClientDetailsService();
        return clientDetailsService;
    }

    public static CreditAccountService getCreditAccountService() {
        if (creditAccountService == null)
            creditAccountService = new CreditAccountService();
        return creditAccountService;
    }

    public static CreditApprovementService getCreditApprovementService() {
        if (creditApprovementService == null)
            creditApprovementService = new CreditApprovementService();
        return creditApprovementService;
    }

    public static DepositAccountService getDepositAccountService() {
        if (depositAccountService == null)
            depositAccountService = new DepositAccountService();
        return depositAccountService;
    }

    public static RefillService getRefillService() {
        if (refillService == null)
            refillService = new RefillService();
        return refillService;
    }

    public static TransferService getTransferService() {
        if (transferService == null)
            transferService = new TransferService();
        return transferService;
    }

    public static UserAccountService getUserAccountService() {
        if (userAccountService == null)
            userAccountService = new UserAccountService();
        return userAccountService;
    }

    public static UserService getUserService() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }
}
