package controller.servlets;

import controller.ActionHandler;
import enums.Mappings;
import factories.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.LOGGED_IN;
import static enums.Mappings.LOGGED_IN_PAGE;

public class LoggedInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case LOGGED_IN:
                req.getRequestDispatcher(LOGGED_IN_PAGE.getName()).forward(req, resp);
                break;
        }
    }
}
