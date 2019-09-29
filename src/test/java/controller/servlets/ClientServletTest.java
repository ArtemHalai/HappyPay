package controller.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static enums.Fields.ROLE;
import static enums.Mappings.CLIENT_ACCOUNTS;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ClientServlet servlet;

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(CLIENT_ACCOUNTS.getName());
        when(request.getSession()).thenReturn(session);
        servlet.doGet(request, response);
        verify(request, times(2)).getRequestURI();
        verify(session, times(1)).getAttribute(ROLE.getName());
    }
}