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

public class CreditRequestAdminFacade {

    private CreditApprovementService creditApprovementService;
    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
    private Connection connection;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public CreditRequestAdminFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setCreditApprovementService(CreditApprovementService creditApprovementService) {
        this.creditApprovementService = creditApprovementService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public List<CreditRequestAdmin> findAllByDecision(boolean decision) {
        connection = connectionFactory.getConnection();
        creditApprovementService.setCreditApprovementDAO(factory.getCreditApprovementDAO(connection, CREDIT_APPROVEMENT_JDBC));
        List<CreditRequestAdmin> list = creditApprovementService.findAllByDecision(decision);
        ConnectionClosure.close(connection);
        return list;
    }

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

    public boolean deleteRequest(int userId) {
        connection = connectionFactory.getConnection();
        creditApprovementService.setCreditApprovementDAO(factory.getCreditApprovementDAO(connection, CREDIT_APPROVEMENT_JDBC));
        boolean deleted = creditApprovementService.deleteRequest(userId);
        ConnectionClosure.close(connection);
        return deleted;
    }
}
