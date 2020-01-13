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
import service.BillPaymentService;
import service.UserAccountService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BillPaymentFacadeTest {

    private static final int USER_ID = 1;
    private static final double AMOUNT = 100.99;
    private static final double USER_BALANCE = 1001.99;
    private static final Date DATE = new Date(System.currentTimeMillis() + 100000);

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
    }

    @Test
    public void payBill_ReturnsTrue_WhenBillWasPayed() {
        when(billPaymentOperation.getUserId()).thenReturn(USER_ID);
        when(billPaymentOperation.getAmount()).thenReturn(AMOUNT);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getValidity()).thenReturn(DATE);
        when(userAccount.getBalance()).thenReturn(USER_BALANCE);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccountService.updateBalanceById(USER_BALANCE, USER_ID)).thenReturn(true);
        when(billPaymentService.add(billPaymentOperation)).thenReturn(true);

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        assertTrue(billPayed);
    }

    @Test
    public void payBill_ReturnsFalse_WhenBillWasNotPayed() {
        Date invalidDate = new Date(System.currentTimeMillis() - 1000);

        when(billPaymentOperation.getUserId()).thenReturn(USER_ID);
        when(billPaymentOperation.getAmount()).thenReturn(AMOUNT);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.getValidity()).thenReturn(invalidDate);

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        assertFalse(billPayed);
    }

    @Test
    public void payBill_ThrowsException_WhenAddBillPaymentOperation() throws SQLException {
        when(billPaymentOperation.getUserId()).thenReturn(USER_ID);
        when(billPaymentOperation.getAmount()).thenReturn(AMOUNT);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getValidity()).thenReturn(DATE);
        when(userAccount.getBalance()).thenReturn(USER_BALANCE);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(userAccountService.updateBalanceById(USER_BALANCE, USER_ID)).thenReturn(true);
        when(billPaymentService.add(billPaymentOperation)).thenThrow(new RuntimeException());

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        verify(connection).rollback();
        assertFalse(billPayed);
    }

    @Test
    public void getUserAccount_ReturnsUserAccount_WhenUserExistsForGivenId() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);

        UserAccount actualUserAccount = billPaymentFacade.getUserAccount(USER_ID);

        assertEquals(userAccount, actualUserAccount);
    }
}