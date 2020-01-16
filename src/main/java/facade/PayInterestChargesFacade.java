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
public class PayInterestChargesFacade {

    private UserAccountService userAccountService;
    private CreditAccountService creditAccountService;
    private JDBCConnectionFactory connectionFactory;

    public PayInterestChargesFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public boolean checkInterestCharges(int userId) {
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setDefaultCreditAccountDAO(connection);
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
            userAccountService.setDefaultUserAccountDAO(connection);
            creditAccountService.setDefaultCreditAccountDAO(connection);
            UserAccount userAccount = userAccountService.getById(userId);

            if (UserAccountValidity.checkUserIdIsValid(userAccount) && userAccount.isCredit() && userAccount.getBalance() >= amount) {
                userAccount.setBalance(userAccount.getBalance() - amount);

                CreditAccount creditAccount = creditAccountService.getById(userId);

                boolean checkedAndUpdated = checkAndUpdateInterestCharges(userId, amount, connection, userAccount, creditAccount);
                if (checkedAndUpdated) {
                    return true;
                }
                connection.rollback();
            } else {
                return false;
            }
        } catch (SQLException e) {
            log.error("Could not pay interest charges", e);
        }
        return false;
    }

    private boolean checkAndUpdateInterestCharges(int userId, double amount, Connection connection, UserAccount userAccount, CreditAccount creditAccount) {
        try {
            if (creditAccount.getInterestCharges() < amount) {
                boolean updatedInterestCharges = creditAccountService.updateInterestCharges(0, userId);
                double returnAmount = amount - creditAccount.getInterestCharges();
                boolean updatedBalance = userAccountService.updateBalanceById(userAccount.getBalance() + returnAmount, userId);
                if (updatedBalance && updatedInterestCharges) {
                    connection.commit();
                    return true;
                }
            }

            if (creditAccount.getInterestCharges() >= amount) {
                boolean updatedInterestCharges = creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() - amount, userId);
                boolean updatedBalance = userAccountService.updateBalanceById(userAccount.getBalance(), userId);
                if (updatedBalance && updatedInterestCharges) {
                    connection.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Could not pay interest charges", e);
        }
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
