package command;

import enums.Mappings;
import facade.LoginFacade;
import model.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static enums.Attributes.ERRORS;
import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.ADMIN;
import static enums.Role.CLIENT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {

    @Mock
    private LoginFacade facade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private User user;

    @InjectMocks
    private LoginCommand command;

    private static final String USER = "USERNAME";
    private static final String PASS = "11111111";

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void execute_ReturnsLoggedInMapping_WhenSessionContainsRoleId() {
        when(session.getAttribute(ROLE.getName())).thenReturn(CLIENT.getRoleId());
        Mappings actualMapping = command.execute(request, response);
        assertEquals(LOGGED_IN, actualMapping);
    }

    @Test
    public void execute_ReturnsLoginViewMapping_WhenUsernameOrPasswordParameterInRequestIsEqualNull() {
        Mappings actualMapping = command.execute(request, response);
        assertEquals(LOGIN_VIEW, actualMapping);
    }

    @Test
    public void execute_ReturnsErrorMapping_WhenUsernameOrPasswordIsNotMatchingRegex() {
        String password = "111";

        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(password);

        Mappings actualMapping = command.execute(request, response);

        Map<String, String> errors = new HashMap<>();
        errors.put("password", "Password should contains at least 8 characters.");
        verify(request).setAttribute(ERRORS.getName(), errors);

        assertEquals(ERROR, actualMapping);
    }

    @Test
    public void execute_ReturnsHomeAdminMapping_WhenCurrentUserHasAdminRoleId() {
        User user = spy(new User());
        user.setRole(ADMIN.getRoleId());

        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);
        when(facade.getUserByUsernameAndPassword(USER, PASS)).thenReturn(user);

        Mappings actualMapping = command.execute(request, response);

        verify(session).setAttribute(ROLE.getName(), ADMIN.getRoleId());
        assertEquals(HOME_ADMIN, actualMapping);
    }

    @Test
    public void execute_ReturnsHomeMapping_WhenCurrentUserHasRoleDifferentThanAdminRoleId() {

        when(facade.getUserByUsernameAndPassword(USER, PASS)).thenReturn(user);
        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);

        Mappings actualMapping = command.execute(request, response);

        verify(facade).getUserByUsernameAndPassword(USER, PASS);
        verify(session).setAttribute(ROLE.getName(), CLIENT.getRoleId());
        verify(session).setAttribute(USER_ID.getName(), user.getUserId());

        assertEquals(HOME, actualMapping);
    }

    @Test
    public void execute_ReturnsErrorMapping_WhenUserWithSuchUsernameIsNotExisting() {
        when(facade.getUserByUsernameAndPassword(USER, PASS)).thenReturn(null);
        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);

        Mappings actualMapping = command.execute(request, response);

        verify(facade).getUserByUsernameAndPassword(USER, PASS);

        Map<String, String> errors = new HashMap<>();
        errors.put("user", "User with such username doesn't exist.");
        verify(request).setAttribute(ERRORS.getName(), errors);

        assertEquals(ERROR, actualMapping);
    }

    @Test
    @Ignore
    public void execute_SetsErrorsInMap_WhenSendRequestsInMultithreading() {

        Callable<Boolean> callableWithWrongPassword = () -> {
            String password = "111";

            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getSession()).thenReturn(session);
            when(request.getParameter(USERNAME.getName())).thenReturn(USER);
            when(request.getParameter(PASSWORD.getName())).thenReturn(password);

            Map<String, String> errors = new HashMap<>();
            errors.put("username", "Wrong username.");

            doThrow(new RuntimeException()).when(request).setAttribute(ERRORS.getName(), errors);

            try {
                command.execute(request, response);
            } catch (RuntimeException e) {
                return true;
            }
            return false;
        };

        Callable<Boolean> callableWithWrongUsername = () -> {
            String username = "a";

            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getSession()).thenReturn(session);
            when(request.getParameter(USERNAME.getName())).thenReturn(username);
            when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);

            Map<String, String> errors = new HashMap<>();
            errors.put("password", "Password should contains at least 8 characters.");

            doThrow(new RuntimeException()).when(request).setAttribute(ERRORS.getName(), errors);

            try {
                command.execute(request, response);
            } catch (RuntimeException e) {
                return true;
            }
            return false;
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        boolean isDetected = false;

        while (!isDetected) {
            Future<Boolean> resultWhenWrongPassword = executor.submit(callableWithWrongPassword);
            Future<Boolean> resultWhenWrongUsername = executor.submit(callableWithWrongUsername);

            try {
                isDetected = resultWhenWrongPassword.get() || resultWhenWrongUsername.get();
            } catch (InterruptedException | ExecutionException e) {
                break;
            }
        }
        executor.shutdown();
        assertTrue(isDetected);
    }
}