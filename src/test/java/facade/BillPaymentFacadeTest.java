package facade;

import factories.JDBCConnectionFactory;
import model.BillPaymentOperation;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import service.BillPaymentService;
import service.UserAccountService;
import util.UserAccountGetter;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserAccountGetter.class)
public class BillPaymentFacadeTest {

    private static final int USER_ID = 1;
    private static final double AMOUNT = 100.99;
    private static final double USER_BALANCE = 1001.99;
    private static final LocalDate DATE = LocalDate.now().plusDays(4);

    @Mock
    private BillPaymentService billPaymentService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private BillPaymentOperation billPaymentOperation;

    @Mock
    private UserAccount userAccount;

    @Mock
    private Connection connection;

    @InjectMocks
    private BillPaymentFacade billPaymentFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(billPaymentOperation.getUserId()).thenReturn(USER_ID);
        when(billPaymentOperation.getAmount()).thenReturn(AMOUNT);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.getValidity()).thenReturn(DATE);
        when(userAccount.getBalance()).thenReturn(USER_BALANCE);
        when(userAccountService.updateBalanceById(USER_BALANCE, USER_ID)).thenReturn(true);
    }

    @Test
    public void payBill_ReturnsTrue_WhenBillWasPayed() {
        when(billPaymentService.add(billPaymentOperation)).thenReturn(true);

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        assertTrue(billPayed);
    }

    @Test
    public void payBill_ReturnsFalse_WhenBillWasNotPayed() {
        LocalDate invalidDate = LocalDate.now().minusDays(1);

        when(userAccount.getValidity()).thenReturn(invalidDate);

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        assertFalse(billPayed);
    }

    @Test
    public void payBill_ThrowsException_WhenAddBillPaymentOperation() throws SQLException {
        when(billPaymentService.add(billPaymentOperation)).thenThrow(new RuntimeException());

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        verify(connection).rollback();
        assertFalse(billPayed);
    }

    @Test
    public void getUserAccount_ReturnsUserAccount_WhenAccountExistsForGivenUserId() {
        mockStatic(UserAccountGetter.class);
        when(UserAccountGetter.getUserAccount(USER_ID)).thenReturn(userAccount);

        UserAccount actualUserAccount = billPaymentFacade.getUserAccount(USER_ID);

        assertEquals(userAccount, actualUserAccount);
    }
}