package service;

import dao.intefaces.BillPaymentDAO;
import model.AllOperationsDTO;
import model.BillPaymentOperation;
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

    @Test
    public void add() {
        BillPaymentOperation operation = new BillPaymentOperation();
        when(dao.add(operation)).thenReturn(true);
        service.add(operation);
        verify(dao).add(operation);
    }

    @Test
    public void getById() {
        BillPaymentOperation operation = new BillPaymentOperation();
        when(dao.getById(1)).thenReturn(operation);
        BillPaymentOperation byId = service.getById(1);
        verify(dao).getById(1);
        assertEquals(operation, byId);
    }

    @Test
    public void getAllOperations() {
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        AllOperationsDTO operationsDTO1 = new AllOperationsDTO();
        when(dao.getLimitOperations(operationsDTO1)).thenReturn(operationsDTO);
        AllOperationsDTO allOperations = service.getAllOperations(operationsDTO1);
        verify(dao).getLimitOperations(operationsDTO1);
        assertEquals(operationsDTO, allOperations);
    }
}