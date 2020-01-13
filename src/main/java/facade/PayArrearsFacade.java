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

import static enums.DAOEnum.*;

@Log4j
public class PayArrearsFacade {

    private UserAccountService userAccountService;
    private CreditAccountService creditAccountService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public PayArrearsFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public boolean payArrears(int userId, double amount) {
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

                boolean checkedAndUpdated = checkAndUpdateArrears(userId, amount, connection, userAccount, creditAccount);
                if (checkedAndUpdated) {
                    return true;
                }
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error("Could not pay arrears", e);
        }
        return false;
    }

    private boolean checkAndUpdateArrears(int userId, double amount, Connection connection, UserAccount userAccount, CreditAccount creditAccount) {
        try {
            if (creditAccount.getArrears() < amount && creditAccountService.updateArrears(0, userId)) {
                double returnAmount = amount - creditAccount.getArrears();
                boolean updatedBalance = userAccountService.updateBalanceById(userAccount.getBalance() + returnAmount, userId);
                if (updatedBalance) {
                    connection.commit();
                    return true;
                }
            }

            if (creditAccount.getArrears() >= amount && creditAccountService.updateArrears(creditAccount.getArrears() - amount, userId)) {
                boolean updateBalanceById = userAccountService.updateBalanceById(userAccount.getBalance(), userId);
                if (updateBalanceById) {
                    connection.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Could not pay arrears", e);
        }
        return false;
    }

    public boolean checkArrears(int userId) {
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
            if (creditAccountService.getById(userId).getArrears() <= 0) {
                return false;
            }
        } catch (SQLException e) {
            log.error("Could not check arrears", e);
        }
        return true;
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
}
