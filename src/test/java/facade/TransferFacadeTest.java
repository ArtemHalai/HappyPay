package facade;

import factories.JDBCConnectionFactory;
import model.TransferOperation;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.TransferService;
import service.UserAccountService;

import java.sql.Connection;
import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferFacadeTest {

    @Mock
    private TransferService transferService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private UserAccount userAccount;

    @Mock
    private UserAccount recipientUserAccount;

    @Mock
    private TransferOperation transferOperation;

    @Mock
    private Connection connection;

    @InjectMocks
    private TransferFacade transferFacade;

    private static final int USER_ID = 1;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void transfer_ReturnsTrue_WhenTransferWasSuccessful() {
        long recipientAccountNumber = 12345678;
        double amount = 100.99;
        double balance = 1001.99;
        Date date = new Date(System.currentTimeMillis() + 100);

        when(transferOperation.getRecipientAccountNumber()).thenReturn(recipientAccountNumber);
        when(transferOperation.getUserId()).thenReturn(USER_ID);
        when(transferOperation.getAmount()).thenReturn(amount);
        when(userAccountService.getByAccountNumber(recipientAccountNumber)).thenReturn(recipientUserAccount);
        when(userAccountService.payById(USER_ID, amount)).thenReturn(userAccount);
        when(recipientUserAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.getValidity()).thenReturn(date);
        when(userAccount.getBalance()).thenReturn(balance);
        when(recipientUserAccount.getBalance()).thenReturn(balance);
        when(userAccountService.updateBalanceById(balance, USER_ID)).thenReturn(true);
        when(userAccountService.updateByAccount(balance + amount, recipientAccountNumber)).thenReturn(true);
        when(transferService.add(transferOperation)).thenReturn(true);

        boolean successfulTransfer = transferFacade.transfer(transferOperation);

        assertTrue(successfulTransfer);
    }

    @Test
    public void transfer_ReturnsFalse_WhenTransferWasNotSuccessful() {
        long recipientAccountNumber = 12345678;
        double amount = 100.99;
        double balance = 1001.99;
        Date date = new Date(System.currentTimeMillis() + 100);

        when(transferOperation.getRecipientAccountNumber()).thenReturn(recipientAccountNumber);
        when(transferOperation.getUserId()).thenReturn(USER_ID);
        when(transferOperation.getAmount()).thenReturn(amount);
        when(userAccountService.getByAccountNumber(recipientAccountNumber)).thenReturn(recipientUserAccount);
        when(userAccountService.payById(USER_ID, amount)).thenReturn(userAccount);
        when(recipientUserAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.getValidity()).thenReturn(date);
        when(userAccount.getBalance()).thenReturn(balance);
        when(recipientUserAccount.getBalance()).thenReturn(balance);
        when(userAccountService.updateBalanceById(balance, USER_ID)).thenReturn(true);
        when(userAccountService.updateByAccount(balance + amount, recipientAccountNumber)).thenReturn(true);
        when(transferService.add(transferOperation)).thenReturn(false);

        boolean unsuccessfulTransfer = transferFacade.transfer(transferOperation);

        assertFalse(unsuccessfulTransfer);
    }

    @Test
    public void getUserAccount_ReturnsUserAccount_WhenUserExistsForGivenId() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);

        UserAccount actualUserAccount = transferFacade.getUserAccount(USER_ID);

        assertEquals(userAccount, actualUserAccount);
    }
}