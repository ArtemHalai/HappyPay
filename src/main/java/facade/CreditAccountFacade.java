package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.CreditAccount;
import model.UserAccount;
import model.calculation.CreditCalculator;
import service.CreditAccountService;
import util.UserAccountGetter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class CreditAccountFacade {

    private static final String ERROR = "Could not get credit account for user with id: %d";
    private CreditAccountService creditAccountService;
    private JDBCConnectionFactory connectionFactory;

    public CreditAccountFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public CreditAccount getCreditAccount(int userId) {
        CreditAccount creditAccount = new CreditAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setDefaultCreditAccountDAO(connection);
            creditAccount = creditAccountService.getById(userId);
        } catch (SQLException e) {
            log.error(String.format(ERROR, userId), e);
        }
        return creditAccount;
    }

    public List<CreditAccount> getAll() {
        List<CreditAccount> list = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setDefaultCreditAccountDAO(connection);
            list = creditAccountService.getAll();
        } catch (SQLException e) {
            log.error("Could not get all credit accounts", e);
        }
        return list;
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

    public boolean updateInterestCharges(CreditAccount creditAccount) {
        boolean updated = false;
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setDefaultCreditAccountDAO(connection);
            CreditCalculator creditCalculator = new CreditCalculator(creditAccount);
            double amount = creditCalculator.calculate();
            updated = creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() + amount, creditAccount.getUserId());
        } catch (SQLException e) {
            log.error(String.format("Could not update interest charges for user with id: %d", creditAccount.getUserId()), e);
        }
        return updated;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
