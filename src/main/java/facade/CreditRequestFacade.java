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

/**
 * A class that works with CreditApprovementService, UserAccountService.
 *
 * @see CreditApprovementService
 * @see UserAccountService
 */
public class CreditRequestFacade {

    private UserAccountService userAccountService;
    private CreditApprovementService creditApprovementService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public CreditRequestFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    /**
     * Method to set UserAccountService object {@link #userAccountService}.
     *
     * @param userAccountService The UserAccountService object.
     * @see UserAccountService
     */
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Method to set CreditApprovementService object {@link #creditApprovementService}.
     *
     * @param creditApprovementService The CreditApprovementService object.
     * @see CreditApprovementService
     */
    public void setCreditApprovementService(CreditApprovementService creditApprovementService) {
        this.creditApprovementService = creditApprovementService;
    }

    /**
     * Method to create credit request using CreditRequest object.
     *
     * @param creditRequest The CreditRequest object.
     * @return <code>true</code> if credit request was created; <code>false</code> otherwise.
     * @see CreditRequest
     */
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

    /**
     * Method to check credit by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if user has credit account; <code>false</code> otherwise.
     */
    public boolean checkCredit(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        if (userAccountService.getById(userId).getCredit()) {
            ConnectionClosure.close(connection);
            return false;
        }
        ConnectionClosure.close(connection);
        return true;
    }

    /**
     * Method to get UserAccount object by user id.
     *
     * @param userId The user id.
     * @return The UserAccount object.
     * @see UserAccount
     */
    public UserAccount getUserAccount(int userId) {
        connection = connectionFactory.getConnection();
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        ConnectionClosure.close(connection);
        return userAccount;
    }
}
