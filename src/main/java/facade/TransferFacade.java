package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.TransferOperation;
import model.UserAccount;
import service.TransferService;
import service.UserAccountService;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.*;

@Log4j
public class TransferFacade {

    private TransferService transferService;
    private UserAccountService userAccountService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public TransferFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    public boolean transfer(TransferOperation transferOperation) {
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            transferService.setTransferDAO(factory.getTransferDAO(connection, TRANSFER_JDBC));
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            UserAccount recipientAccount = userAccountService.getByAccountNumber(transferOperation.getRecipientAccountNumber());
            UserAccount userAccount = userAccountService.getById(transferOperation.getUserId());

            boolean checkedAndUpdated = checkAndUpdateBalance(transferOperation, connection, recipientAccount, userAccount);
            if (checkedAndUpdated) {
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error("Could not make transfer", e);
        }
        return false;
    }

    private boolean checkAndUpdateBalance(TransferOperation transferOperation, Connection connection, UserAccount recipientAccount, UserAccount userAccount) throws SQLException {
        if (userAccount.getUserId() > 0 && recipientAccount.getUserId() > 0 && userAccount.getValidity().getTime() > System.currentTimeMillis()) {
            boolean updated = false;
            boolean updatedRecipient = false;
            try {
                if (userAccount.getBalance() >= transferOperation.getAmount()) {
                    userAccount.setBalance(userAccount.getBalance() - transferOperation.getAmount());
                } else {
                    return false;
                }
                updated = userAccountService.updateBalanceById(userAccount.getBalance(), transferOperation.getUserId());
                updatedRecipient = userAccountService.updateByAccount(recipientAccount.getBalance() + transferOperation.getAmount(),
                        transferOperation.getRecipientAccountNumber());
            } catch (Exception e) {
                connection.rollback();
                log.error("Could not make transfer", e);
            }
            if (isTransferSuccessful(transferOperation, updated, updatedRecipient, connection)) {
                connection.commit();
                return true;
            }
        }
        return false;
    }

    private boolean isTransferSuccessful(TransferOperation transferOperation, boolean updated, boolean updatedRecipient,
                                         Connection connection) throws SQLException {
        boolean added;
        try {
            added = transferService.add(transferOperation);
        } catch (Exception e) {
            connection.rollback();
            log.error("Could not make transfer", e);
            return false;
        }
        return updated && added && updatedRecipient;
    }

    public UserAccount getUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            userAccount = userAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get user account", e);
        }
        return userAccount;
    }
}
