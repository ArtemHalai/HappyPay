package facade;

import enums.Fields;
import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.BillPaymentOperation;
import model.CreditAccount;
import model.DepositAccount;
import service.BillPaymentService;
import service.CreditAccountService;
import service.DepositAccountService;
import util.TransactionManager;

import java.sql.Connection;

import static enums.DAOEnum.*;

public class BillPaymentFacade {

    private BillPaymentService billPaymentService;
    private DepositAccountService depositAccountService;
    private CreditAccountService creditAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public BillPaymentFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setDepositAccountService(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public void setBillPaymentService(BillPaymentService billPaymentService) {
        this.billPaymentService = billPaymentService;
    }

    public boolean payBill(BillPaymentOperation billPaymentOperation, Fields account) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        billPaymentService.setBillPaymentDAO(factory.getBillPaymentDAO(connection, BILL_PAYMENT_JDBC));
        switch (account) {
            case CREDIT:
                return payBillByCreditAccount(billPaymentOperation);
            case DEPOSIT:
                return payBillByDepositAccount(billPaymentOperation);
            default:
                TransactionManager.rollbackTransaction(connection);
                return false;
        }
    }

    private boolean payBillByDepositAccount(BillPaymentOperation billPaymentOperation) {
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        DepositAccount depositAccount = depositAccountService.payById(billPaymentOperation.getUserId(), billPaymentOperation.getAmount());
        if (depositAccount != null) {
            boolean updated = depositAccountService.updateBalanceById(depositAccount.getBalance(), billPaymentOperation.getUserId());
            boolean added = billPaymentService.add(billPaymentOperation);
            if (updated && added) {
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    private boolean payBillByCreditAccount(BillPaymentOperation billPaymentOperation) {
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditAccount creditAccount = creditAccountService.payById(billPaymentOperation.getUserId(), billPaymentOperation.getAmount());
        if (creditAccount != null) {
            boolean updated = creditAccountService.updateBalanceById(creditAccount.getBalance(), billPaymentOperation.getUserId());
            boolean added = billPaymentService.add(billPaymentOperation);
            if (updated && added) {
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }
}
