package facade;

import factories.JDBCConnectionFactory;
import model.CreditAccount;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CreditAccountService;
import service.UserAccountService;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PayArrearsFacadeTest {

    private static final int USER_ID = 1;
    private static final double AMOUNT = 100.88;
    private static final double BALANCE = 1111.99;
    private static final double ARREARS = 111.99;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private CreditAccountService creditAccountService;

    @Mock
    private UserAccount userAccount;

    @Mock
    private CreditAccount creditAccount;

    @Mock
    private Connection connection;

    @InjectMocks
    private PayArrearsFacade payArrearsFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void payArrears_ReturnsTrue_WhenArrearsLessThanGivenAmountAndPayed() {
        double arrears = 11.99;
        double returnAmount = AMOUNT - arrears;

        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getArrears()).thenReturn(arrears);
        when(creditAccountService.updateArrears(0, USER_ID)).thenReturn(true);
        when(userAccountService.updateBalanceById(BALANCE + returnAmount, USER_ID)).thenReturn(true);

        boolean arrearsPayed = payArrearsFacade.payArrears(USER_ID, AMOUNT);

        assertTrue(arrearsPayed);
    }

    @Test
    public void payArrears_ReturnsTrue_WhenArrearsMoreThanOrEqualToGivenAmountAndPayed() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getArrears()).thenReturn(ARREARS);
        when(creditAccountService.updateArrears(ARREARS - AMOUNT, USER_ID)).thenReturn(true);
        when(userAccountService.updateBalanceById(BALANCE, USER_ID)).thenReturn(true);

        boolean arrearsPayed = payArrearsFacade.payArrears(USER_ID, AMOUNT);

        assertTrue(arrearsPayed);
    }

    @Test
    public void payArrears_ReturnsFalse_WhenArrearsCouldNotBePayed() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getArrears()).thenReturn(ARREARS);
        when(creditAccountService.updateArrears(ARREARS - AMOUNT, USER_ID)).thenReturn(true);
        when(userAccountService.updateBalanceById(BALANCE, USER_ID)).thenReturn(false);

        boolean arrearsPayed = payArrearsFacade.payArrears(USER_ID, AMOUNT);

        assertFalse(arrearsPayed);
    }

    @Test
    public void checkArrears_ReturnsFalse_WhenUserDoesNotHaveArrears() {
        double arrears = 0;
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getArrears()).thenReturn(arrears);

        boolean hasArrears = payArrearsFacade.checkArrears(USER_ID);

        assertFalse(hasArrears);
    }

    @Test
    public void checkArrears_ReturnsTrue_WhenUserHasArrears() {
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getArrears()).thenReturn(ARREARS);

        boolean hasArrears = payArrearsFacade.checkArrears(USER_ID);

        assertTrue(hasArrears);
    }
}