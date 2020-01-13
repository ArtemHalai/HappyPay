package facade;

import factories.JDBCConnectionFactory;
import model.CreditAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CreditAccountService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditAccountFacadeTest {

    private static final int USER_ID = 1;
    private static final double ARREARS = 100;
    private static final double RATE = 13.1;
    private static final double INTEREST_CHARGES = 1380.1;
    private static final int DAYS = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
    private static final double AMOUNT =  RATE / 100 / DAYS * 1 * ARREARS;

    @Mock
    private CreditAccountService creditAccountService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private CreditAccount creditAccount;

    @Mock
    private Connection connection;

    @InjectMocks
    private CreditAccountFacade creditAccountFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void getCreditAccount_ReturnsCreditAccount_WhenCreditAccountExistsForGivenUserId() {
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);

        CreditAccount actualCreditAccount = creditAccountFacade.getCreditAccount(USER_ID);

        assertEquals(creditAccount, actualCreditAccount);
    }

    @Test
    public void getAll_ReturnsListWithCreditAccounts_WhenThereIsAny() {
        List<CreditAccount> creditAccounts = new ArrayList<>();
        when(creditAccountService.getAll()).thenReturn(creditAccounts);

        List<CreditAccount> actualCreditAccounts = creditAccountFacade.getAll();

        assertEquals(creditAccounts, actualCreditAccounts);
    }

    @Test
    public void checkArrears_ReturnsFalse_WhenCreditAccountDoesNotHaveArrears() {
        double arrears = 0;

        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getArrears()).thenReturn(arrears);

        boolean noArrears = creditAccountFacade.checkArrears(USER_ID);

        assertFalse(noArrears);
    }

    @Test
    public void checkArrears_ReturnsTrue_WhenCreditAccountHasArrears() {
        when(creditAccountService.getById(USER_ID)).thenReturn(creditAccount);
        when(creditAccount.getArrears()).thenReturn(ARREARS);

        boolean noArrears = creditAccountFacade.checkArrears(USER_ID);

        assertTrue(noArrears);
    }

    @Test
    public void updateInterestCharges_ReturnsTrue_WhenInterestChargesWereUpdated() {
        setCreditAccountMockToReturnNecessaryData();
        when(creditAccountService.updateInterestCharges(INTEREST_CHARGES + AMOUNT, USER_ID)).thenReturn(true);

        boolean interestChargesUpdated = creditAccountFacade.updateInterestCharges(creditAccount);

        assertTrue(interestChargesUpdated);
    }

    public void setCreditAccountMockToReturnNecessaryData() {
        when(creditAccount.getRate()).thenReturn(RATE);
        when(creditAccount.getArrears()).thenReturn(ARREARS);
        when(creditAccount.getInterestCharges()).thenReturn(INTEREST_CHARGES);
        when(creditAccount.getUserId()).thenReturn(USER_ID);
    }

    @Test
    public void updateInterestCharges_ReturnsFalse_WhenInterestChargesWereNotUpdated() {
        setCreditAccountMockToReturnNecessaryData();
        when(creditAccountService.updateInterestCharges(INTEREST_CHARGES + AMOUNT, USER_ID)).thenReturn(false);

        boolean interestChargesUpdated = creditAccountFacade.updateInterestCharges(creditAccount);

        assertFalse(interestChargesUpdated);
    }
}