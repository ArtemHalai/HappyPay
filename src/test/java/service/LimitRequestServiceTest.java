package service;

import dao.intefaces.LimitRequestDAO;
import model.LimitRequest;
import model.LimitRequestAdmin;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LimitRequestServiceTest {

    @Mock
    private LimitRequestDAO dao;

    @InjectMocks
    private LimitRequestService service;

    @Before
    public void init() {
        service.setLimitRequestDAO(dao);
    }

    @Test
    public void add() {
        LimitRequest limitRequest = new LimitRequest();
        when(dao.add(limitRequest)).thenReturn(true);
        service.add(limitRequest);
        verify(dao, times(1)).add(limitRequest);
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

    @Test
    public void getById() {
        LimitRequest request = new LimitRequest();
        when(dao.getById(anyInt())).thenReturn(request);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(request, service.getById(1));
    }
}