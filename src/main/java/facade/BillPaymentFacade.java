package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.BillPaymentOperation;
import model.UserAccount;
import service.BillPaymentService;
import service.UserAccountService;
import util.TransactionManager;
import util.UserAccountGetter;
import util.UserAccountValidity;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.*;

@Log4j
public class BillPaymentFacade {

    private static final String ERROR = "Could not make bill payment for user with id: %d, and bill number: %d";
    private BillPaymentService billPaymentService;
    private UserAccountService userAccountService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public BillPaymentFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setBillPaymentService(BillPaymentService billPaymentService) {
        this.billPaymentService = billPaymentService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public boolean payBill(BillPaymentOperation billPaymentOperation) {
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            billPaymentService.setBillPaymentDAO(factory.getBillPaymentDAO(connection, BILL_PAYMENT_JDBC));
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            UserAccount userAccount = userAccountService.getById(billPaymentOperation.getUserId());

            boolean checkedAndUpdated = checkAndUpdateBalance(billPaymentOperation, connection, userAccount);
            if (checkedAndUpdated) {
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error(String.format(ERROR, billPaymentOperation.getUserId(), billPaymentOperation.getBillNumber()), e);
        }
        return false;
    }

    private boolean checkAndUpdateBalance(BillPaymentOperation billPaymentOperation, Connection connection, UserAccount userAccount) {
        if (UserAccountValidity.userIdAndValidityAreValid(userAccount) && userAccount.getBalance() >= billPaymentOperation.getAmount()) {
            try {
                userAccount.setBalance(userAccount.getBalance() - billPaymentOperation.getAmount());
                boolean updated = userAccountService.updateBalanceById(userAccount.getBalance(), billPaymentOperation.getUserId());
                boolean added = billPaymentService.add(billPaymentOperation);
                if (updated && added) {
                    connection.commit();
                    return true;
                }
            } catch (Exception e) {
                log.error(String.format(ERROR, billPaymentOperation.getUserId(), billPaymentOperation.getBillNumber()), e);
                return false;
            }
        }
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
