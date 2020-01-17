package command;

import argument_matchers.TransferOperationArgumentMatcher;
import enums.Mappings;
import facade.TransferFacade;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.CLIENT;
import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferCommandTest {

    private static final int USER_TEST_ID = 1;
    private static final String TEST_AMOUNT = "1000";
    private static final String TEST_RECIPIENT_ACCOUNT_NUMBER = "12345678";
    private static final LocalDate VALID_DATE = LocalDate.now().plusDays(1);

    @Mock
    private TransferFacade facade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private UserAccount userAccount;

    @InjectMocks
    private TransferCommand command;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER_ID.getName())).thenReturn(USER_TEST_ID);
        when(session.getAttribute(ROLE.getName())).thenReturn(CLIENT.getRoleId());
        when(request.getParameter(AMOUNT.getName())).thenReturn(TEST_AMOUNT);
        when(request.getParameter(ACCOUNT_NUMBER.getName())).thenReturn(TEST_RECIPIENT_ACCOUNT_NUMBER);
        when(facade.getUserAccount(USER_TEST_ID)).thenReturn(userAccount);
        when(userAccount.getValidity()).thenReturn(VALID_DATE);
    }

    @Test
    public void execute_ReturnsLoginViewMapping_WhenUserIsNotLoggedIn() {
        reset(session);
        when(request.getSession()).thenReturn(session);

        Mappings actualMapping = command.execute(request, response);

        assertEquals(LOGIN_VIEW, actualMapping);
    }

    @Test
    public void execute_ReturnsClientAccountsMapping_WhenUserHasInvalidTermForAccount() {
        LocalDate notValidDate = LocalDate.now().minusDays(1);

        when(userAccount.getValidity()).thenReturn(notValidDate);

        Mappings actualMapping = command.execute(request, response);

        assertEquals(CLIENT_ACCOUNTS, actualMapping);
    }

    @Test
    public void execute_ReturnsTransferMapping_WhenParametersInRequestAreEmpty() {
        when(request.getParameter(AMOUNT.getName())).thenReturn(null);

        Mappings actualMapping = command.execute(request, response);

        assertEquals(TRANSFER, actualMapping);
    }

    @Test
    public void execute_ReturnsSuccessfulMapping_WhenRefillOperationWasSuccessful() {
        when(facade.transfer(argThat(new TransferOperationArgumentMatcher()))).thenReturn(true);

        Mappings actualMapping = command.execute(request, response);

        assertEquals(SUCCESSFUL, actualMapping);
    }

    @Test
    public void execute_ReturnsErrorMapping_WhenAccountDoesNotExist() {
        when(facade.transfer(argThat(new TransferOperationArgumentMatcher()))).thenReturn(false);

        Mappings actualMapping = command.execute(request, response);

        assertEquals(ERROR, actualMapping);
    }
}