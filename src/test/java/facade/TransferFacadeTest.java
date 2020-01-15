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
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferFacadeTest {

    private static final int USER_ID = 1;
    private static final long RECIPIENT_ACCOUNT_NUMBER = 12345678;
    private static final double BALANCE = 1001.99;
    private static final double AMOUNT = 100.99;
    private static final LocalDateTime DATE = LocalDateTime.now().plusHours(4);

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

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(transferOperation.getRecipientAccountNumber()).thenReturn(RECIPIENT_ACCOUNT_NUMBER);
        when(transferOperation.getUserId()).thenReturn(USER_ID);
        when(transferOperation.getAmount()).thenReturn(AMOUNT);
        when(userAccountService.getByAccountNumber(RECIPIENT_ACCOUNT_NUMBER)).thenReturn(recipientUserAccount);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
        when(userAccount.getUserId()).thenReturn(USER_ID);
        when(recipientUserAccount.getUserId()).thenReturn(USER_ID);
        when(userAccount.getValidity()).thenReturn(DATE);
        when(userAccount.getBalance()).thenReturn(BALANCE);
        when(recipientUserAccount.getBalance()).thenReturn(BALANCE);
        when(userAccountService.updateBalanceById(BALANCE, USER_ID)).thenReturn(true);
        when(userAccountService.updateByAccount(BALANCE + AMOUNT, RECIPIENT_ACCOUNT_NUMBER)).thenReturn(true);
    }

    @Test
    public void transfer_ReturnsTrue_WhenTransferWasSuccessful() {
        int userId = 4;

        when(userAccount.getUserId()).thenReturn(userId);
        when(transferService.add(transferOperation)).thenReturn(true);

        boolean successfulTransfer = transferFacade.transfer(transferOperation);

        assertTrue(successfulTransfer);
    }

    @Test
    public void transfer_ReturnsFalse_WhenTransferWasNotSuccessful() {
        when(transferService.add(transferOperation)).thenReturn(false);

        boolean unsuccessfulTransfer = transferFacade.transfer(transferOperation);

        assertFalse(unsuccessfulTransfer);
    }
}