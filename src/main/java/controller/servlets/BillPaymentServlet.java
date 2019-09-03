package controller.servlets;

import controller.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

public class BillPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case BILL_PAYMENT:
                req.getRequestDispatcher(BILL_PAYMENT_PAGE.getName()).forward(req, resp);
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
                req.getRequestDispatcher(BILL_PAYMENT_PAGE.getName()).forward(req, resp);
                break;
            case SUCCESSFUL:
                resp.sendRedirect(req.getServletPath() + "/" + SUCCESSFUL.getName());
                break;
            case UNSUCCESSFUL:
                resp.sendRedirect(req.getServletPath() + "/" + UNSUCCESSFUL.getName());
                break;
        }
    }
}
