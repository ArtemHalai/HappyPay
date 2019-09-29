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

import java.io.IOException;

import static enums.Fields.NAME;
import static enums.Mappings.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private RegistrationServlet servlet;

    @Before
    public void init(){
        when(request.getRequestURI()).thenReturn(REGISTRATION_VIEW.getName());
        when(request.getParameter(NAME.getName())).thenReturn(null);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(REGISTRATION_PAGE.getName())).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request, times(2)).getRequestURI();
        verify(request, times(1)).getRequestDispatcher(REGISTRATION_PAGE.getName());
    }

    @Test
    public void doPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(request, times(2)).getRequestURI();
    }
}