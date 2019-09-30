package facade;

import comparator.OperationDateComparator;
import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.*;
import service.*;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static enums.DAOEnum.*;

/**
 * A class that works with RefillService, CreditAccountService, BillPaymentService, TransferService, UserAccountService.
 *
 * @see RefillService
 * @see TransferService
 * @see BillPaymentService
 * @see CreditAccountService
 * @see UserAccountService
 */
public class RefillFacade {

    private RefillService refillService;
    private TransferService transferService;
    private BillPaymentService billPaymentService;
    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public RefillFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    /**
     * Method to set UserAccountService object {@link #userAccountService}.
     *
     * @param userAccountService The UserAccountService object.
     * @see UserAccountService
     */
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Method to set RefillService object {@link #refillService}.
     *
     * @param refillService The RefillService object.
     * @see RefillService
     */
    public void setRefillService(RefillService refillService) {
        this.refillService = refillService;
    }

    /**
     * Method to set CreditAccountService object {@link #creditAccountService}.
     *
     * @param creditAccountService The CreditAccountService object.
     * @see CreditAccountService
     */
    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    /**
     * Method to set TransferService object {@link #transferService}.
     *
     * @param transferService The TransferService object.
     * @see TransferService
     */
    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Method to set BillPaymentService object {@link #billPaymentService}.
     *
     * @param billPaymentService The BillPaymentService object.
     * @see BillPaymentService
     */
    public void setBillPaymentService(BillPaymentService billPaymentService) {
        this.billPaymentService = billPaymentService;
    }

    /**
     * Method to refill using RefillOperation object.
     *
     * @param refillOperation The RefillOperation object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see RefillOperation
     */
    public boolean refill(RefillOperation refillOperation) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(refillOperation.getUserId());
        CreditAccount creditAccount = creditAccountService.getById(refillOperation.getUserId());
        if (userAccount.getUserId() > 0 && userAccount.getCredit() && creditAccount.getLimit() >= refillOperation.getAmount()) {
            boolean limitUpdated =
                    creditAccountService.updateBalanceById(creditAccount.getLimit() - refillOperation.getAmount(), refillOperation.getUserId());
            boolean addArrears =
                    creditAccountService.updateArrears(creditAccount.getArrears() + refillOperation.getAmount(), refillOperation.getUserId());
            boolean updated =
                    userAccountService.updateBalanceById(userAccount.getBalance() + refillOperation.getAmount(), refillOperation.getUserId());
            boolean added =
                    refillService.add(refillOperation);
            if (updated && added && limitUpdated && addArrears) {
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    /**
     * Method to get refill operations using RefillPaginationDTO object.
     *
     * @param paginationDTO The RefillPaginationDTO object.
     * @return The RefillPaginationDTO object containing data needed for pagination with refill operations.
     * @see RefillPaginationDTO
     */
    public RefillPaginationDTO getRefillOperations(RefillPaginationDTO paginationDTO) {
        connection = connectionFactory.getConnection();
        refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
        RefillPaginationDTO refillPaginationDTO = refillService.getRefillOperations(paginationDTO);
        ConnectionClosure.close(connection);
        return refillPaginationDTO;
    }

    /**
     * Method to get all operations using AllOperationsDTO object.
     *
     * @param allOperationsDTO The AllOperationsDTO object.
     * @return The AllOperationsDTO object containing necessary data.
     * @see AllOperationsDTO
     */
    public AllOperationsDTO getAllOperations(AllOperationsDTO allOperationsDTO) {
        connection = connectionFactory.getConnection();
        refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
        billPaymentService.setBillPaymentDAO(factory.getBillPaymentDAO(connection, BILL_PAYMENT_JDBC));
        transferService.setTransferDAO(factory.getTransferDAO(connection, TRANSFER_JDBC));

        AllOperationsDTO paginationDTO1 = refillService.getAllOperations(allOperationsDTO);
        AllOperationsDTO paginationDTO2 = billPaymentService.getAllOperations(allOperationsDTO);
        AllOperationsDTO paginationDTO3 = transferService.getAllOperations(allOperationsDTO);

        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(allOperationsDTO.getUserId());
        operationsDTO.setPageSize(allOperationsDTO.getPageSize());

        List<OperationsData> list = new ArrayList<>();
        list.addAll(paginationDTO1.getList());
        list.addAll(paginationDTO2.getList());
        list.addAll(paginationDTO3.getList());
        if (list.size() > 10)
            operationsDTO.setList(list.stream()
                    .sorted((o1, o2) -> new OperationDateComparator()
                            .compare(o1.getDate(), o2.getDate()))
                    .collect(Collectors.toList()).subList(0, 10));
        else
            operationsDTO.setList(list.stream().sorted().collect(Collectors.toList()));

        ConnectionClosure.close(connection);
        return operationsDTO;
    }

    /**
     * Method to get UserAccount object by user id.
     *
     * @param userId The user id.
     * @return The UserAccount object.
     * @see UserAccount
     */
    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
