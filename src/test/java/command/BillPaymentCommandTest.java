package command;

import enums.Mappings;
import facade.BillPaymentFacade;
import model.BillPaymentOperation;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;

import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.CLIENT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BillPaymentCommandTest {

    @Mock
    private BillPaymentFacade facade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Spy
    private UserAccount userAccount;

    @InjectMocks
    private BillPaymentCommand command;

    private static final int USER_TEST_ID = 1;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER_ID.getName())).thenReturn(USER_TEST_ID);
        when(session.getAttribute(ROLE.getName())).thenReturn(CLIENT.getRoleId());
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
        when(facade.getUserAccount(USER_TEST_ID)).thenReturn(userAccount);

        Mappings actualMapping = command.execute(request, response);
        assertEquals(CLIENT_ACCOUNTS, actualMapping);
    }

    @Test
    public void execute_ReturnsBillPaymentMapping_WhenAmountParameterInRequestEqualsNull() {
        setValidityToUserAccount();

        Mappings actualMapping = command.execute(request, response);
        assertEquals(BILL_PAYMENT, actualMapping);
    }

    private void setValidityToUserAccount() {
        long addedTimeToPassDateValidity = 100;
        userAccount.setValidity(new Date(System.currentTimeMillis() + addedTimeToPassDateValidity));
        when(facade.getUserAccount(USER_TEST_ID)).thenReturn(userAccount);
    }

    @Test
    public void execute_ReturnsErrorMapping_WhenUserWithSuchUsernameIsNotExisting() {
        setValidityToUserAccount();

        String negativeBillNumber = "-12345678";
        String amount = "0";

        when(request.getParameter(AMOUNT.getName())).thenReturn(amount);
        when(request.getParameter(BILL_NUMBER.getName())).thenReturn(negativeBillNumber);
        Mappings actualMapping = command.execute(request, response);

        assertEquals(ERROR, actualMapping);
    }

    @Test
    public void execute_ReturnsSuccessfulMapping_WhenBillPayedSuccessfully() {
        setValidityToUserAccount();

        when(facade.payBill(any(BillPaymentOperation.class))).thenReturn(true);
        when(request.getParameter(AMOUNT.getName())).thenReturn("10");
        when(request.getParameter(BILL_NUMBER.getName())).thenReturn("12345678");
        Mappings actualMapping = command.execute(request, response);

        assertEquals(SUCCESSFUL, actualMapping);
    }

    @Test
    public void execute_ReturnsErrorMapping_WhenBillPayedUnsuccessfully() {
        setValidityToUserAccount();

        String billNumber = "12345678";
        String amount = "10";

        when(facade.payBill(any(BillPaymentOperation.class))).thenReturn(false);
        when(request.getParameter(AMOUNT.getName())).thenReturn(amount);
        when(request.getParameter(BILL_NUMBER.getName())).thenReturn(billNumber);
        Mappings actualMapping = command.execute(request, response);

        assertEquals(ERROR, actualMapping);
    }
}