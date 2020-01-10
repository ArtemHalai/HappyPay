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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BillPaymentFacadeTest {

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

    private static final int USER_ID = 1;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void payBill_ReturnsTrue_WhenBillWasPayed() {
        double amount = 100.99;
        double userBalance = 1000.99;
        Date date = new Date(System.currentTimeMillis() + 100);

        when(billPaymentOperation.getUserId()).thenReturn(USER_ID);
        when(billPaymentOperation.getAmount()).thenReturn(amount);
        when(userAccountService.payById(USER_ID, amount)).thenReturn(userAccount);
        when(userAccount.getValidity()).thenReturn(date);
        when(userAccount.getBalance()).thenReturn(userBalance);
        when(userAccountService.updateBalanceById(userBalance, USER_ID)).thenReturn(true);
        when(billPaymentService.add(billPaymentOperation)).thenReturn(true);

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        assertTrue(billPayed);
    }

    @Test
    public void payBill_ReturnsFalse_WhenBillWasNotPayed() {
        double amount = 100.99;

        when(billPaymentOperation.getUserId()).thenReturn(USER_ID);
        when(billPaymentOperation.getAmount()).thenReturn(amount);
        when(userAccountService.payById(USER_ID, amount)).thenReturn(null);

        boolean billPayed = billPaymentFacade.payBill(billPaymentOperation);

        assertFalse(billPayed);
    }

    @Test
    public void getUserAccount_ReturnsFalse_WhenBillWasNotPayed() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);

        UserAccount actualUserAccount = billPaymentFacade.getUserAccount(USER_ID);

        assertEquals(userAccount, actualUserAccount);
    }
}