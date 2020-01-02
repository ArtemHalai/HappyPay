package service;

import dao.intefaces.DepositAccountDAO;
import model.DepositAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DepositAccountServiceTest {

    @Mock
    private DepositAccountDAO dao;

    @InjectMocks
    private DepositAccountService service;

    @Test
    public void add() {
        DepositAccount depositAccount = new DepositAccount();
        when(dao.add(depositAccount)).thenReturn(true);
        boolean add = service.add(depositAccount);
        verify(dao).add(depositAccount);
        assertTrue(add);
    }

    @Test
    public void updateBalanceById() {
        when(dao.updateBalanceById(1.11, 1)).thenReturn(true);
        boolean b = service.updateBalanceById(1.11, 1);
        verify(dao).updateBalanceById(1.11, 1);
        assertTrue(b);
    }

    @Test
    public void getById() {
        DepositAccount depositAccount = new DepositAccount();
        when(dao.getById(1)).thenReturn(depositAccount);
        DepositAccount byId = service.getById(1);
        verify(dao).getById(1);
        assertEquals(depositAccount, byId);
    }

    @Test
    public void deleteDepositAccount() {
        when(dao.deleteDepositAccount(9)).thenReturn(true);
        boolean b = service.deleteDepositAccount(9);
        verify(dao).deleteDepositAccount(9);
        assertTrue(b);
    }

    @Test
    public void getAll() {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setBalance(121.1);
        DepositAccount depositAccount1 = new DepositAccount();
        DepositAccount depositAccount2 = new DepositAccount();
        DepositAccount depositAccount3 = new DepositAccount();
        List<DepositAccount> list = new ArrayList<>();
        list.add(depositAccount);
        list.add(depositAccount1);
        list.add(depositAccount2);
        list.add(depositAccount3);
        when(dao.getAll()).thenReturn(list);
        List<DepositAccount> all = service.getAll();
        verify(dao).getAll();
        assertEquals(121.1, all.get(0).getBalance(), 0);
    }
}