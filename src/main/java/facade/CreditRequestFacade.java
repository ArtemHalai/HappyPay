package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.UserAccount;
import service.CreditApprovementService;
import service.UserAccountService;
import util.ConnectionClosure;

import java.sql.Connection;

import static enums.DAOEnum.CREDIT_APPROVEMENT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

public class CreditRequestFacade {

    private UserAccountService userAccountService;
    private CreditApprovementService creditApprovementService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public CreditRequestFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setCreditApprovementService(CreditApprovementService creditApprovementService) {
        this.creditApprovementService = creditApprovementService;
    }

    public boolean createCreditRequest(CreditRequest creditRequest) {
        connection = connectionFactory.getConnection();
        creditApprovementService.setCreditApprovementDAO(factory.getCreditApprovementDAO(connection, CREDIT_APPROVEMENT_JDBC));

        CreditApprovementOperation operation = creditApprovementService.getById(creditRequest.getUserId());
        if (operation.getUserId() < 0 && creditApprovementService.createCreditRequest(creditRequest)) {
            ConnectionClosure.close(connection);
            return true;
        }
        ConnectionClosure.close(connection);
        return false;
    }

    public boolean checkCredit(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        if (userAccountService.getById(userId).isCredit()) {
            ConnectionClosure.close(connection);
            return false;
        }
        ConnectionClosure.close(connection);
        return true;
    }

    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
