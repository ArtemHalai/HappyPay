package service;

import dao.intefaces.UserDAO;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static enums.Role.ADMIN;
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

    private final String USERNAME = "USERNAME";

    @Test
    public void getUserByUsernameAndPassword() {
        user.setUsername(USERNAME);
        when(dao.isUserExist(USERNAME)).thenReturn(user);
        when(user.getRole()).thenReturn(ADMIN.getRoleId());
        when(dao.getUserByUsernameAndPassword(user)).thenReturn(user);
        User userByUsernameAndPassword = service.getUserByUsernameAndPassword(user);
        verify(dao).getUserByUsernameAndPassword(user);
        verify(dao).isUserExist(USERNAME);
        assertEquals(user, userByUsernameAndPassword);
    }

    @Test
    public void isUserExist() {
        when(dao.isUserExist(USERNAME)).thenReturn(user);
        boolean userExist = service.isUserExist(USERNAME);
        verify(dao).isUserExist(USERNAME);
        assertTrue(userExist);
    }

    @Test
    public void addUser() {
        when(dao.add(user)).thenReturn(1);
        int i = service.addUser(user);
        verify(dao).add(user);
        assertEquals(1, i);
    }
}