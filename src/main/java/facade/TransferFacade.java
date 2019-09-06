package facade;

import enums.Fields;
import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.DepositAccount;
import model.TransferOperation;
import service.CreditAccountService;
import service.DepositAccountService;
import service.TransferService;
import util.TransactionManager;

import java.sql.Connection;

import static enums.DAOEnum.*;

public class TransferFacade {

    private TransferService transferService;
    private DepositAccountService depositAccountService;
    private CreditAccountService creditAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public TransferFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setDepositAccountService(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    public boolean transfer(TransferOperation transferOperation, Fields account) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        transferService.setTransferDAO(factory.getTransferDAO(connection, TRANSFER_JDBC));
        switch (account) {
            case CREDIT:
                return transferFromCreditAccount(transferOperation);
            case DEPOSIT:
                return transferFromDepositAccount(transferOperation);
            default:
                TransactionManager.rollbackTransaction(connection);
                return false;
        }
    }

    private boolean transferFromDepositAccount(TransferOperation transferOperation) {
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        DepositAccount account = depositAccountService.getByAccountNumber(transferOperation.getRecipientAccountNumber());
        if (account.getUserId() < 0) {
            TransactionManager.rollbackTransaction(connection);
            return false;
        }
        DepositAccount depositAccount = depositAccountService.payById(transferOperation.getUserId(),
                transferOperation.getAmount());
        if (depositAccount != null && account.getUserId() > 0) {
            boolean updated = depositAccountService.updateBalanceById(depositAccount.getBalance(), transferOperation.getUserId());
            boolean updatedRecipient = depositAccountService.updateByAccount(account.getBalance() + transferOperation.getAmount(),
                    transferOperation.getRecipientAccountNumber());
            if (isTransferSuccessful(transferOperation, updated, updatedRecipient)) return true;
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    private boolean transferFromCreditAccount(TransferOperation transferOperation) {
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditAccount account = creditAccountService.getByAccountNumber(transferOperation.getRecipientAccountNumber());
        if (account.getUserId() < 0) {
            TransactionManager.rollbackTransaction(connection);
            return false;
        }
        CreditAccount creditAccount = creditAccountService.payById(transferOperation.getUserId(),
                transferOperation.getAmount());
        if (creditAccount != null && account.getUserId() > 0) {
            boolean updated = creditAccountService.updateBalanceById(creditAccount.getBalance(), transferOperation.getUserId());
            boolean updatedRecipient = creditAccountService.updateByAccount(account.getBalance() + transferOperation.getAmount(),
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
}
