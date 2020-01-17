package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.TransferOperation;
import model.UserAccount;
import service.TransferService;
import service.UserAccountService;
import util.TransactionManager;
import util.UserAccountGetter;
import util.UserAccountValidity;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class TransferFacade {

    private static final String ERROR = "Could not make transfer for user with id: %d, and recipient account number: %d";
    private TransferService transferService;
    private UserAccountService userAccountService;
    private JDBCConnectionFactory connectionFactory;

    public TransferFacade() {
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
            transferService.setDefaultTransferDAO(connection);
            userAccountService.setDefaultUserAccountDAO(connection);
            UserAccount recipientAccount = userAccountService.getByAccountNumber(transferOperation.getRecipientAccountNumber());
            UserAccount userAccount = userAccountService.getById(transferOperation.getUserId());

            boolean checkedAndUpdated = checkAndUpdateBalance(transferOperation, recipientAccount, userAccount);
            if (checkedAndUpdated) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error(String.format(ERROR, transferOperation.getUserId(), transferOperation.getRecipientAccountNumber()), e);
        }
        return false;
    }

    private boolean checkAndUpdateBalance(TransferOperation transferOperation, UserAccount recipientAccount, UserAccount userAccount) {
        if (UserAccountValidity.checkUserIdAndValidityAreValid(userAccount) && recipientAccount.getUserId() > 0
                && userAccount.getBalance() >= transferOperation.getAmount()) {
            try {
                userAccount.setBalance(userAccount.getBalance() - transferOperation.getAmount());
                boolean updated = userAccountService.updateBalanceById(userAccount.getBalance(), transferOperation.getUserId());
                boolean updatedRecipient = userAccountService.updateByAccount(recipientAccount.getBalance() + transferOperation.getAmount(),
                        transferOperation.getRecipientAccountNumber());
                boolean added = transferService.add(transferOperation);
                if (updated && added && updatedRecipient) {
                    return true;
                }
            } catch (Exception e) {
                log.error(String.format(ERROR, transferOperation.getUserId(), transferOperation.getRecipientAccountNumber()), e);
            }
        }
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
