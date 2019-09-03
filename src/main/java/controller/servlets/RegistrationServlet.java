package controller.servlets;

import controller.ActionHandler;
import enums.Mappings;
import factories.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.HOME;
import static enums.Mappings.REGISTRATION_PAGE;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case REGISTRATION_VIEW:
                req.getRequestDispatcher(REGISTRATION_PAGE.getName()).forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case ERRORS:
                req.getRequestDispatcher(REGISTRATION_PAGE.getName()).forward(req, resp);
                break;
            case HOME:
                resp.sendRedirect(HOME.getName());
                break;
        }
    }
}
