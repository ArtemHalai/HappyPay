package controller.servlets;

import org.junit.Before;
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
import static enums.Fields.USER_ID;
import static enums.Mappings.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OpenDepositServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private OpenDepositServlet servlet;

    @Before
    public void init(){
        when(request.getRequestURI()).thenReturn(OPEN_DEPOSIT.getName());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(ROLE.getName())).thenReturn(2);
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