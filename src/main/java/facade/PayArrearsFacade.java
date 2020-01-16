package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.CreditAccount;
import model.UserAccount;
import service.CreditAccountService;
import service.UserAccountService;
import util.TransactionManager;
import util.UserAccountGetter;
import util.UserAccountValidity;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class PayArrearsFacade {

    private static final String ERROR = "Could not pay arrears for user with id: %d";
    private UserAccountService userAccountService;
    private CreditAccountService creditAccountService;
    private JDBCConnectionFactory connectionFactory;

    public PayArrearsFacade() {
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
            userAccountService.setDefaultUserAccountDAO(connection);
            creditAccountService.setDefaultCreditAccountDAO(connection);
            UserAccount userAccount = userAccountService.getById(userId);

            if (UserAccountValidity.checkUserIdIsValid(userAccount) && userAccount.isCredit() && userAccount.getBalance() >= amount) {
                userAccount.setBalance(userAccount.getBalance() - amount);

                CreditAccount creditAccount = creditAccountService.getById(userId);

                boolean checkedAndUpdated = checkAndUpdateArrears(userId, amount, connection, userAccount, creditAccount);
                if (checkedAndUpdated) {
                    connection.commit();
                    return true;
                }
                connection.rollback();
            } else {
                return false;
            }
        } catch (SQLException e) {
            log.error(String.format(ERROR, userId), e);
        }
        return false;
    }

    private boolean checkAndUpdateArrears(int userId, double amount, Connection connection, UserAccount userAccount, CreditAccount creditAccount) {
        try {
            if (creditAccount.getArrears() < amount) {
                boolean updatedArrears = creditAccountService.updateArrears(0, userId);
                double returnAmount = amount - creditAccount.getArrears();
                boolean updatedBalance = userAccountService.updateBalanceById(userAccount.getBalance() + returnAmount, userId);
                if (updatedBalance && updatedArrears) {
                    connection.commit();
                    return true;
                }
            }

            if (creditAccount.getArrears() >= amount) {
                boolean updatedArrears = creditAccountService.updateArrears(creditAccount.getArrears() - amount, userId);
                boolean updateBalanceById = userAccountService.updateBalanceById(userAccount.getBalance(), userId);
                if (updateBalanceById && updatedArrears) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(String.format(ERROR, userId), e);
        }
        return false;
    }

    public boolean checkArrears(int userId) {
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setDefaultCreditAccountDAO(connection);
            if (creditAccountService.getById(userId).getArrears() <= 0) {
                return false;
            }
        } catch (SQLException e) {
            log.error(String.format("Could not check arrears for user with id: %d", userId), e);
        }
        return true;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
