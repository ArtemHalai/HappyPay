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
import java.util.Collections;
import java.util.List;

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
        CreditApprovementOperation approvementOperationById = service.getById(1);
        verify(dao).getById(1);
        assertEquals(operation, approvementOperationById);
    }

    @Test
    public void findAllByDecision() {
        CreditRequestAdmin requestAdmin = new CreditRequestAdmin();
        requestAdmin.setDecision(true);
        CreditRequestAdmin requestAdmin1 = new CreditRequestAdmin();
        requestAdmin1.setDecision(false);
        List<CreditRequestAdmin> list = new ArrayList<>(Arrays.asList(requestAdmin, requestAdmin1));

        when(dao.findAllByDecision(false)).thenReturn(Collections.singletonList(list.get(1)));
        List<CreditRequestAdmin> allByDecision = service.findAllByDecision(false);
        verify(dao).findAllByDecision(false);
        assertFalse(allByDecision.get(0).isDecision());
    }

    @Test
    public void updateDecision() {
        int userId = 1;
        when(dao.updateDecision(true, userId)).thenReturn(true);
        service.updateDecision(true, userId);
        verify(dao).updateDecision(true, userId);
        assertTrue(service.updateDecision(true, userId));
    }

    @Test
    public void deleteRequest() {
        when(dao.deleteRequest(5)).thenReturn(true);
        boolean deletedRequest = service.deleteRequest(5);
        verify(dao).deleteRequest(5);
        assertTrue(deletedRequest);
    }
}