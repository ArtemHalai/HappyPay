package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.DepositAccount;
import model.RefillOperation;
import model.UserAccount;
import model.calculation.DepositCalculator;
import service.DepositAccountService;
import service.RefillService;
import service.UserAccountService;
import util.DateValidity;
import util.TransactionManager;
import util.UserAccountGetter;
import util.UserAccountValidity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static enums.AccountDetails.RATE;
import static enums.AccountType.MAIN_ACCOUNT;
import static enums.DepositEnum.YEAR;
import static enums.OperationType.REFILL_DEPOSIT;

@Log4j
public class DepositAccountFacade {

    private DepositAccountService depositAccountService;
    private UserAccountService userAccountService;
    private RefillService refillService;
    private JDBCConnectionFactory connectionFactory;

    public DepositAccountFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setDepositAccountService(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    public void setRefillService(RefillService refillService) {
        this.refillService = refillService;
    }

    public DepositAccount getDepositAccount(int userId) {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setUserId(-1);
        try (Connection connection = connectionFactory.getConnection()) {
            depositAccountService.setDefaultDepositAccountDAO(connection);
            depositAccount = depositAccountService.getById(userId);
        } catch (SQLException e) {
            log.error("Could not get deposit account", e);
        }
        return depositAccount;
    }

    public boolean transferMoneyToAccountBalance(DepositAccount depositAccount) {
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            depositAccountService.setDefaultDepositAccountDAO(connection);
            userAccountService.setDefaultUserAccountDAO(connection);
            UserAccount userAccount = userAccountService.getById(depositAccount.getUserId());
            DepositCalculator depositCalculator =
                    new DepositCalculator(depositAccount);
            double amount = depositCalculator.calculate();

            if (UserAccountValidity.userIdIsValid(userAccount)) {
                boolean updatedAccountBalance =
                        userAccountService.updateBalanceById(userAccount.getBalance() + amount, depositAccount.getUserId());
                boolean updatedDepositStatus = userAccountService.updateDepositStatusById(depositAccount.getUserId(), false);
                boolean deleted = depositAccountService.deleteDepositAccount(depositAccount.getUserId());

                if (updatedAccountBalance && updatedDepositStatus && deleted) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error("Could not transfer money to account balance", e);
        }
        return false;
    }

    public boolean checkDeposit(int userId) {
        try (Connection connection = connectionFactory.getConnection()) {
            userAccountService.setDefaultUserAccountDAO(connection);
            if (userAccountService.getById(userId).isDeposit()) {
                return false;
            }
        } catch (SQLException e) {
            log.error("Could not check deposit", e);
        }
        return true;
    }

    public boolean openDeposit(int userId, double balance) {
        try (Connection connection = connectionFactory.getConnection()) {
            TransactionManager.setRepeatableRead(connection);
            userAccountService.setDefaultUserAccountDAO(connection);
            depositAccountService.setDefaultDepositAccountDAO(connection);
            refillService.setDefaultRefillDAO(connection);
            UserAccount userAccount = userAccountService.getById(userId);
            if (UserAccountValidity.userIdIsValid(userAccount) && userAccount.getBalance() >= balance && !userAccount.isDeposit() && depositCreator(userId, balance, userAccount, connection)) {
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error("Could not open deposit", e);
        }
        return false;
    }

    private boolean depositCreator(int userId, double balance, UserAccount userAccount, Connection connection) throws SQLException {
        boolean deleted = depositAccountService.deleteDepositAccount(userId);
        if (deleted) {
            DepositAccount depositAccount = new DepositAccount();
            depositAccount.setUserId(userId);
            depositAccount.setBalance(balance);
            depositAccount.setAccountNumber(userAccount.getAccountNumber());
            depositAccount.setRate(RATE.getDetails());
            depositAccount.setTerm(DateValidity.setDepositTerm());
            depositAccount.setDepositEnum(YEAR);
            boolean added = depositAccountService.add(depositAccount);
            boolean updated = userAccountService.updateDepositStatusById(userId, true);
            boolean updateBalance = userAccountService.updateBalanceById(userAccount.getBalance() - balance, userId);

            RefillOperation refillOperation = new RefillOperation();
            refillOperation.setUserId(userId);
            refillOperation.setAmount(balance);
            refillOperation.setOperationType(REFILL_DEPOSIT.getName());
            refillOperation.setSenderAccountType(MAIN_ACCOUNT.getName());
            boolean refillAdded = refillService.add(refillOperation);
            if (added && updated && refillAdded && updateBalance) {
                connection.commit();
                return true;
            }
        }
        return false;
    }

    public List<DepositAccount> getAll() {
        List<DepositAccount> list = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            depositAccountService.setDefaultDepositAccountDAO(connection);
            list = depositAccountService.getAll();
        } catch (SQLException e) {
            log.error("Could not get all deposit accounts", e);
        }
        return list;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
