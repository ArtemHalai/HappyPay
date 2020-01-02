package controller.servlets;

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

import static enums.Mappings.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SuccessfulServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private SuccessfulServlet servlet;

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(SUCCESSFUL.getName());
        when(request.getRequestDispatcher(SUCCESSFUL_PAGE.getName())).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request, times(2)).getRequestURI();
        verify(request).getRequestDispatcher(SUCCESSFUL_PAGE.getName());
    }
}