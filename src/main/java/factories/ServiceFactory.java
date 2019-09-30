package factories;

import service.*;

/**
 * A class that provides factory to get necessary service.
 */
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

    /**
     * Private constructor to prevent
     * the instantiation of this class directly
     */
    private ServiceFactory() {

    }

    /**
     * Gets the instance of factory.
     *
     * @return the instance of {@link #factory}.
     */
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

    /**
     * Gets the BillPaymentService.
     *
     * @return the instance of BillPaymentService.
     *
     * @see BillPaymentService
     */
    public BillPaymentService getBillPaymentService() {
        if (billPaymentService == null)
            billPaymentService = new BillPaymentService();
        return billPaymentService;
    }

    /**
     * Gets the LimitRequestService.
     *
     * @return the instance of LimitRequestService.
     *
     * @see LimitRequestService
     */
    public LimitRequestService getLimitRequestService() {
        if (limitRequestService == null)
            limitRequestService = new LimitRequestService();
        return limitRequestService;
    }

    /**
     * Gets the ClientDetailsService.
     *
     * @return the instance of ClientDetailsService.
     *
     * @see ClientDetailsService
     */
    public ClientDetailsService getClientDetailsService() {
        if (clientDetailsService == null)
            clientDetailsService = new ClientDetailsService();
        return clientDetailsService;
    }

    /**
     * Gets the CreditAccountService.
     *
     * @return the instance of CreditAccountService.
     *
     * @see CreditAccountService
     */
    public CreditAccountService getCreditAccountService() {
        if (creditAccountService == null)
            creditAccountService = new CreditAccountService();
        return creditAccountService;
    }

    /**
     * Gets the CreditApprovementService.
     *
     * @return the instance of CreditApprovementService.
     *
     * @see CreditApprovementService
     */
    public CreditApprovementService getCreditApprovementService() {
        if (creditApprovementService == null)
            creditApprovementService = new CreditApprovementService();
        return creditApprovementService;
    }

    /**
     * Gets the DepositAccountService.
     *
     * @return the instance of DepositAccountService.
     *
     * @see DepositAccountService
     */
    public DepositAccountService getDepositAccountService() {
        if (depositAccountService == null)
            depositAccountService = new DepositAccountService();
        return depositAccountService;
    }

    /**
     * Gets the RefillService.
     *
     * @return the instance of RefillService.
     *
     * @see RefillService
     */
    public RefillService getRefillService() {
        if (refillService == null)
            refillService = new RefillService();
        return refillService;
    }

    /**
     * Gets the TransferService.
     *
     * @return the instance of TransferService.
     *
     * @see TransferService
     */
    public TransferService getTransferService() {

        if (transferService == null)
            transferService = new TransferService();
        return transferService;
    }

    /**
     * Gets the UserAccountService.
     *
     * @return the instance of UserAccountService.
     *
     * @see UserAccountService
     */
    public UserAccountService getUserAccountService() {

        if (userAccountService == null)
            userAccountService = new UserAccountService();
        return userAccountService;
    }

    /**
     * Gets the UserService.
     *
     * @return the instance of UserService.
     *
     * @see UserService
     */
    public UserService getUserService() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }
}
