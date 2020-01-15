package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.UserAccount;
import service.CreditApprovementService;
import service.UserAccountService;
import util.UserAccountGetter;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class CreditRequestFacade {

    private static final String ERROR = "Could not create credit request for user with id: %d";
    private UserAccountService userAccountService;
    private CreditApprovementService creditApprovementService;
    private JDBCConnectionFactory connectionFactory;

    public CreditRequestFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setCreditApprovementService(CreditApprovementService creditApprovementService) {
        this.creditApprovementService = creditApprovementService;
    }

    public boolean createCreditRequest(CreditRequest creditRequest) {
        try (Connection connection = connectionFactory.getConnection()) {
            creditApprovementService.setDefaultCreditApprovementDAO(connection);

            CreditApprovementOperation operation = creditApprovementService.getById(creditRequest.getUserId());
            if (operation.getUserId() < 0 && creditApprovementService.createCreditRequest(creditRequest)) {
                return true;
            }
        } catch (SQLException e) {
            log.error(String.format(ERROR, creditRequest.getUserId()), e);
        }
        return false;
    }

    public boolean checkCredit(int userId) {
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setDefaultUserAccountDAO(connection);
            if (userAccountService.getById(userId).isCredit()) {
                return false;
            }
        } catch (SQLException e) {
            log.error(String.format("Could not check credit for user with id: %d", userId), e);
        }
        return true;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
