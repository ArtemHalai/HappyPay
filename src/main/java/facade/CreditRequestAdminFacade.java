package facade;

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

public class CreditRequestAdminFacade {

    private CreditApprovementService creditApprovementService;
    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
    private Connection connection;
    private JDBCConnectionFactory connectionFactory;

    public CreditRequestAdminFacade() {
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
        creditApprovementService.setDefaultCreditApprovementDAO(connection);
        List<CreditRequestAdmin> list = creditApprovementService.findAllByDecision(decision);
        ConnectionClosure.close(connection);
        return list;
    }

    public boolean updateCreditStatus(int userId, boolean decision, double amount) {
        connection = connectionFactory.getConnection();
        TransactionManager.setRepeatableRead(connection);
        userAccountService.setDefaultUserAccountDAO(connection);
        creditApprovementService.setDefaultCreditApprovementDAO(connection);
        creditAccountService.setDefaultCreditAccountDAO(connection);
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
        creditApprovementService.setDefaultCreditApprovementDAO(connection);
        boolean deleted = creditApprovementService.deleteRequest(userId);
        ConnectionClosure.close(connection);
        return deleted;
    }
}
