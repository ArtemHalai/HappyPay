package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.UserAccount;
import model.calculation.CreditCalculator;
import service.CreditAccountService;
import service.UserAccountService;
import util.ConnectionClosure;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

public class CreditAccountFacade {

    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public CreditAccountFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public CreditAccount getCreditAccount(int userId) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditAccount creditAccount = creditAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return creditAccount;
    }

    public List<CreditAccount> getAll() {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        List<CreditAccount> list = creditAccountService.getAll();
        ConnectionClosure.close(connection);
        return list;
    }

    public boolean checkArrears(int userId) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        if (creditAccountService.getById(userId).getArrears() <= 0) {
            ConnectionClosure.close(connection);
            return false;
        }
        ConnectionClosure.close(connection);
        return true;
    }

    public boolean updateInterestCharges(CreditAccount creditAccount) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditCalculator creditCalculator = new CreditCalculator(creditAccount);
        double amount = creditCalculator.calculate();
        boolean updated = creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() + amount, creditAccount.getUserId());
        ConnectionClosure.close(connection);
        return updated;
    }

    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
