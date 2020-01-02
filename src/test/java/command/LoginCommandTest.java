package command;

import enums.Mappings;
import facade.LoginFacade;
import model.User;
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

import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.ERRORS;
import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.ADMIN;
import static enums.Role.CLIENT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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

    @Spy
    private User user;

    @InjectMocks
    private LoginCommand command;

    private final String USER = "USERNAME";
    private final String PASS = "11111111";

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void executeLOGGED_IN() {
        when(session.getAttribute(ROLE.getName())).thenReturn(CLIENT.getRoleId());
        Mappings execute = command.execute(request, response);
        assertEquals(LOGGED_IN, execute);
    }

    @Test
    public void executeLOGIN_VIEW() {
        Mappings execute = command.execute(request, response);
        assertEquals(LOGIN_VIEW, execute);
    }

    @Test
    public void executeERROR() {
        String password = "111";

        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(password);

        Mappings execute = command.execute(request, response);

        Map<String, String> errors = new HashMap<>();
        errors.put("password", "Password should contains at least 8 characters.");
        verify(request).setAttribute(ERRORS.getName(), errors);

        assertEquals(ERROR, execute);
    }

    @Test
    public void executeHOME_ADMIN() {
        user.setRole(ADMIN.getRoleId());

        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);
        when(facade.getUserByUsernameAndPassword(any(User.class))).thenReturn(user);

        Mappings execute = command.execute(request, response);
        verify(session).setAttribute(ROLE.getName(), ADMIN.getRoleId());
        assertEquals(HOME_ADMIN, execute);
    }

    @Test
    public void executeHOME() {
        user.setUsername(USER);
        user.setPassword(PASS);
        user.setUserId(1);
        user.setRole(CLIENT.getRoleId());

        when(facade.getUserByUsernameAndPassword(any(User.class))).thenReturn(user);
        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);

        Mappings execute = command.execute(request, response);

        verify(facade).getUserByUsernameAndPassword(any(User.class));
        verify(session).setAttribute(ROLE.getName(), CLIENT.getRoleId());
        verify(session).setAttribute(USER_ID.getName(), user.getUserId());

        assertEquals(HOME, execute);
        assertEquals(user, facade.getUserByUsernameAndPassword(user));
    }

    @Test
    public void executeERROR_USER_DOES_NOT_EXIST() {
        when(facade.getUserByUsernameAndPassword(any(User.class))).thenReturn(null);
        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);

        Mappings execute = command.execute(request, response);

        verify(facade).getUserByUsernameAndPassword(any(User.class));

        Map<String, String> errors = new HashMap<>();
        errors.put("user", "User with such username doesn't exist.");
        verify(request).setAttribute(ERRORS.getName(), errors);

        assertEquals(ERROR, execute);
    }
}