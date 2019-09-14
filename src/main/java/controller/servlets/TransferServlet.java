package controller.servlets;

import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class TransferServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case TRANSFER:
                req.getRequestDispatcher(TRANSFER_PAGE.getName()).forward(req, resp);
                break;
            case LOGIN_VIEW:
                resp.sendRedirect(LOGIN_VIEW.getName());
                break;
            case CLIENT_ACCOUNTS:
                resp.sendRedirect(CLIENT_ACCOUNTS.getName());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case ERROR:
                req.getRequestDispatcher(TRANSFER_PAGE.getName()).forward(req, resp);
                break;
            case SUCCESSFUL:
                resp.sendRedirect(req.getServletPath() + "/" + SUCCESSFUL.getName());
                break;
            case CLIENT_ACCOUNTS:
                resp.sendRedirect(CLIENT_ACCOUNTS.getName());
                break;
        }
    }
}
