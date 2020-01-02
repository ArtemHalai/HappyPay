package service;

import dao.intefaces.LimitRequestDAO;
import model.LimitRequest;
import model.LimitRequestAdmin;
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
public class LimitRequestServiceTest {

    @Mock
    private LimitRequestDAO dao;

    @InjectMocks
    private LimitRequestService service;
    
    @Test
    public void add() {
        LimitRequest limitRequest = new LimitRequest();
        when(dao.add(limitRequest)).thenReturn(false);
        boolean add = service.add(limitRequest);
        verify(dao).add(limitRequest);
        assertFalse(add);
    }

    @Test
    public void findAllByDecision() {
        LimitRequestAdmin request = new LimitRequestAdmin();
        request.setDecision(true);
        LimitRequestAdmin request1 = new LimitRequestAdmin();
        request1.setDecision(false);
        LimitRequestAdmin request2 = new LimitRequestAdmin();
        request2.setDecision(true);
        List<LimitRequestAdmin> list = new ArrayList<>(Arrays.asList(request, request1, request2));
        when(dao.findAllByDecision(false)).thenReturn(list.stream().filter(x -> !x.isDecision()).collect(Collectors.toList()));
        List<LimitRequestAdmin> allByDecision = service.findAllByDecision(false);
        verify(dao).findAllByDecision(false);
        assertFalse(allByDecision.get(0).isDecision());
    }

    @Test
    public void updateDecision() {
        when(dao.updateDecision(true, 6)).thenReturn(true);
        boolean b = service.updateDecision(true, 6);
        verify(dao).updateDecision(true, 6);
        assertTrue(b);
    }

    @Test
    public void deleteRequest() {
        when(dao.deleteRequest(7)).thenReturn(true);
        boolean b = service.deleteRequest(7);
        verify(dao).deleteRequest(7);
        assertTrue(b);
    }

    @Test
    public void getById() {
        LimitRequest request = new LimitRequest();
        when(dao.getById(4)).thenReturn(request);
        LimitRequest byId = service.getById(4);
        verify(dao).getById(4);
        assertEquals(request, byId);
    }
}