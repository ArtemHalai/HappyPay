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
public class PayInterestChargesFacadeTest {

    private static final int USER_ID = 1;
    private static final double AMOUNT = 100.88;
    private static final double BALANCE = 1111.99;
    private static final double INTEREST_CHARGES = 111.99;

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
    private PayInterestChargesFacade payInterestChargesFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void checkInterestCharges_ReturnsFalse_WhenUserDoesNotHaveInterestCharges() {
        double interestCharges = 0;

        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getInterestCharges()).thenReturn(interestCharges);

        boolean hasInterestCharges = payInterestChargesFacade.checkInterestCharges(USER_ID);

        assertFalse(hasInterestCharges);
    }

    @Test
    public void checkInterestCharges_ReturnsTrue_WhenUserHasInterestCharges() {
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getInterestCharges()).thenReturn(INTEREST_CHARGES);

        boolean hasInterestCharges = payInterestChargesFacade.checkInterestCharges(USER_ID);

        assertTrue(hasInterestCharges);
    }

    @Test
    public void payInterestCharges_ReturnsTrue_WhenInterestChargesLessThanGivenAmountAndPayed() {
        double interestCharges = 11.99;
        double returnAmount = AMOUNT - interestCharges;

        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getInterestCharges()).thenReturn(interestCharges);
        when(creditAccountService.updateInterestCharges(0, USER_ID)).thenReturn(true);
        when(userAccountService.updateBalanceById(BALANCE + returnAmount, USER_ID)).thenReturn(true);

        boolean interestChargesPayed = payInterestChargesFacade.payInterestCharges(USER_ID, AMOUNT);

        assertTrue(interestChargesPayed);
    }

    @Test
    public void payInterestCharges_ReturnsTrue_WhenInterestChargesMoreThanOrEqualToGivenAmountAndPayed() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getInterestCharges()).thenReturn(INTEREST_CHARGES);
        when(creditAccountService.updateInterestCharges(INTEREST_CHARGES - AMOUNT, USER_ID)).thenReturn(true);
        when(userAccountService.updateBalanceById(BALANCE, USER_ID)).thenReturn(true);

        boolean interestChargesPayed = payInterestChargesFacade.payInterestCharges(USER_ID, AMOUNT);

        assertTrue(interestChargesPayed);
    }

    @Test
    public void payInterestCharges_ReturnsFalse_WhenInterestChargesCouldNotBePayed() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.isCredit()).thenReturn(true);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getInterestCharges()).thenReturn(INTEREST_CHARGES);
        when(creditAccountService.updateInterestCharges(INTEREST_CHARGES - AMOUNT, USER_ID)).thenReturn(true);
        when(userAccountService.updateBalanceById(BALANCE, USER_ID)).thenReturn(false);

        boolean interestChargesPayed = payInterestChargesFacade.payInterestCharges(USER_ID, AMOUNT);

        assertFalse(interestChargesPayed);
    }
}