package service;

import dao.intefaces.CreditAccountDAO;
import model.CreditAccount;
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
public class CreditAccountServiceTest {

    @Mock
    private CreditAccountDAO dao;

    @InjectMocks
    private CreditAccountService service;

    @Test
    public void add() {
        CreditAccount account = new CreditAccount();
        when(dao.add(account)).thenReturn(true);
        boolean add = service.add(account);
        verify(dao).add(account);
        assertTrue(add);
    }

    @Test
    public void updateArrears() {
        when(dao.payArrears(100.99, 1)).thenReturn(true);
        boolean b = service.updateArrears(100.99, 1);
        verify(dao).payArrears(100.99, 1);
        assertTrue(b);
    }

    @Test
    public void updateBalanceById() {
        when(dao.updateBalanceById(4.44, 2)).thenReturn(true);
        boolean b = service.updateBalanceById(4.44, 2);
        verify(dao).updateBalanceById(4.44, 2);
        assertTrue(b);
    }

    @Test
    public void getById() {
        CreditAccount account = new CreditAccount();
        when(dao.getById(3)).thenReturn(account);
        service.getById(3);
        verify(dao).getById(3);
        assertEquals(account, service.getById(3));
    }

    @Test
    public void getAll() {
        CreditAccount creditAccount = new CreditAccount();
        CreditAccount creditAccount1 = new CreditAccount();
        CreditAccount creditAccount2 = new CreditAccount();
        CreditAccount creditAccount3 = new CreditAccount();
        creditAccount.setLimit(180.14);
        List<CreditAccount> list = new ArrayList<>();
        list.add(creditAccount);
        list.add(creditAccount1);
        list.add(creditAccount2);
        list.add(creditAccount3);
        when(dao.getAll()).thenReturn(list);
        List<CreditAccount> all = service.getAll();
        verify(dao).getAll();
        assertEquals(180.14, all.get(0).getLimit(), 0);
    }

    @Test
    public void updateInterestCharges() {
        when(dao.updateInterestCharges(5.55, 4)).thenReturn(true);
        boolean b = service.updateInterestCharges(5.55, 4);
        verify(dao).updateInterestCharges(5.55, 4);
        assertTrue(b);
    }
}