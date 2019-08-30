package factories;

import enums.ServiceEnum;

public class ServiceFactory {
    private static volatile ServiceFactory factory = null;

    private static Service billPaymentService = null;
    private static Service clientDetailsService = null;
    private static Service creditAccountService = null;
    private static Service creditApprovementService = null;
    private static Service depositAccountService = null;
    private static Service refillService = null;
    private static Service transferService = null;
    private static Service userAccountService = null;
    private static Service userService = null;

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

    public Service getDAO(ServiceEnum serviceEnum) {

        if (serviceEnum == null)
            return null;
        Service service = null;
        switch (serviceEnum) {
            case BILL_PAYMENT_SERVICE:
                if (billPaymentService == null)
                    billPaymentService = new BillPaymentService();
                service = billPaymentService;
                break;
            case CLIENT_DETAILS_SERVICE:
                if (clientDetailsService == null)
                    clientDetailsService = new ClientDetailsService();
                service = clientDetailsService;
                break;
            case CREDIT_ACCOUNT_SERVICE:
                if (creditAccountService == null)
                    creditAccountService = new CreditAccountService();
                service = creditAccountService;
                break;
            case CREDIT_APPROVEMENT_SERVICE:
                if (creditApprovementService == null)
                    creditApprovementService = new CreditApprovementService();
                service = creditApprovementService;
                break;
            case DEPOSIT_ACCOUNT_SERVICE:
                if (depositAccountService == null)
                    depositAccountService = new DepositAccountService();
                service = depositAccountService;
                break;
            case REFILL_SERVICE:
                if (refillService == null)
                    refillService = new RefillService();
                service = refillService;
                break;
            case TRANSFER_SERVICE:
                if (transferService == null)
                    transferService = new TransferService();
                service = transferService;
                break;
            case USER_ACCOUNT_SERVICE:
                if (userAccountService == null)
                    userAccountService = new UserAccountService();
                service = userAccountService;
                break;
            case USER_SERVICE:
                if (userService == null)
                    userService = new UserService();
                service = userService;
                break;
        }
        return service;
    }

}
