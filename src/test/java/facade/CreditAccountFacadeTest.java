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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditAccountFacadeTest {

    @Mock
    private CreditAccountService creditAccountService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private UserAccount userAccount;

    @Mock
    private CreditAccount creditAccount;

    @Mock
    private Connection connection;

    @InjectMocks
    private CreditAccountFacade creditAccountFacade;

    private static final int USER_ID = 1;
    private static final double ARREARS = 100;
    private static final double RATE = 13.1;
    private static final double INTEREST_CHARGES = 1380.1;

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
        int days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
        double amount = RATE / 100 / days * 1 * ARREARS;

        setCreditAccountMockToReturnNecessaryData();
        when(creditAccountService.updateInterestCharges(INTEREST_CHARGES + amount, USER_ID)).thenReturn(true);

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
        int days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
        double amount = RATE / 100 / days * 1 * ARREARS;

        setCreditAccountMockToReturnNecessaryData();
        when(creditAccountService.updateInterestCharges(INTEREST_CHARGES + amount, USER_ID)).thenReturn(false);

        boolean interestChargesUpdated = creditAccountFacade.updateInterestCharges(creditAccount);

        assertFalse(interestChargesUpdated);
    }

    @Test
    public void getUserAccount_ReturnsUserAccount_WhenUserExistsForGivenId() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);

        UserAccount actualUserAccount = creditAccountFacade.getUserAccount(USER_ID);

        assertEquals(userAccount, actualUserAccount);
    }
}