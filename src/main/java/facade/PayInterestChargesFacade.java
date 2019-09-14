package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.UserAccount;
import service.CreditAccountService;
import service.UserAccountService;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

public class PayInterestChargesFacade {

    private UserAccountService userAccountService;
    private CreditAccountService creditAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public PayInterestChargesFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }

    public boolean checkInterestCharges(int userId) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        if (creditAccountService.getById(userId).getInterestCharges() <= 0) {
            ConnectionClosure.close(connection);
            return false;
        }
        ConnectionClosure.close(connection);
        return true;
    }

    public boolean payInterestCharges(int userId, double amount) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.payById(userId, amount);
        if (userAccount != null && userAccount.getCredit()) {
            CreditAccount creditAccount = creditAccountService.getById(userId);
            if (creditAccount.getInterestCharges() >= amount && creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() - amount, userId)) {
                userAccountService.updateBalanceById(userAccount.getBalance(), userId);
                TransactionManager.commitTransaction(connection);
                return true;
            }
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }
}
