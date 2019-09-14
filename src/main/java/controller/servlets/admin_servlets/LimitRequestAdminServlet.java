package controller.servlets.admin_servlets;

import enums.Mappings;
import util.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class LimitRequestAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case LIMIT_REQUEST_ADMIN_REDIRECT:
                resp.sendRedirect(LIMIT_REQUEST_ADMIN.getName());
                break;
            case LIMIT_REQUEST_ADMIN:
                req.getRequestDispatcher(LIMIT_REQUEST_ADMIN_PAGE.getName()).forward(req, resp);
                break;
            case LOGIN_VIEW:
                resp.sendRedirect(LOGIN_VIEW.getName());
                break;
        }
    }
}
