package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.TransferOperation;
import model.UserAccount;
import service.TransferService;
import service.UserAccountService;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;

import static enums.DAOEnum.*;

/**
 * A class that works with TransferService, UserAccountService.
 *
 * @see TransferService
 * @see UserAccountService
 */
public class TransferFacade {

    private TransferService transferService;
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
    public TransferFacade() {
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
     * Method to set TransferService object {@link #transferService}.
     *
     * @param transferService The TransferService object.
     * @see TransferService
     */
    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Method to transfer using TransferOperation object.
     *
     * @param transferOperation The TransferOperation object.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see TransferOperation
     * {@link #isTransferSuccessful(TransferOperation, boolean, boolean)}
     */
    public boolean transfer(TransferOperation transferOperation) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        transferService.setTransferDAO(factory.getTransferDAO(connection, TRANSFER_JDBC));
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount recipientAccount = userAccountService.getByAccountNumber(transferOperation.getRecipientAccountNumber());
        UserAccount userAccount = userAccountService.payById(transferOperation.getUserId(), transferOperation.getAmount());
        if (userAccount != null && recipientAccount.getUserId() > 0 && userAccount.getValidity().getTime() > System.currentTimeMillis()) {
            boolean updated = userAccountService.updateBalanceById(userAccount.getBalance(), transferOperation.getUserId());
            boolean updatedRecipient = userAccountService.updateByAccount(recipientAccount.getBalance() + transferOperation.getAmount(),
                    transferOperation.getRecipientAccountNumber());
            if (isTransferSuccessful(transferOperation, updated, updatedRecipient)) return true;
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    /**
     * Method to transfer using TransferOperation object.
     *
     * @param transferOperation The TransferOperation object.
     * @param updated The boolean value representing successful result of updating balance by id.
     * @param updatedRecipient The boolean value representing successful result of updating balance by account.
     * @return <code>true</code> if operation was successful; <code>false</code> otherwise.
     * @see TransferOperation
     */
    private boolean isTransferSuccessful(TransferOperation transferOperation, boolean updated, boolean updatedRecipient) {
        boolean added = transferService.add(transferOperation);
        if (updated && added && updatedRecipient) {
            TransactionManager.commitTransaction(connection);
            return true;
        }
        return false;
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
