package service;

import dao.intefaces.RefillDAO;
import model.AllOperationsDTO;
import model.RefillOperation;
import model.RefillPaginationDTO;
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
    
    @Test
    public void add() {
        RefillOperation operation = new RefillOperation();
        when(dao.add(operation)).thenReturn(true);
        boolean add = service.add(operation);
        verify(dao).add(operation);
        assertTrue(add);
    }

    @Test
    public void getById() {
        RefillOperation operation = new RefillOperation();
        when(dao.getById(1)).thenReturn(operation);
        RefillOperation byId = service.getById(1);
        verify(dao).getById(1);
        assertEquals(operation, byId);
    }

    @Test
    public void getRefillOperations() {
        RefillPaginationDTO paginationDTO = new RefillPaginationDTO();
        paginationDTO.setPage(4);
        when(dao.getRefillOperations(paginationDTO)).thenReturn(paginationDTO);
        int page = service.getRefillOperations(paginationDTO).getPage();
        verify(dao).getRefillOperations(paginationDTO);
        assertEquals(4, page, 0);
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