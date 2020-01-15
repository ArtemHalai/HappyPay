package command;

import enums.Mappings;
import facade.LimitRequestFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.LimitRequest;
import model.UserAccount;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.ERRORS;
import static enums.Errors.AMOUNT_ERROR;
import static enums.Errors.CREDIT_ERROR;
import static enums.Fields.AMOUNT;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;

@Log4j
public class LimitRequestCommand implements Command {

    private LimitRequestFacade limitRequestFacade = new LimitRequestFacade();

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;
        int userId = (int) session.getAttribute(USER_ID.getName());
        UserAccount userAccount = limitRequestFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        if (request.getParameter(AMOUNT.getName()) == null)
            return LIMIT_REQUEST;
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()).trim());
        if (amount <= 0) {
            errors.clear();
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
            request.setAttribute(ERRORS.getName(), errors);
            return LIMIT_REQUEST;
        }

        log.info("Client wants to increase credit limit");
        limitRequestFacade.setLimitRequestService(ServiceFactory.getInstance().getLimitRequestService());
        LimitRequest limitRequest = new LimitRequest();
        limitRequest.setUserId(userId);
        limitRequest.setDecision(false);
        limitRequest.setAmount(amount);
        limitRequest.setOperationDate(new Date(System.currentTimeMillis()));
        boolean added = limitRequestFacade.addLimitRequest(limitRequest);
        if (added)
            return SUCCESSFUL;
        errors.clear();
        errors.put(CREDIT.getName(), CREDIT_ERROR.getName());
        request.setAttribute(ERRORS.getName(), errors);
        return LIMIT_REQUEST;
    }
}
