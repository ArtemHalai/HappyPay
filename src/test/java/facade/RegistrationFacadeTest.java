package facade;

import argument_matchers.UserAccountArgumentMatcher;
import argument_matchers.UserArgumentMatcher;
import factories.JDBCConnectionFactory;
import model.ClientDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.ClientDetailsService;
import service.UserAccountService;
import service.UserService;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationFacadeTest {

    private static final String USERNAME = "USERNAME";
    private static final String PASS = "11111111";

    @Mock
    private ClientDetailsService clientDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private ClientDetails clientDetails;

    @Mock
    private Connection connection;

    @InjectMocks
    private RegistrationFacade registrationFacade;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(clientDetails.getUsername()).thenReturn(USERNAME);
        when(clientDetails.getPassword()).thenReturn(PASS);
        when(userService.isUserExist(USERNAME)).thenReturn(false);
    }

    @Test
    public void addClientDetails_ReturnsNegativeUserId_WhenUserAlreadyExists() {
        int expectedUserId = -1;

        when(userService.isUserExist(USERNAME)).thenReturn(true);

        int userId = registrationFacade.addClientDetails(clientDetails);

        assertEquals(expectedUserId, userId);
    }

    @Test
    public void addClientDetails_ReturnsGeneratedUserId_WhenUserAndClientDetailsAddedSuccessfully() {
        int expectedUserId = 1;

        when(userService.addUser(argThat(new UserArgumentMatcher()))).thenReturn(expectedUserId);
        when(clientDetailsService.add(clientDetails)).thenReturn(true);
        when(userAccountService.add(argThat(new UserAccountArgumentMatcher()))).thenReturn(true);

        int userId = registrationFacade.addClientDetails(clientDetails);

        assertEquals(expectedUserId, userId);
    }

    @Test
    public void addClientDetails_ReturnsNegativeUserId_WhenUserAndClientDetailsAddedUnsuccessfully() {
        int expectedUserId = -1;

        when(userService.addUser(argThat(new UserArgumentMatcher()))).thenReturn(expectedUserId);
        when(clientDetailsService.add(clientDetails)).thenReturn(false);

        int userId = registrationFacade.addClientDetails(clientDetails);

        assertEquals(expectedUserId, userId);
    }
}