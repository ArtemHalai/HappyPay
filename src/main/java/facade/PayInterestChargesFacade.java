package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.CreditAccount;
import model.UserAccount;
import service.CreditAccountService;
import service.UserAccountService;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

@Log4j
public class PayInterestChargesFacade {

    private UserAccountService userAccountService;
    private CreditAccountService creditAccountService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public PayInterestChargesFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public UserAccount getUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            userAccount = userAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get user account", e);
        }
        return userAccount;
    }

    public boolean checkInterestCharges(int userId) {
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
            if (creditAccountService.getById(userId).getInterestCharges() <= 0) {
                return false;
            }
        } catch (SQLException e) {
            log.error("Could not check interest charges", e);
        }
        return true;
    }

    public boolean payInterestCharges(int userId, double amount) {
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            userAccountService.setUserAccountDAO(factory.getUserAccountDAO(connection, USER_ACCOUNT_JDBC));
            creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
            UserAccount userAccount = userAccountService.getById(userId);

            if (userAccount.getUserId() > 0 && userAccount.isCredit()) {
                if (userAccount.getBalance() >= amount) {
                    userAccount.setBalance(userAccount.getBalance() - amount);
                } else {
                    return false;
                }

                CreditAccount creditAccount = creditAccountService.getById(userId);

                boolean checkedAndUpdated = checkAndUpdateInterestCharges(userId, amount, connection, userAccount, creditAccount);
                if (checkedAndUpdated) {
                    return true;
                }
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error("Could not pay interest charges", e);
        }
        return false;
    }

    private boolean checkAndUpdateInterestCharges(int userId, double amount, Connection connection, UserAccount userAccount, CreditAccount creditAccount) {
        try {
            if (creditAccount.getInterestCharges() < amount && creditAccountService.updateInterestCharges(0, userId)) {
                double returnAmount = amount - creditAccount.getInterestCharges();
                userAccountService.updateBalanceById(userAccount.getBalance() + returnAmount, userId);
                connection.commit();
                return true;
            }

            if (creditAccount.getInterestCharges() >= amount && creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() - amount, userId)) {
                userAccountService.updateBalanceById(userAccount.getBalance(), userId);
                connection.commit();
                return true;
            }
        } catch (Exception e) {
            log.error("Could not pay interest charges", e);
        }
        return false;
    }
}
