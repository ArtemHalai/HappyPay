package service;

import dao.intefaces.TransferDAO;
import model.AllOperationsDTO;
import model.TransferOperation;
import org.junit.Before;
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

    @Before
    public void init() {
        service.setTransferDAO(dao);
    }

    @Test
    public void add() {
        TransferOperation operation = new TransferOperation();
        when(dao.add(operation)).thenReturn(true);
        service.add(operation);
        verify(dao, times(1)).add(operation);
    }

    @Test
    public void getById() {
        TransferOperation operation = new TransferOperation();
        when(dao.getById(anyInt())).thenReturn(operation);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(operation, service.getById(1));
    }

    @Test
    public void getAllOperations() {
        AllOperationsDTO operationsDTO = new AllOperationsDTO();
        operationsDTO.setUserId(4);
        when(dao.getLimitOperations(operationsDTO)).thenReturn(operationsDTO);
        service.getAllOperations(operationsDTO);
        verify(dao, times(1)).getLimitOperations(operationsDTO);
        assertEquals(4, service.getAllOperations(operationsDTO).getUserId(), 0);
    }
}