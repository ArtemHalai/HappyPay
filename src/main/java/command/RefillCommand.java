package command;

import enums.Mappings;
import facade.RefillFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.RefillOperation;
import model.UserAccount;
import util.CheckOperationErrors;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static enums.AccountType.CREDIT_TYPE;
import static enums.Attributes.NOT_ENOUGH_AMOUNT;
import static enums.Errors.*;
import static enums.Fields.*;
import static enums.Mappings.*;

@Log4j
public class RefillCommand implements Command {

    private RefillFacade refillFacade = new RefillFacade();

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;

        refillFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = refillFacade.getUserAccount((Integer) session.getAttribute(USER_ID.getName()));
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        if (request.getParameter(AMOUNT.getName()) == null)
            return REFILL;

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));

        errors = CheckOperationErrors.errorsEmpty(request, amount);

        if (!errors.isEmpty())
            return ERROR;
        else
            return refillOperation(session, amount);
    }

    private Mappings refillOperation(HttpSession session, double amount) {
        int userId = (int) session.getAttribute(USER_ID.getName());
        log.info("Client refills his account with amount: " + amount);
        RefillOperation refillOperation = new RefillOperation();
        refillOperation.setAmount(amount);
        refillOperation.setUserId(userId);
        refillOperation.setSenderAccountType(CREDIT_TYPE.getName());
        refillFacade.setRefillService(ServiceFactory.getInstance().getRefillService());
        refillFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
        boolean refilled = refillFacade.refill(refillOperation);
        if (refilled)
            return SUCCESSFUL;
        errors.put(NOT_ENOUGH_AMOUNT.getName(), NOT_ENOUGH_ERROR.getName());
        return ERROR;
    }
}
