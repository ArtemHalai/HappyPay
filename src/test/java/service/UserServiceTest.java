package service;

import dao.intefaces.UserDAO;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static enums.Role.ADMIN;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDAO dao;

    @InjectMocks
    private UserService service;

    @Before
    public void init() {
        service.setUserDAO(dao);
    }

    @Test
    public void getUserByUsernameAndPassword() {
        User user = new User();
        User user1 = spy(user);
        when(dao.isUserExist(anyString())).thenReturn(user1);
        when(user1.getRole()).thenReturn(ADMIN.getRoleId());
        when(dao.getUserByUsernameAndPassword(user1)).thenReturn(user1);
        service.getUserByUsernameAndPassword(user1);
        verify(dao, times(1)).getUserByUsernameAndPassword(user1);
        verify(dao, times(1)).isUserExist(anyString());
        assertEquals(user1, dao.getUserByUsernameAndPassword(user1));
    }

    @Test
    public void isUserExist() {
        when(dao.isUserExist(anyString())).thenReturn(new User());
        service.isUserExist(anyString());
        verify(dao, times(1)).isUserExist(anyString());
        assertNotNull(dao.isUserExist(anyString()));
    }

    @Test
    public void addUser() {
        User user = new User();
        when(dao.add(user)).thenReturn(anyInt());
        service.addUser(user);
        verify(dao, times(1)).add(user);
    }
}