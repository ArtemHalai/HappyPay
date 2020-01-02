package service;

import dao.intefaces.TransferDAO;
import model.AllOperationsDTO;
import model.TransferOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    @Mock
    private TransferDAO dao;

    @InjectMocks
    private TransferService service;

    @Test
    public void add() {
        TransferOperation operation = new TransferOperation();
        when(dao.add(operation)).thenReturn(false);
        boolean add = service.add(operation);
        verify(dao).add(operation);
        assertFalse(add);
    }

    @Test
    public void getById() {
        TransferOperation operation = new TransferOperation();
        when(dao.getById(1)).thenReturn(operation);
        TransferOperation byId = service.getById(1);
        verify(dao).getById(1);
        assertEquals(operation, byId);
    }

    @Test
    public void getAllOperations() {
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(4);
        when(dao.getLimitOperations(operationsDTO)).thenReturn(operationsDTO);
        int userId = service.getAllOperations(operationsDTO).getUserId();
        verify(dao).getLimitOperations(operationsDTO);
        assertEquals(4, userId, 0);
    }
}