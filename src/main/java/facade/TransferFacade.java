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

public class TransferFacade {

    private TransferService transferService;
    private UserAccountService userAccountService;
    private Connection connection;
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

    private boolean isTransferSuccessful(TransferOperation transferOperation, boolean updated, boolean updatedRecipient) {
        boolean added = transferService.add(transferOperation);
        if (updated && added && updatedRecipient) {
            TransactionManager.commitTransaction(connection);
            return true;
        }
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
