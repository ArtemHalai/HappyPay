package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import model.LimitRequestAdmin;
import service.CreditAccountService;
import service.LimitRequestService;
import util.ConnectionClosure;

import java.sql.Connection;
import java.util.List;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.LIMIT_JDBC;

/**
 * A class that works with LimitRequestService, CreditAccountService.
 *
 * @see LimitRequestService
 * @see CreditAccountService
 */
public class LimitRequestAdminFacade {

    private LimitRequestService limitRequestService;
    private CreditAccountService creditAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    /**
     * Sole constructor to initialize {@link #factory} and {@link #connectionFactory}.
     *
     * @see DaoFactory
     * @see JDBCConnectionFactory
     */
    public LimitRequestAdminFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
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
     * Method to set LimitRequestService object {@link #limitRequestService}.
     *
     * @param limitRequestService The LimitRequestService object.
     * @see LimitRequestService
     */
    public void setLimitRequestService(LimitRequestService limitRequestService) {
        this.limitRequestService = limitRequestService;
    }

    /**
     * Method to get all LimitRequestAdmin objects by decision.
     *
     * @return The list of LimitRequestAdmin objects.
     * @see LimitRequestAdmin
     */
    public List<LimitRequestAdmin> findAllByDecision(boolean decision) {
        connection = connectionFactory.getConnection();
        limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
        List<LimitRequestAdmin> list = limitRequestService.findAllByDecision(decision);
        ConnectionClosure.close(connection);
        return list;
    }

    /**
     * Method to update limit by user id, decision, amount.
     *
     * @param userId The user id.
     * @param decision The decision.
     * @param amount The amount.
     * @return <code>true</code> if limit was updated; <code>false</code> otherwise.
     */
    public boolean updateLimit(int userId, double amount, boolean decision) {
        connection = connectionFactory.getConnection();
        limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
        creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
        boolean updated = creditAccountService.updateBalanceById(amount, userId);
        deleteRequest(userId);
        ConnectionClosure.close(connection);
        return updated;
    }

    /**
     * Method to delete request by user id.
     *
     * @param userId The user id.
     * @return <code>true</code> if request was deleted; <code>false</code> otherwise.
     */
    public boolean deleteRequest(int userId) {
        connection = connectionFactory.getConnection();
        limitRequestService.setLimitRequestDAO(factory.getLimitRequestDAO(connection, LIMIT_JDBC));
        boolean deleted = limitRequestService.deleteRequest(userId);
        ConnectionClosure.close(connection);
        return deleted;
    }
}
