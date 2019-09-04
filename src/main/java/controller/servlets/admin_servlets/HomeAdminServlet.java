package controller.servlets.admin_servlets;

import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.HOME_ADMIN_PAGE;
import static enums.Mappings.LOGIN_VIEW;

public class HomeAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case HOME_ADMIN:
                req.getRequestDispatcher(HOME_ADMIN_PAGE.getName()).forward(req, resp);
                break;
            case LOGIN_VIEW:
                resp.sendRedirect(LOGIN_VIEW.getName());
                break;
        }
    }
}
