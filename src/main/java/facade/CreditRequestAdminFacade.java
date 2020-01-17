package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.CreditAccount;
import model.CreditRequestAdmin;
import model.UserAccount;
import service.CreditAccountService;
import service.CreditApprovementService;
import service.UserAccountService;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static enums.AccountDetails.CREDIT_RATE;

@Log4j
public class CreditRequestAdminFacade {

    private CreditApprovementService creditApprovementService;
    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
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
        List<CreditRequestAdmin> list = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            creditApprovementService.setDefaultCreditApprovementDAO(connection);
            list = creditApprovementService.findAllByDecision(decision);
        } catch (SQLException e) {
            log.error(String.format("Could not find all credit requests by decision: %s", decision), e);
        }
        return list;
    }

    public boolean updateCreditStatus(int userId, boolean decision, double amount) {
        try (Connection connection = connectionFactory.getConnection()) {
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

            if (updateCreditStatusAndUpdateDecision(creditAccount, userId, decision)) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error(String.format("Could not update credit status for user with id: %d and decision: %s", userId, decision), e);
        }
        return false;
    }

    private boolean updateCreditStatusAndUpdateDecision(CreditAccount creditAccount, int userId, boolean decision) {
        boolean added;
        boolean updated;
        boolean updatedDecision;
        try {
            added = creditAccountService.add(creditAccount);
            updated = userAccountService.updateCreditStatusById(userId, decision);
            updatedDecision = creditApprovementService.updateDecision(decision, userId);
        } catch (Exception e) {
            log.error(String.format("Could not update credit status for user with id: %d and decision: %s", userId, decision), e);
            return false;
        }
        return added && updated && updatedDecision;
    }

    public boolean deleteRequest(int userId) {
        boolean deleted = false;
        try (Connection connection = connectionFactory.getConnection()) {
            creditApprovementService.setDefaultCreditApprovementDAO(connection);
            deleted = creditApprovementService.deleteRequest(userId);
        } catch (SQLException e) {
            log.error(String.format("Could not delete request for user with id: %d", userId), e);
        }
        return deleted;
    }
}
