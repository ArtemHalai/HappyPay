package controller.servlets;

import factories.CommandFactory;
import util.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.*;

/**
 * Define deposit servlet class which extends HttpServlet class.
 *
 * @see HttpServlet
 */
public class DepositServlet extends HttpServlet {

    /**
     * This method called by the server to allow a servlet to handle a GET request.
     * Gets request URI using ActionHandler class and delegate action to CommandFactory to define which command to use for this request.
     * Then execute command and define what to do further.
     *
     * @param req  The HttpServletRequest object.
     * @param resp The HttpServletResponse object.
     * @throws IOException      If IO exception occurred while processing this request.
     * @throws ServletException If servlet exception occurred while processing this request.
     * @see CommandFactory
     * @see ActionHandler
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);

        switch (page) {
            case DEPOSIT:
                req.getRequestDispatcher(DEPOSIT_PAGE.getName()).forward(req, resp);
                break;
            case OPEN_DEPOSIT:
                resp.sendRedirect(OPEN_DEPOSIT.getName());
                break;
            case CLIENT_ACCOUNTS:
                resp.sendRedirect(CLIENT_ACCOUNTS.getName());
                break;
            case LOGIN_VIEW:
                resp.sendRedirect(LOGIN_VIEW.getName());
                break;
        }
    }
}
