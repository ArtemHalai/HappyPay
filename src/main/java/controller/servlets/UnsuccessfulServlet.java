package controller.servlets;

import controller.ActionHandler;
import enums.Mappings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static enums.Mappings.UNSUCCESSFUL_PAGE;

public class UnsuccessfulServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mappings page = ActionHandler.getPage(req, resp);
        switch (page) {
            case UNSUCCESSFUL:
                req.getRequestDispatcher(UNSUCCESSFUL_PAGE.getName()).forward(req, resp);
                break;
        }
    }
}
