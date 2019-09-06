package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditRequest;
import model.UserAccount;
import service.CreditApprovementService;
import service.UserAccountService;
import util.TransactionManager;

import java.sql.Connection;

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

    public boolean createCreditRequest(int userId, double amount) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        if (userAccount.getCredit()) {
            TransactionManager.rollbackTransaction(connection);
            return false;
        } else {
            CreditRequest creditRequest = new CreditRequest();
            creditRequest.setUserId(userId);
            creditRequest.setDecision(false);
            creditRequest.setAmount(amount);
            boolean created = creditApprovementService.createCreditRequest(creditRequest);
            if (created) {
                TransactionManager.commitTransaction(connection);
                return true;
            } else {
                TransactionManager.rollbackTransaction(connection);
                return false;
            }
        }
    }
}
