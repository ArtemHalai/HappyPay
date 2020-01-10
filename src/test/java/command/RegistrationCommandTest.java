package command;

import argument_matchers.ClientDetailsArgumentMatcher;
import enums.Mappings;
import facade.RegistrationFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Attributes.*;
import static enums.Mappings.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {

    @Mock
    private RegistrationFacade facade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RegistrationCommand command;

    private static final String INPUT_NAME = "name";
    private static final String INPUT_SURNAME = "surname";
    private static final String INPUT_USERNAME = "username";
    private static final String INPUT_PASSWORD = "12345678";
    private static final String INPUT_PHONE_NUMBER = "+380000000000";
    private static final String INPUT_BIRTHDAY = "999-01-10";

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(NAME.getName())).thenReturn(INPUT_NAME);
        when(request.getParameter(SURNAME.getName())).thenReturn(INPUT_SURNAME);
        when(request.getParameter(USERNAME.getName())).thenReturn(INPUT_USERNAME);
        when(request.getParameter(PASSWORD.getName())).thenReturn(INPUT_PASSWORD);
        when(request.getParameter(PHONE_NUMBER.getName())).thenReturn(INPUT_PHONE_NUMBER);
        when(request.getParameter(BIRTHDAY.getName())).thenReturn(INPUT_BIRTHDAY);
    }

    @Test
    public void execute_ReturnsRegistrationViewMapping_WhenUserInputsAreEmpty() {
        when(request.getParameter(NAME.getName())).thenReturn(null);
        Mappings actualMapping = command.execute(request, response);
        assertEquals(REGISTRATION_VIEW, actualMapping);
    }

    @Test
    public void execute_ReturnsErrorMapping_WhenUserHasErrorsInInputForm() {
        String wrongPassword = "1";

        when(request.getParameter(PASSWORD.getName())).thenReturn(wrongPassword);

        Mappings actualMapping = command.execute(request, response);
        assertEquals(ERROR, actualMapping);
    }

    @Test
    public void execute_ReturnsErrorMapping_WhenUserAlreadyExists() {
        int userId = -1;

        when(facade.addClientDetails(argThat(new ClientDetailsArgumentMatcher()))).thenReturn(userId);

        Mappings actualMapping = command.execute(request, response);
        assertEquals(ERROR, actualMapping);
    }

    @Test
    public void execute_ReturnsHomeMapping_WhenUserRegisteredSuccessfully() {
        int userId = 1;

        when(facade.addClientDetails(argThat(new ClientDetailsArgumentMatcher()))).thenReturn(userId);

        Mappings actualMapping = command.execute(request, response);
        assertEquals(HOME, actualMapping);
    }
}