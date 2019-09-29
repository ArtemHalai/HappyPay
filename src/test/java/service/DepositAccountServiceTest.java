package service;

import dao.intefaces.DepositAccountDAO;
import model.DepositAccount;
import org.junit.Before;
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

    @Before
    public void init() {
        service.setDepositAccountDAO(dao);
    }

    @Test
    public void add() {
        DepositAccount depositAccount = new DepositAccount();
        when(dao.add(depositAccount)).thenReturn(true);
        service.add(depositAccount);
        verify(dao, times(1)).add(depositAccount);
    }

    @Test
    public void updateBalanceById() {
        when(dao.updateBalanceById(anyDouble(), anyInt())).thenReturn(true);
        service.updateBalanceById(110.8, 1);
        verify(dao, times(1)).updateBalanceById(anyDouble(), anyInt());
        assertTrue(service.updateBalanceById(120.3, 4));
    }

    @Test
    public void getById() {
        DepositAccount depositAccount = new DepositAccount();
        when(dao.getById(anyInt())).thenReturn(depositAccount);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(depositAccount, service.getById(1));
    }

    @Test
    public void deleteDepositAccount() {
        when(dao.deleteDepositAccount(anyInt())).thenReturn(true);
        service.deleteDepositAccount(1);
        verify(dao, times(1)).deleteDepositAccount(1);
        assertTrue(service.deleteDepositAccount(4));
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
        service.getAll();
        verify(dao, times(1)).getAll();
        assertEquals(121.1, service.getAll().get(0).getBalance(), 0);
    }
}