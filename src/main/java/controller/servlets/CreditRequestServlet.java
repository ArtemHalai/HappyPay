package controller.servlets;

import controller.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class CreditRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case CREDIT_REQUEST:
                req.getRequestDispatcher(CREDIT_REQUEST_PAGE.getName()).forward(req, resp);
                break;
            case LOGIN_VIEW:
                resp.sendRedirect(LOGIN_VIEW.getName());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case ERRORS:
                req.getRequestDispatcher(CLIENT_ACCOUNTS_PAGE.getName()).forward(req, resp);
                break;
            case HOME:
                req.getRequestDispatcher(HOME.getName()).forward(req, resp);
                break;
        }
    }
}
