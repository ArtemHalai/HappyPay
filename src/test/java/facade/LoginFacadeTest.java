package facade;

import factories.JDBCConnectionFactory;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.UserService;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginFacadeTest {

    private static final String USERNAME = "username";
    private static final String PASS = "12345678";

    @Mock
    private UserService userService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private User user;

    @Mock
    private Connection connection;

    @InjectMocks
    private LoginFacade loginFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void getUserByUsernameAndPassword_ReturnsUser_WhenUserExistsInDatabase() {
        when(userService.getUserByUsernameAndPassword(USERNAME, PASS)).thenReturn(user);

        User userByUsernameAndPassword = loginFacade.getUserByUsernameAndPassword(USERNAME, PASS);

        assertEquals(user, userByUsernameAndPassword);
    }

    @Test
    public void getUserByUsernameAndPassword_ReturnsNull_WhenUserIsNotExistingInDatabase() {
        when(userService.getUserByUsernameAndPassword(USERNAME, PASS)).thenReturn(null);

        User userByUsernameAndPassword = loginFacade.getUserByUsernameAndPassword(USERNAME, PASS);

        assertNull(userByUsernameAndPassword);
    }
}