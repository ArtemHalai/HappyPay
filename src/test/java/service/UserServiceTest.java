package service;

import dao.intefaces.UserDAO;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDAO dao;

    @Spy
    User user;

    @InjectMocks
    private UserService service;

    private static final String USERNAME = "USERNAME";
    private static final String PASS = "12345678";

    @Test
    public void getUserByUsernameAndPassword_ReturnsUserObject_WhenUserExists() {
        String hashedPassword = "$2$11$1812gH1234yU4321Ea543uAZ7ro0jUrCz4OupZD/nOKpOMo3CAKAm";

        user.setPassword(hashedPassword);
        when(dao.isUserExist(USERNAME)).thenReturn(user);
        User userByUsernameAndPassword = service.getUserByUsernameAndPassword(USERNAME, PASS);
        assertEquals(user, userByUsernameAndPassword);
    }

    @Test
    public void getUserByUsernameAndPassword_ReturnsNull_WhenUserIsNotExisting() {
        when(dao.isUserExist(USERNAME)).thenReturn(null);
        User userByUsernameAndPassword = service.getUserByUsernameAndPassword(USERNAME, PASS);
        assertNull(userByUsernameAndPassword);
    }

    @Test
    public void getUserByUsernameAndPassword_ReturnsNull_WhenUserEnteredWrongPassword() {
        String hashedPassword = "$2$11$1812gH1234yU4321Ea543uAZ7ro0jUrCz4OupZD/nOKpOMo3CAKAm";
        String enteredWrongPassword = "111111111";

        user.setPassword(hashedPassword);
        when(dao.isUserExist(USERNAME)).thenReturn(user);
        User userByUsernameAndPassword = service.getUserByUsernameAndPassword(USERNAME, enteredWrongPassword);
        assertNull(userByUsernameAndPassword);
    }

    @Test
    public void isUserExist_ReturnsTrue_WhenUserWithCurrentUsernameIsNotEqualNull() {
        when(dao.isUserExist(USERNAME)).thenReturn(user);
        boolean userExist = service.isUserExist(USERNAME);
        assertTrue(userExist);
    }

    @Test
    public void addUser_ReturnsGeneratedUserId_WhenUserAddedSuccessfully() {
        when(dao.add(user)).thenReturn(1);
        int userId = service.addUser(user);
        assertEquals(1, userId);
    }
}