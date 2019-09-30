package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.DepositAccount;
import model.RefillOperation;
import model.UserAccount;
import model.calculation.DepositCalculator;
import service.DepositAccountService;
import service.RefillService;
import service.UserAccountService;
import util.ConnectionClosure;
import util.DateValidity;
import util.TransactionManager;

import java.sql.Connection;
import java.util.List;

import static enums.AccountDetails.RATE;
import static enums.AccountType.MAIN_ACCOUNT;
import static enums.DAOEnum.*;
import static enums.DepositEnum.YEAR;
import static enums.OperationType.REFILL_DEPOSIT;

/**
 * A class that works with DepositAccountService, UserAccountService, RefillService.
 *
 * @see DepositAccountService
 * @see UserAccountService
 * @see RefillService
 */
public class DepositAccountFacade {

    private DepositAccountService depositAccountService;
    private UserAccountService userAccountService;
    private RefillService refillService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public DepositAccountFacade() {
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
     * Method to set DepositAccountService object {@link #depositAccountService}.
     *
     * @param depositAccountService The DepositAccountService object.
     * @see DepositAccountService
     */
    public void setDepositAccountService(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
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
     * Method to get DepositAccount object by user id.
     *
     * @param userId The user id.
     * @return The DepositAccount object.
     * @see DepositAccount
     */
    public DepositAccount getDepositAccount(int userId) {
        connection = connectionFactory.getConnection();
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        DepositAccount depositAccount = depositAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return depositAccount;
    }

    /**
     * Method to transfer money from deposit to main account using DepositAccount object.
     *
     * @param depositAccount The DepositAccount.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean transferMoneyToAccountBalance(DepositAccount depositAccount) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(depositAccount.getUserId());
        DepositCalculator depositCalculator =
                new DepositCalculator(depositAccount);
        double amount = depositCalculator.calculate();

        if (userAccount.getUserId() > 0) {
            boolean updatedAccountBalance =
                    userAccountService.updateBalanceById(userAccount.getBalance() + amount, depositAccount.getUserId());
            boolean updatedDepositStatus = userAccountService.updateDepositStatusById(depositAccount.getUserId(), false);
            boolean deleted = depositAccountService.deleteDepositAccount(depositAccount.getUserId());

            if (updatedAccountBalance && updatedDepositStatus && deleted) {
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    /**
     * Method to check deposit by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if user doesn't have deposit account; <code>false</code> otherwise.
     */
    public boolean checkDeposit(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        if (userAccountService.getById(userId).getDeposit()) {
            ConnectionClosure.close(connection);
            return false;
        }
        ConnectionClosure.close(connection);
        return true;
    }

    /**
     * Method to open deposit by user id and with given amount.
     *
     * @param userId The user id.
     * @param balance The amount to put on deposit account.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    public boolean openDeposit(int userId, double balance) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        if (userAccount.getUserId() > 0 && userAccount.getBalance() >= balance && !userAccount.getDeposit())
            if (depositCreator(userId, balance, userAccount)) return true;
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    /**
     * Method to open deposit by user id, given amount and UserAccount object.
     *
     * @param userId The user id.
     * @param balance The amount to put on deposit account.
     * @param userAccount The UserAccount object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     */
    private boolean depositCreator(int userId, double balance, UserAccount userAccount) {
        boolean deleted = depositAccountService.deleteDepositAccount(userId);
        if (deleted) {
            DepositAccount depositAccount = new DepositAccount();
            depositAccount.setUserId(userId);
            depositAccount.setBalance(balance);
            depositAccount.setAccountNumber(userAccount.getAccountNumber());
            depositAccount.setRate(RATE.getDetails());
            depositAccount.setTerm(DateValidity.setDepositTerm());
            depositAccount.setDepositEnum(YEAR);
            boolean added = depositAccountService.add(depositAccount);
            boolean updated = userAccountService.updateDepositStatusById(userId, true);
            boolean updateBalance = userAccountService.updateBalanceById(userAccount.getBalance() - balance, userId);

            RefillOperation refillOperation = new RefillOperation();
            refillOperation.setUserId(userId);
            refillOperation.setAmount(balance);
            refillOperation.setOperationType(REFILL_DEPOSIT.getName());
            refillOperation.setSenderAccountType(MAIN_ACCOUNT.getName());
            boolean refillAdded = refillService.add(refillOperation);
            if (added && updated && refillAdded && updateBalance) {
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get all deposit accounts.
     *
     * @return The list of DepositAccount objects.
     * @see DepositAccount
     */
    public List<DepositAccount> getAll() {
        connection = connectionFactory.getConnection();
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        List<DepositAccount> list = depositAccountService.getAll();
        ConnectionClosure.close(connection);
        return list;
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
