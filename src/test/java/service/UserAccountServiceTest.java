package service;

import dao.intefaces.UserAccountDAO;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    @Mock
    private UserAccountDAO dao;

    @InjectMocks
    private UserAccountService service;

    @Before
    public void init() {
        service.setUserAccountDAO(dao);
    }

    @Test
    public void add() {
        UserAccount userAccount = new UserAccount();
        when(dao.add(userAccount)).thenReturn(true);
        service.add(userAccount);
        verify(dao, times(1)).add(userAccount);
    }

    @Test
    public void getById() {
        UserAccount userAccount = new UserAccount();
        when(dao.getById(anyInt())).thenReturn(userAccount);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(userAccount, service.getById(1));
    }

    @Test
    public void updateBalanceById() {
        when(dao.updateBalanceById(anyDouble(), anyInt())).thenReturn(true);
        service.updateBalanceById(130.18, 1);
        verify(dao, times(1)).updateBalanceById(anyDouble(), anyInt());
        assertTrue(dao.updateBalanceById(13.31, 29));
    }

    @Test
    public void updateCreditStatusById() {
        when(dao.updateCreditStatusById(anyInt(), anyBoolean())).thenReturn(true);
        service.updateCreditStatusById(4, true);
        verify(dao, times(1)).updateCreditStatusById(anyInt(), anyBoolean());
        assertTrue(dao.updateCreditStatusById(1, false));
    }

    @Test
    public void updateTerm() {
        UserAccount account = new UserAccount();
        UserAccount userAccount = spy(account);
        userAccount.setValidity(new Date(System.currentTimeMillis() - 100));
        when(dao.getById(anyInt())).thenReturn(userAccount);
        when(dao.updateTerm(anyInt())).thenReturn(false);
        service.updateTerm(4);
        verify(dao, times(1)).updateTerm(anyInt());
        assertFalse(dao.updateTerm(1));
    }

    @Test
    public void getByAccountNumber() {
        UserAccount account = new UserAccount();
        when(dao.getByAccountNumber(anyLong())).thenReturn(account);
        service.getByAccountNumber(anyLong());
        verify(dao, times(1)).getByAccountNumber(anyInt());
        assertEquals(account, service.getByAccountNumber(1));
    }

    @Test
    public void payById() {
        UserAccount account = new UserAccount();
        UserAccount userAccount = spy(account);
        userAccount.setBalance(100);
        when(dao.getById(anyInt())).thenReturn(account);
        service.payById(anyInt(), 110);
        verify(dao, times(1)).getById(anyInt());
        assertNull(service.payById(anyInt(), 110));
    }

    @Test
    public void updateByAccount() {
        when(dao.updateByAccount(anyDouble(), anyLong())).thenReturn(true);
        service.updateByAccount(4.8, 124365468546L);
        verify(dao, times(1)).updateByAccount(anyDouble(), anyLong());
        assertTrue(dao.updateByAccount(1, 21356756));
    }

    @Test
    public void updateDepositStatusById() {
        when(dao.updateDepositStatusById(anyInt(), anyBoolean())).thenReturn(true);
        service.updateDepositStatusById(1, false);
        verify(dao, times(1)).updateDepositStatusById(anyInt(), anyBoolean());
        assertTrue(dao.updateDepositStatusById(1, true));
    }
}