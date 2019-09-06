package facade;

import enums.Fields;
import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.DepositAccount;
import model.RefillOperation;
import service.CreditAccountService;
import service.DepositAccountService;
import service.RefillService;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;

import static enums.DAOEnum.*;

public class RefillFacade {

    private RefillService refillService;
    private DepositAccountService depositAccountService;
    private CreditAccountService creditAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public RefillFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setRefillService(RefillService refillService) {
        this.refillService = refillService;
    }

    public void setDepositAccountService(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public boolean refill(RefillOperation refillOperation, Fields account) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        refillService.setRefillDAO(factory.getRefillDAO(connection, REFILL_JDBC));
        switch (account) {
            case CREDIT:
                return refillCreditAccount(refillOperation);
            case DEPOSIT:
                return refillDepositAccount(refillOperation);
            default:
                ConnectionClosure.close(connection);
                return false;
        }
    }

    private boolean refillDepositAccount(RefillOperation refillOperation) {
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        DepositAccount depositAccountSender = depositAccountService.getByAccountAndIban(refillOperation);
        if (depositAccountSender == null) {
            TransactionManager.rollbackTransaction(connection);
            return false;
        }
        DepositAccount depositAccountRecipient = depositAccountService.getById(refillOperation.getUserId());
        boolean updatedSender = depositAccountService.updateByAccount(depositAccountSender.getBalance(), refillOperation.getAccountNumber());
        boolean updatedRecipient = depositAccountService.updateBalanceById(refillOperation.getAmount() + depositAccountRecipient.getBalance(),
                refillOperation.getUserId());
        if (updatedRecipient && updatedSender) {
            TransactionManager.commitTransaction(connection);
            return true;
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    private boolean refillCreditAccount(RefillOperation refillOperation) {
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditAccount creditAccountSender = creditAccountService.getByAccountAndIban(refillOperation);
        if (creditAccountSender == null) {
            TransactionManager.rollbackTransaction(connection);
            return false;
        }
        CreditAccount creditAccountRecipient = creditAccountService.getById(refillOperation.getUserId());
        boolean updatedSender = creditAccountService.updateByAccount(creditAccountSender.getBalance(), refillOperation.getAccountNumber());
        boolean updatedRecipient = creditAccountService.updateBalanceById(refillOperation.getAmount() + creditAccountRecipient.getBalance(),
                refillOperation.getUserId());
        if (updatedRecipient && updatedSender) {
            TransactionManager.commitTransaction(connection);
            return true;
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }
}
