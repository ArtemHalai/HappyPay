package facade;

import factories.JDBCConnectionFactory;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.UserAccountService;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountFacadeTest {

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private JDBCConnectionFactory connectionFactory;

    @Mock
    private UserAccount userAccount;

    @Mock
    private Connection connection;

    @InjectMocks
    private UserAccountFacade userAccountFacade;

    private static final int USER_ID = 1;

    @Before
    public void setUp() {
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    public void getUserAccount_ReturnsUserAccount_WhenUserExistsForGivenId() {
        when(userAccountService.getById(USER_ID)).thenReturn(userAccount);

        UserAccount actualUserAccount = userAccountFacade.getUserAccount(USER_ID);

        assertEquals(userAccount, actualUserAccount);
    }
}