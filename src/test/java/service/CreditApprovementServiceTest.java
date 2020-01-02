package service;

import dao.intefaces.CreditApprovementDAO;
import model.CreditApprovementOperation;
import model.CreditRequest;
import model.CreditRequestAdmin;
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
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CreditApprovementServiceTest {

    @Mock
    private CreditApprovementDAO dao;

    @InjectMocks
    private CreditApprovementService service;

    @Test
    public void createCreditRequest() {
        CreditRequest request = new CreditRequest();
        when(dao.createCreditRequest(request)).thenReturn(true);
        boolean creditRequest = service.createCreditRequest(request);
        verify(dao).createCreditRequest(request);
        assertTrue(creditRequest);
    }

    @Test
    public void getById() {
        CreditApprovementOperation operation = new CreditApprovementOperation();
        when(dao.getById(1)).thenReturn(operation);
        CreditApprovementOperation byId = service.getById(1);
        verify(dao).getById(1);
        assertEquals(operation, byId);
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
        List<CreditRequestAdmin> allByDecision = service.findAllByDecision(false);
        verify(dao).findAllByDecision(false);
        assertFalse(allByDecision.get(0).isDecision());
    }

    @Test
    public void updateDecision() {
        when(dao.updateDecision(true, 1)).thenReturn(true);
        service.updateDecision(true, 1);
        verify(dao).updateDecision(true, 1);
        assertTrue(service.updateDecision(true, 1));
    }

    @Test
    public void deleteRequest() {
        when(dao.deleteRequest(5)).thenReturn(true);
        boolean b = service.deleteRequest(5);
        verify(dao).deleteRequest(5);
        assertTrue(b);
    }
}