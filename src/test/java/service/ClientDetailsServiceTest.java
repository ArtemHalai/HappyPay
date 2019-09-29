package service;

import dao.intefaces.ClientDetailsDAO;
import model.ClientDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientDetailsServiceTest {

    @Mock
    private ClientDetailsDAO dao;

    @InjectMocks
    private ClientDetailsService service;

    @Before
    public void init() {
        service.setClientDetailsDAO(dao);
    }

    @Test
    public void add() {
        ClientDetails clientDetails = new ClientDetails();
        when(dao.add(clientDetails)).thenReturn(true);
        service.add(clientDetails);
        verify(dao, times(1)).add(clientDetails);
    }

    @Test
    public void getById() {
        ClientDetails clientDetails = new ClientDetails();
        when(dao.getById(anyInt())).thenReturn(clientDetails);
        service.getById(anyInt());
        verify(dao, times(1)).getById(anyInt());
        assertEquals(clientDetails, service.getById(1));
    }
}