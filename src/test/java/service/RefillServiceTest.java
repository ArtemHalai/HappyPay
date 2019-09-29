package service;

import dao.intefaces.RefillDAO;
import model.AllOperationsDTO;
import model.RefillOperation;
import model.RefillPaginationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RefillServiceTest {

    @Mock
    private RefillDAO dao;

    @InjectMocks
    private RefillService service;

    @Before
    public void init() {
        service.setRefillDAO(dao);
    }

    @Test
    public void add() {
        RefillOperation operation = new RefillOperation();
        when(dao.add(operation)).thenReturn(true);
        service.add(operation);
        verify(dao, times(1)).add(operation);
    }

    @Test
    public void getById() {
        RefillOperation operation = new RefillOperation();
        when(dao.getById(anyInt())).thenReturn(operation);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(operation, service.getById(1));
    }

    @Test
    public void getRefillOperations() {
        RefillPaginationDTO paginationDTO = new RefillPaginationDTO();
        paginationDTO.setPage(4);
        when(dao.getRefillOperations(paginationDTO)).thenReturn(paginationDTO);
        service.getRefillOperations(paginationDTO);
        verify(dao, times(1)).getRefillOperations(paginationDTO);
        assertEquals(4, service.getRefillOperations(paginationDTO).getPage(), 0);
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