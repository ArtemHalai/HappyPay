package controller.servlets;

import org.junit.Before;
import org.junit.BeforeClass;
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
import static enums.Mappings.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RefillServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RefillServlet servlet;

    @Before
    public void init(){
        when(request.getRequestURI()).thenReturn(REFILL.getName());
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request, times(2)).getRequestURI();
        verify(session, times(1)).getAttribute(ROLE.getName());
        verify(response, times(1)).sendRedirect(LOGIN_VIEW.getName());
    }

    @Test
    public void doPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(request, times(2)).getRequestURI();
        verify(session, times(1)).getAttribute(ROLE.getName());
    }
}