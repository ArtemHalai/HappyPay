package controller.servlets;

import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class ClientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case CLIENT_ACCOUNTS:
                req.getRequestDispatcher(CLIENT_ACCOUNTS_PAGE.getName()).forward(req, resp);
                break;
            case LOGIN_VIEW:
                resp.sendRedirect(LOGIN_VIEW.getName());
                break;
        }
    }
}
