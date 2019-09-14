package factories;

import service.*;

public class ServiceFactory {
    private static volatile ServiceFactory factory = null;

    private static BillPaymentService billPaymentService = null;
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

    public static ServiceFactory getInstance() {
        if (factory == null) {
            synchronized (ServiceFactory.class) {
                if (factory == null) {
                    factory = new ServiceFactory();
                }
            }
        }
        return factory;
    }

    public BillPaymentService getBillPaymentService() {
        if (billPaymentService == null)
            billPaymentService = new BillPaymentService();
        return billPaymentService;
    }

    public LimitRequestService getLimitRequestService() {
        if (limitRequestService == null)
            limitRequestService = new LimitRequestService();
        return limitRequestService;
    }

    public ClientDetailsService getClientDetailsService() {
        if (clientDetailsService == null)
            clientDetailsService = new ClientDetailsService();
        return clientDetailsService;
    }

    public CreditAccountService getCreditAccountService() {
        if (creditAccountService == null)
            creditAccountService = new CreditAccountService();
        return creditAccountService;
    }

    public CreditApprovementService getCreditApprovementService() {
        if (creditApprovementService == null)
            creditApprovementService = new CreditApprovementService();
        return creditApprovementService;
    }

    public DepositAccountService getDepositAccountService() {
        if (depositAccountService == null)
            depositAccountService = new DepositAccountService();
        return depositAccountService;
    }

    public RefillService getRefillService() {
        if (refillService == null)
            refillService = new RefillService();
        return refillService;
    }

    public TransferService getTransferService() {

        if (transferService == null)
            transferService = new TransferService();
        return transferService;
    }

    public UserAccountService getUserAccountService() {

        if (userAccountService == null)
            userAccountService = new UserAccountService();
        return userAccountService;
    }

    public UserService getUserService() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }
}
