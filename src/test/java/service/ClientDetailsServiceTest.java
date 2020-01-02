package service;

import dao.intefaces.ClientDetailsDAO;
import model.ClientDetails;
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

    @Test
    public void add() {
        ClientDetails clientDetails = new ClientDetails();
        when(dao.add(clientDetails)).thenReturn(true);
        service.add(clientDetails);
        verify(dao).add(clientDetails);
    }

    @Test
    public void getById() {
        ClientDetails clientDetails = new ClientDetails();
        when(dao.getById(4)).thenReturn(clientDetails);
        ClientDetails byId = service.getById(4);
        verify(dao).getById(4);
        assertEquals(clientDetails, byId);
    }
}