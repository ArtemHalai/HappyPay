package controller.servlets;

import controller.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.SUCCESSFUL_PAGE;

public class SuccessfulServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);
        switch (page) {
            case SUCCESSFUL:
                req.getRequestDispatcher(SUCCESSFUL_PAGE.getName()).forward(req, resp);
                break;
        }
    }
}