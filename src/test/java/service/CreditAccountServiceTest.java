package service;

import dao.intefaces.CreditAccountDAO;
import model.CreditAccount;
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
public class CreditAccountServiceTest {

    @Mock
    private CreditAccountDAO dao;

    @InjectMocks
    private CreditAccountService service;

    @Before
    public void init() {
        service.setCreditAccountDAO(dao);
    }

    @Test
    public void add() {
        CreditAccount account = new CreditAccount();
        when(dao.add(account)).thenReturn(true);
        service.add(account);
        verify(dao, times(1)).add(account);
    }

    @Test
    public void updateArrears() {
        when(dao.payArrears(anyDouble(), anyInt())).thenReturn(true);
        service.updateArrears(100.88, 4);
        verify(dao, times(1)).payArrears(anyDouble(), anyInt());
        assertTrue(dao.payArrears(110.33, 10));
    }

    @Test
    public void updateBalanceById() {
        when(dao.updateBalanceById(anyDouble(), anyInt())).thenReturn(true);
        service.updateBalanceById(130.88, 1);
        verify(dao, times(1)).updateBalanceById(anyDouble(), anyInt());
        assertTrue(dao.updateBalanceById(89.33, 20));
    }

    @Test
    public void getById() {
        CreditAccount account = new CreditAccount();
        when(dao.getById(anyInt())).thenReturn(account);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(account, service.getById(1));
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
        service.getAll();
        verify(dao, times(1)).getAll();
        assertEquals(180.14, service.getAll().get(0).getLimit(), 0);
    }

    @Test
    public void updateInterestCharges() {
        when(dao.updateInterestCharges(anyDouble(), anyInt())).thenReturn(true);
        service.updateInterestCharges(130.18, 1);
        verify(dao, times(1)).updateInterestCharges(anyDouble(), anyInt());
        assertTrue(dao.updateInterestCharges(13.31, 29));
    }
}