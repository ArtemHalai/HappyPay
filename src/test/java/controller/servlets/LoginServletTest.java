package controller.servlets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static enums.Fields.*;
import static enums.Mappings.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private LoginServlet servlet;

    @Before
    public void init(){
        when(request.getRequestURI()).thenReturn(LOGIN_VIEW.getName());
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(session.getAttribute(ROLE.getName())).thenReturn(2);
        servlet.doGet(request, response);
        verify(request, times(2)).getRequestURI();
        verify(session, times(2)).getAttribute(ROLE.getName());
        verify(response).sendRedirect(LOGGED_IN.getName());
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter(USERNAME.getName())).thenReturn("a");
        when(request.getParameter(PASSWORD.getName())).thenReturn("1");
        when(request.getRequestDispatcher(LOGIN_PAGE.getName())).thenReturn(dispatcher);
        servlet.doPost(request, response);
        verify(request, times(2)).getRequestURI();
        verify(session, times(2)).getAttribute(ROLE.getName());
        verify(request).getRequestDispatcher(LOGIN_PAGE.getName());
    }
}