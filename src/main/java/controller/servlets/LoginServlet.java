package controller.servlets;

import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case LOGIN_VIEW:
                req.getRequestDispatcher(LOGIN_PAGE.getName()).forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case ERROR:
                req.getRequestDispatcher(LOGIN_PAGE.getName()).forward(req, resp);
                break;
            case HOME:
                resp.sendRedirect(HOME.getName());
                break;
            case HOME_ADMIN:
                resp.sendRedirect(HOME_ADMIN.getName());
                break;
        }
    }
}
