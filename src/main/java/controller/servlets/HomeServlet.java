package controller.servlets;

import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case HOME_ADMIN:
                resp.sendRedirect(HOME_ADMIN.getName());
                break;
            case HOME:
                req.getRequestDispatcher(INDEX_PAGE.getName()).forward(req, resp);
                break;
        }
    }
}