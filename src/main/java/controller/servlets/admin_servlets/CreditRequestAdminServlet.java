package controller.servlets.admin_servlets;

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
 * Define credit request admin servlet class which extends HttpServlet class.
 *
 * @see HttpServlet
 */
public class CreditRequestAdminServlet extends HttpServlet {

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
