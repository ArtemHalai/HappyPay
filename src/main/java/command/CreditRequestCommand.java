package command;

import enums.Mappings;
import facade.CreditRequestFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.CreditRequest;
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
import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Mappings.CREDIT;

@Log4j
public class CreditRequestCommand implements Command {

    private CreditRequestFacade creditRequestFacade = new CreditRequestFacade();

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;
        int userId = (int) session.getAttribute(USER_ID.getName());
        creditRequestFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());

        UserAccount userAccount = creditRequestFacade.getUserAccount((Integer) session.getAttribute(USER_ID.getName()));
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        if (!creditRequestFacade.checkCredit(userId))
            return CREDIT;
        if (request.getParameter(AMOUNT.getName()) == null)
            return CREDIT_REQUEST;
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()).trim());
        if (amount <= 0) {
            errors.clear();
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
            request.setAttribute(ERRORS.getName(), errors);
            return CREDIT_REQUEST;
        }
        log.info("Client wants to open credit account");
        return createCreditRequest(request, userId, amount);
    }

    private Mappings createCreditRequest(HttpServletRequest request, int userId, double amount) {
        creditRequestFacade.setCreditApprovementService(ServiceFactory.getInstance().getCreditApprovementService());
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setUserId(userId);
        creditRequest.setDecision(false);
        creditRequest.setAmount(amount);
        creditRequest.setOperationDate(new Date(System.currentTimeMillis()));
        boolean createCreditRequest = creditRequestFacade.createCreditRequest(creditRequest);
        if (createCreditRequest)
            return SUCCESSFUL;
        errors.clear();
        errors.put(CREDIT.getName(), CREDIT_ERROR.getName());
        request.setAttribute(ERRORS.getName(), errors);
        return CREDIT_REQUEST;
    }
}
