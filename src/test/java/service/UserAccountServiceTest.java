package service;

import dao.intefaces.UserAccountDAO;
import model.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    @Mock
    private UserAccountDAO dao;

    @Spy
    UserAccount userAccount;

    @InjectMocks
    private UserAccountService service;

    @Test
    public void add() {
        UserAccount userAccount = new UserAccount();
        when(dao.add(userAccount)).thenReturn(true);
        boolean add = service.add(userAccount);
        verify(dao).add(userAccount);
        assertTrue(add);
    }

    @Test
    public void getById() {
        UserAccount userAccount = new UserAccount();
        when(dao.getById(1)).thenReturn(userAccount);
        UserAccount byId = service.getById(1);
        verify(dao).getById(1);
        assertEquals(userAccount, byId);
    }

    @Test
    public void updateBalanceById() {
        when(dao.updateBalanceById(10.11, 4)).thenReturn(true);
        boolean updateBalanceById = service.updateBalanceById(10.11, 4);
        verify(dao).updateBalanceById(10.11, 4);
        assertTrue(updateBalanceById);
    }

    @Test
    public void updateCreditStatusById() {
        when(dao.updateCreditStatusById(1, false)).thenReturn(true);
        boolean updateCreditStatusById = service.updateCreditStatusById(1, false);
        verify(dao).updateCreditStatusById(1, false);
        assertTrue(updateCreditStatusById);
    }

    @Test
    public void updateTerm() {
        userAccount.setValidity(LocalDate.now().minusDays(1));
        when(dao.getById(1)).thenReturn(userAccount);
        when(dao.updateTerm(1)).thenReturn(false);
        boolean updatedTerm = service.updateTerm(1);
        verify(dao).updateTerm(1);
        assertFalse(updatedTerm);
    }

    @Test
    public void getByAccountNumber() {
        UserAccount account = new UserAccount();
        when(dao.getByAccountNumber(11111111)).thenReturn(account);
        UserAccount getByAccountNumber = service.getByAccountNumber(11111111);
        verify(dao).getByAccountNumber(11111111);
        assertEquals(account, getByAccountNumber);
    }

    @Test
    public void updateByAccount() {
        when(dao.updateByAccount(10.1, 11111111)).thenReturn(true);
        boolean updateByAccount = service.updateByAccount(10.1, 11111111);
        verify(dao).updateByAccount(10.1, 11111111);
        assertTrue(updateByAccount);
    }

    @Test
    public void updateDepositStatusById() {
        when(dao.updateDepositStatusById(1, false)).thenReturn(true);
        boolean updateDepositStatusById = service.updateDepositStatusById(1, false);
        verify(dao).updateDepositStatusById(1, false);
        assertTrue(updateDepositStatusById);
    }
}