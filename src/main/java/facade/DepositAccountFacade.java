package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.DepositAccount;
import service.DepositAccountService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.DEPOSIT_ACCOUNT_JDBC;

public class DepositAccountFacade {

    private DepositAccountService depositAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public DepositAccountFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setDepositAccountService(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    public DepositAccount getDepositAccount(int userId) {
        connection = connectionFactory.getConnection();
        depositAccountService.setDepositAccountDAO(factory.getDepositAccountDAO(connection, DEPOSIT_ACCOUNT_JDBC));
        DepositAccount depositAccount = depositAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return depositAccount;
    }
}
