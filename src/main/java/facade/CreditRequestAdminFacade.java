package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.CreditRequestAdmin;
import model.UserAccount;
import service.CreditAccountService;
import service.CreditApprovementService;
import service.UserAccountService;
import util.ConnectionClosure;
import util.TransactionManager;

import java.sql.Connection;
import java.util.List;

import static enums.AccountDetails.CREDIT_RATE;
import static enums.DAOEnum.*;

/**
 * A class that works with CreditApprovementService, CreditAccountService, UserAccountService.
 *
 * @see CreditApprovementService
 * @see CreditAccountService
 * @see UserAccountService
 */
public class CreditRequestAdminFacade {

    private CreditApprovementService creditApprovementService;
    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public CreditRequestAdminFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
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
     * Method to set CreditAccountService object {@link #creditAccountService}.
     *
     * @param creditAccountService The CreditAccountService object.
     * @see CreditAccountService
     */
    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
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
     * Method to get all CreditRequestAdmin objects by decision.
     *
     * @return The list of CreditRequestAdmin objects.
     * @see CreditRequestAdmin
     */
    public List<CreditRequestAdmin> findAllByDecision(boolean decision) {
        connection = connectionFactory.getConnection();
        creditApprovementService.setCreditApprovementDAO(factory.getCreditApprovementDAO(connection, CREDIT_APPROVEMENT_JDBC));
        List<CreditRequestAdmin> list = creditApprovementService.findAllByDecision(decision);
        ConnectionClosure.close(connection);
        return list;
    }

    /**
     * Method to update credit status by user id, decision, amount.
     *
     * @param userId The user id.
     * @param decision The decision.
     * @param amount The amount.
     * @return <code>true</code> if credit status was updated; <code>false</code> otherwise.
     */
    public boolean updateCreditStatus(int userId, boolean decision, double amount) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
        creditApprovementService.setCreditApprovementDAO(factory.getCreditApprovementDAO(connection, CREDIT_APPROVEMENT_JDBC));
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        UserAccount userAccount = userAccountService.getById(userId);
        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setUserId(userId);
        creditAccount.setAccountNumber(userAccount.getAccountNumber());
        creditAccount.setLimit(amount);
        creditAccount.setRate(CREDIT_RATE.getDetails());
        boolean added = creditAccountService.add(creditAccount);
        boolean updated = userAccountService.updateCreditStatusById(userId, decision);
        boolean updatedDecision = creditApprovementService.updateDecision(decision, userId);
        if(updated && updatedDecision && added){
            TransactionManager.commitTransaction(connection);
            return true;
        }
        TransactionManager.rollbackTransaction(connection);
        return false;
    }

    /**
     * Method to delete request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if request was deleted; <code>false</code> otherwise.
     */
    public boolean deleteRequest(int userId) {
        connection = connectionFactory.getConnection();
        creditApprovementService.setCreditApprovementDAO(factory.getCreditApprovementDAO(connection, CREDIT_APPROVEMENT_JDBC));
        boolean deleted = creditApprovementService.deleteRequest(userId);
        ConnectionClosure.close(connection);
        return deleted;
    }
}
