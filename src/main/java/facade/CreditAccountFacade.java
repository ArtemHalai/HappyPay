package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import service.CreditAccountService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;

public class CreditAccountFacade {

    private CreditAccountService creditAccountService;
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

    public CreditAccount getCreditAccount(int userId) {
        connection = connectionFactory.getConnection();
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        CreditAccount creditAccount = creditAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return creditAccount;
    }
}
