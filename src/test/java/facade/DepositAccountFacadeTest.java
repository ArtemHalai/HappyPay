package facade;

import argument_matchers.DepositAccountArgumentMatcher;
import argument_matchers.RefillOperationArgumentMatcher;
import factories.JDBCConnectionFactory;
import model.DepositAccount;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.DepositAccountService;
import service.RefillService;
import service.UserAccountService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static enums.DepositEnum.YEAR;
import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepositAccountFacadeTest {

    private static final int USER_ID = 1;
    private static final double BALANCE = 1000;
    private static final long ACCOUNT_NUMBER = 12345678;
    private static final double RATE = 11.8;
    private static final Date START_DATE = new Date(System.currentTimeMillis() - 10000000);
    private static final Date TERM = new Date(1000000000);
    private static final long DAYS = ChronoUnit.DAYS.between(START_DATE.toLocalDate(), TERM.toLocalDate());
    private static final double AMOUNT = BALANCE * RATE * DAYS / (DAYS * 100) + BALANCE;

    @Mock
    private DepositAccountService depositAccountService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private RefillService refillService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private DepositAccount depositAccount;

    @Mock
    private UserAccount userAccount;

    @Mock
    private Connection connection;

    @InjectMocks
    private DepositAccountFacade depositAccountFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(depositAccount.getBalance()).thenReturn(BALANCE);
        when(depositAccount.getDepositEnum()).thenReturn(YEAR);
        when(depositAccount.getRate()).thenReturn(RATE);
        when(depositAccount.getUserId()).thenReturn(USER_ID);
        when(depositAccount.getStartDate()).thenReturn(START_DATE);
        when(depositAccount.getTerm()).thenReturn(TERM);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(userAccountService.updateBalanceById(BALANCE + AMOUNT, USER_ID)).thenReturn(true);
        when(userAccountService.updateDepositStatusById(USER_ID, false)).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(userAccount.isDeposit()).thenReturn(false);
        when(userAccount.getAccountNumber()).thenReturn(ACCOUNT_NUMBER);
        when(depositAccountService.deleteDepositAccount(USER_ID)).thenReturn(true);
        when(depositAccountService.add(argThat(new DepositAccountArgumentMatcher()))).thenReturn(true);
        when(userAccountService.updateDepositStatusById(USER_ID, true)).thenReturn(true);
        when(userAccountService.updateBalanceById(0, USER_ID)).thenReturn(true);
    }

    @Test
    public void getDepositAccount_ReturnsDepositAccount_WhenDepositAccountExistsForGivenUserId() {
        when(depositAccountService.getById(USER_ID)).thenReturn(depositAccount);

        DepositAccount actualDepositAccount = depositAccountFacade.getDepositAccount(USER_ID);

        assertEquals(depositAccount, actualDepositAccount);
    }

    @Test
    public void transferMoneyToAccountBalance_ReturnsTrue_WhenMoneyTransferredSuccessfully() {
        boolean moneyTransferred = depositAccountFacade.transferMoneyToAccountBalance(depositAccount);

        assertTrue(moneyTransferred);
    }

    @Test
    public void transferMoneyToAccountBalance_ReturnsFalse_WhenMoneyTransferredUnsuccessfully() {
        when(depositAccountService.deleteDepositAccount(USER_ID)).thenReturn(false);

        boolean moneyTransferred = depositAccountFacade.transferMoneyToAccountBalance(depositAccount);

        assertFalse(moneyTransferred);
    }

    @Test
    public void checkDeposit_ReturnsFalse_WhenUserHasDepositAccount() {
        when(userAccount.isDeposit()).thenReturn(true);

        boolean hasDepositAccount = depositAccountFacade.checkDeposit(USER_ID);

        assertFalse(hasDepositAccount);
    }

    @Test
    public void checkDeposit_ReturnsTrue_WhenUserDoesNotHaveDepositAccount() {
        boolean hasDepositAccount = depositAccountFacade.checkDeposit(USER_ID);

        assertTrue(hasDepositAccount);
    }

    @Test
    public void openDeposit_ReturnsTrue_WhenDepositAccountWasCreatedForGivenBalance() {
        when(refillService.add(argThat(new RefillOperationArgumentMatcher()))).thenReturn(true);

        boolean openDeposit = depositAccountFacade.openDeposit(USER_ID, BALANCE);

        assertTrue(openDeposit);
    }

    @Test
    public void openDeposit_ReturnsFalse_WhenDepositAccountWasNotCreatedForGivenBalance() throws SQLException {
        when(refillService.add(argThat(new RefillOperationArgumentMatcher()))).thenReturn(false);

        boolean openDeposit = depositAccountFacade.openDeposit(USER_ID, BALANCE);

        verify(connection).rollback();

        assertFalse(openDeposit);
    }

    @Test
    public void getAll_ReturnsListWithDepositAccounts_WhenThereIsAny() {
        List<DepositAccount> depositAccounts = new ArrayList<>();
        when(depositAccountService.getAll()).thenReturn(depositAccounts);

        List<DepositAccount> actualDepositAccounts = depositAccountFacade.getAll();

        assertEquals(depositAccounts, actualDepositAccounts);
    }
}