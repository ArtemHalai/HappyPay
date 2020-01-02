package command;

import enums.Mappings;
import facade.LoginFacade;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.*;
import static enums.Mappings.HOME;
import static enums.Mappings.LOGIN_VIEW;
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

    @Test
    public void execute() {
        final String USER = "USERNAME";
        final String PASS = "11111111";
        user.setUsername(USER);
        user.setPassword(PASS);
        user.setUserId(1);
        user.setRole(CLIENT.getRoleId());

        when(request.getSession()).thenReturn(session);
        when(request.getParameter(USERNAME.getName())).thenReturn(USER);
        when(request.getParameter(PASSWORD.getName())).thenReturn(PASS);

        when(facade.getUserByUsernameAndPassword(any(User.class))).thenReturn(user);

        Mappings execute = command.execute(request, response);

        verify(facade).getUserByUsernameAndPassword(any(User.class));
        verify(session).setAttribute(ROLE.getName(), CLIENT.getRoleId());
        verify(session).setAttribute(USER_ID.getName(), user.getUserId());

        assertEquals(HOME, execute);
        assertEquals(user, facade.getUserByUsernameAndPassword(user));

        when(request.getParameter(USERNAME.getName())).thenReturn(null);
        when(request.getParameter(PASSWORD.getName())).thenReturn(null);

        Mappings execute1 = command.execute(request, response);

        assertEquals(LOGIN_VIEW, execute1);
    }
}