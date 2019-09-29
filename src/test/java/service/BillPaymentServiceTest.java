package service;

import dao.intefaces.BillPaymentDAO;
import model.AllOperationsDTO;
import model.BillPaymentOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BillPaymentServiceTest {

    @Mock
    private BillPaymentDAO dao;

    @InjectMocks
    private BillPaymentService service;

    @Before
    public void init() {
        service.setBillPaymentDAO(dao);
    }

    @Test
    public void add() {
        BillPaymentOperation operation = new BillPaymentOperation();
        when(dao.add(operation)).thenReturn(true);
        service.add(operation);
        verify(dao, times(1)).add(operation);
    }

    @Test
    public void getById() {
        BillPaymentOperation operation = new BillPaymentOperation();
        when(dao.getById(anyInt())).thenReturn(operation);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(operation, service.getById(1));
    }

    @Test
    public void getAllOperations() {
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        when(dao.getLimitOperations(any())).thenReturn(operationsDTO);
        service.getAllOperations(operationsDTO);
        verify(dao, times(1)).getLimitOperations(any());
        assertEquals(operationsDTO, service.getAllOperations(any()));
    }
}