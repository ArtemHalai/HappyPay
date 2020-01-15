package facade;

import factories.JDBCConnectionFactory;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CreditApprovementService;
import service.UserAccountService;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditRequestFacadeTest {

    private static final int USER_ID = 1;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private CreditApprovementService creditApprovementService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private CreditRequest creditRequest;

    @Mock
    private UserAccount userAccount;

    @Mock
    private CreditApprovementOperation creditApprovementOperation;

    @Mock
    private Connection connection;

    @InjectMocks
    private CreditRequestFacade creditRequestFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(creditRequest.getUserId()).thenReturn(USER_ID);
        when(creditApprovementService.getById(USER_ID)).thenReturn(creditApprovementOperation);
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);
    }

    @Test
    public void createCreditRequest_ReturnsTrue_WhenCreditRequestWasCreated() {
        int expectedUserId = -1;

        when(creditApprovementOperation.getUserId()).thenReturn(expectedUserId);
        when(creditApprovementService.createCreditRequest(creditRequest)).thenReturn(true);

        boolean requestCreated = creditRequestFacade.createCreditRequest(creditRequest);

        assertTrue(requestCreated);
    }

    @Test
    public void createCreditRequest_ReturnsFalse_WhenCreditRequestWasNotCreated() {
        when(creditApprovementOperation.getUserId()).thenReturn(USER_ID);

        boolean requestCreated = creditRequestFacade.createCreditRequest(creditRequest);

        assertFalse(requestCreated);
    }

    @Test
    public void checkCredit_ReturnsFalse_WhenUserAlreadyHasCreditAccount() {
        when(userAccount.isCredit()).thenReturn(true);

        boolean hasCreditAccount = creditRequestFacade.checkCredit(USER_ID);

        assertFalse(hasCreditAccount);
    }

    @Test
    public void checkCredit_ReturnsTrue_WhenUserDoesNotHaveCreditAccount() {
        when(userAccount.isCredit()).thenReturn(false);

        boolean hasCreditAccount = creditRequestFacade.checkCredit(USER_ID);

        assertTrue(hasCreditAccount);
    }
}