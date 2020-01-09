package facade;

import factories.JDBCConnectionFactory;
import model.User;
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

    @Mock
    private UserService userService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private User user;

    @InjectMocks
    private LoginFacade loginFacade;

    @Test
    public void getUserByUsernameAndPassword_ReturnsUser_WhenUserExistsInDatabase() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(userService.getUserByUsernameAndPassword(user)).thenReturn(user);

        User userByUsernameAndPassword = loginFacade.getUserByUsernameAndPassword(user);

        verify(userService).getUserByUsernameAndPassword(user);
        
        assertEquals(user, userByUsernameAndPassword);
    }
}