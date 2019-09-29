package command.admin_command;

import command.Command;
import enums.Mappings;
import facade.LimitRequestAdminFacade;
import factories.ServiceFactory;
import model.LimitRequestAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static enums.Attributes.ERRORS;
import static enums.Errors.NO_REQUESTS_ERROR;
import static enums.Fields.*;
import static enums.Mappings.*;

public class LimitRequestAdminCommand implements Command {

    private LimitRequestAdminFacade limitRequestAdminFacade = new LimitRequestAdminFacade();
    private static final boolean LIMIT_DECISION = false;

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute(ADMIN_ID.getName()) == null)
            return LOGIN_VIEW;
        if (request.getParameter(DECISION.getName()) == null) {
            limitRequestAdminFacade.setLimitRequestService(ServiceFactory.getInstance().getLimitRequestService());
            List<LimitRequestAdmin> list = limitRequestAdminFacade.findAllByDecision(LIMIT_DECISION);
            if (list.isEmpty())
                request.setAttribute(ERRORS.getName(), NO_REQUESTS_ERROR.getName());
            session.setAttribute(LIMIT_REQUEST_ADMIN.getName(), list);
            return LIMIT_REQUEST_ADMIN;
        }

        int userId = Integer.parseInt(request.getParameter(USER_ID.getName()));
        boolean decision = Boolean.parseBoolean(request.getParameter(DECISION.getName()));
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        if (!decision) {
            limitRequestAdminFacade.setLimitRequestService(ServiceFactory.getInstance().getLimitRequestService());
            limitRequestAdminFacade.deleteRequest(userId);
            return LIMIT_REQUEST_ADMIN_REDIRECT;
        }
        limitRequestAdminFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
        limitRequestAdminFacade.updateLimit(userId, amount, decision);
        return LIMIT_REQUEST_ADMIN_REDIRECT;
    }
}
