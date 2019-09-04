package controller.servlets.admin_servlets;

import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;
import static enums.Mappings.UNSUCCESSFUL_PAGE;

public class CreditRequestAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case CREDIT_REQUEST_ADMIN:
                req.getRequestDispatcher(CREDIT_REQUEST_ADMIN_PAGE.getName()).forward(req, resp);
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
            case SUCCESSFUL:
                resp.sendRedirect(SUCCESSFUL_PAGE.getName());
                break;
            case UNSUCCESSFUL:
                resp.sendRedirect(UNSUCCESSFUL_PAGE.getName());
                break;
        }
    }
}
