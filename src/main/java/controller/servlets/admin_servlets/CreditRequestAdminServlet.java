package controller.servlets.admin_servlets;

import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class CreditRequestAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case CREDIT_REQUEST_ADMIN_REDIRECT:
                resp.sendRedirect(CREDIT_REQUEST_ADMIN.getName());
                break;
            case CREDIT_REQUEST_ADMIN:
                req.getRequestDispatcher(CREDIT_REQUEST_ADMIN_PAGE.getName()).forward(req, resp);
                break;
            case LOGIN_VIEW:
                resp.sendRedirect(LOGIN_VIEW.getName());
                break;
            case HOME_ADMIN:
                resp.sendRedirect(HOME_ADMIN.getName());
                break;
        }
    }
}
