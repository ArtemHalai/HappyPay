package service;

import dao.intefaces.CreditApprovementDAO;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.CreditRequestAdmin;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CreditApprovementServiceTest {

    @Mock
    private CreditApprovementDAO dao;

    @InjectMocks
    private CreditApprovementService service;

    @Before
    public void init() {
        service.setCreditApprovementDAO(dao);
    }

    @Test
    public void createCreditRequest() {
        CreditRequest request = new CreditRequest();
        when(dao.createCreditRequest(request)).thenReturn(true);
        service.createCreditRequest(request);
        verify(dao, times(1)).createCreditRequest(request);
    }

    @Test
    public void getById() {
        CreditApprovementOperation operation = new CreditApprovementOperation();
        when(dao.getById(anyInt())).thenReturn(operation);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(operation, service.getById(1));
    }

    @Test
    public void findAllByDecision() {
        CreditRequestAdmin requestAdmin = new CreditRequestAdmin();
        requestAdmin.setDecision(true);
        CreditRequestAdmin requestAdmin1 = new CreditRequestAdmin();
        requestAdmin1.setDecision(false);
        CreditRequestAdmin requestAdmin2 = new CreditRequestAdmin();
        requestAdmin2.setDecision(true);
        CreditRequestAdmin requestAdmin3 = new CreditRequestAdmin();
        requestAdmin.setDecision(true);
        List<CreditRequestAdmin> list = new ArrayList<>(Arrays.asList(requestAdmin, requestAdmin1, requestAdmin2, requestAdmin3));
        when(dao.findAllByDecision(false)).thenReturn(list.stream().filter(x -> !x.isDecision()).collect(Collectors.toList()));
        service.findAllByDecision(false);
        verify(dao, times(1)).findAllByDecision(false);
        assertFalse(service.findAllByDecision(false).get(0).isDecision());
    }

    @Test
    public void updateDecision() {
        when(dao.updateDecision(anyBoolean(), anyInt())).thenReturn(true);
        service.updateDecision(true, 1);
        verify(dao, times(1)).updateDecision(anyBoolean(), anyInt());
        assertTrue(service.updateDecision(false, 4));
    }

    @Test
    public void deleteRequest() {
        when(dao.deleteRequest(anyInt())).thenReturn(true);
        service.deleteRequest(1);
        verify(dao, times(1)).deleteRequest(anyInt());
        assertTrue(service.deleteRequest(4));
    }
}