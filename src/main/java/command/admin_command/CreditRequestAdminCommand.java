package command.admin_command;

import command.Command;
import enums.Mappings;
import facade.CreditRequestAdminFacade;
import factories.ServiceFactory;
import model.CreditRequestAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static enums.Attributes.ERRORS;
import static enums.Errors.NO_REQUESTS_ERROR;
import static enums.Fields.*;
import static enums.Mappings.*;

public class CreditRequestAdminCommand implements Command {

    private CreditRequestAdminFacade creditRequestAdminFacade = new CreditRequestAdminFacade();
    private static final boolean CREDIT_DECISION = false;

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute(ADMIN_ID.getName()) == null)
            return LOGIN_VIEW;
        if (request.getParameter(DECISION.getName()) == null) {
            creditRequestAdminFacade.setCreditApprovementService(ServiceFactory.getInstance().getCreditApprovementService());
            List<CreditRequestAdmin> list = creditRequestAdminFacade.findAllByDecision(CREDIT_DECISION);
            if (list.isEmpty())
                request.setAttribute(ERRORS.getName(), NO_REQUESTS_ERROR.getName());
            session.setAttribute(CREDIT_REQUEST_ADMIN.getName(), list);
            return CREDIT_REQUEST_ADMIN;
        }

        int userId = Integer.parseInt(request.getParameter(USER_ID.getName()));
        boolean decision = Boolean.parseBoolean(request.getParameter(DECISION.getName()));
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        if (!decision) {
            creditRequestAdminFacade.setCreditApprovementService(ServiceFactory.getInstance().getCreditApprovementService());
            creditRequestAdminFacade.deleteRequest(userId);
            return CREDIT_REQUEST_ADMIN_REDIRECT;
        }
        creditRequestAdminFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
        creditRequestAdminFacade.setCreditApprovementService(ServiceFactory.getInstance().getCreditApprovementService());
        creditRequestAdminFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());

        if (!creditRequestAdminFacade.updateCreditStatus(userId, decision, amount))
            return HOME_ADMIN;
        return CREDIT_REQUEST_ADMIN_REDIRECT;
    }
}
