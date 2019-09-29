package controller.servlets.admin_servlets;

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

import static enums.Fields.ADMIN_ID;
import static enums.Mappings.LIMIT_REQUEST_ADMIN;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LimitRequestAdminServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LimitRequestAdminServlet servlet;

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(LIMIT_REQUEST_ADMIN.getName());
        when(request.getSession()).thenReturn(session);
        servlet.doGet(request, response);
        verify(request, times(2)).getRequestURI();
        verify(session, times(1)).getAttribute(ADMIN_ID.getName());
    }
}