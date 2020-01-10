package facade;

import factories.DaoFactory;
import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.CreditAccount;
import model.UserAccount;
import model.calculation.CreditCalculator;
import service.CreditAccountService;
import service.UserAccountService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static enums.DAOEnum.CREDIT_ACCOUNT_JDBC;
import static enums.DAOEnum.USER_ACCOUNT_JDBC;

@Log4j
public class CreditAccountFacade {

    private CreditAccountService creditAccountService;
    private UserAccountService userAccountService;
    private DaoFactory factory;
    private JDBCConnectionFactory connectionFactory;

    public CreditAccountFacade() {
        factory = DaoFactory.getInstance();
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setCreditAccountService(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public CreditAccount getCreditAccount(int userId) {
        CreditAccount creditAccount = new CreditAccount();
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
            creditAccount = creditAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get credit account", e);
        }
        return creditAccount;
    }

    public List<CreditAccount> getAll() {
        List<CreditAccount> list = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
            list = creditAccountService.getAll();
        } catch (SQLException e) {
            log.error("Could not get all credit accounts", e);
        }
        return list;
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

    public boolean updateInterestCharges(CreditAccount creditAccount) {
        boolean updated = false;
        try (Connection connection = connectionFactory.getConnection()) {
            creditAccountService.setCreditAccountDAO(factory.getCreditAccountDAO(connection, CREDIT_ACCOUNT_JDBC));
            CreditCalculator creditCalculator = new CreditCalculator(creditAccount);
            double amount = creditCalculator.calculate();
            updated = creditAccountService.updateInterestCharges(creditAccount.getInterestCharges() + amount, creditAccount.getUserId());
        } catch (SQLException e) {
            log.error("Could not update interest charges", e);
        }
        return updated;
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
