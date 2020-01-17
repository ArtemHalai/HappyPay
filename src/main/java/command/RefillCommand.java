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

import java.util.Map;

import static enums.AccountType.CREDIT_TYPE;
import static enums.Attributes.NOT_ENOUGH_AMOUNT;
import static enums.Errors.*;
import static enums.Fields.*;
import static enums.Mappings.*;

@Log4j
public class RefillCommand implements Command {

    private RefillFacade refillFacade = new RefillFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session)) {
            return LOGIN_VIEW;
        }

        int userId = (int) session.getAttribute(USER_ID.getName());
        refillFacade.setUserAccountService(ServiceFactory.getUserAccountService());
        UserAccount userAccount = refillFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity())) {
            return CLIENT_ACCOUNTS;
        }

        if (request.getParameter(AMOUNT.getName()) == null) {
            return REFILL;
        }

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));

        Map<String, String> errors = CheckOperationErrors.errorsEmpty(request, amount);

        if (errors.isEmpty()) {
            if (refillOperation(amount, userId)) {
                return SUCCESSFUL;
            }
            errors.put(NOT_ENOUGH_AMOUNT.getName(), NOT_ENOUGH_ERROR.getName());
        }
        return ERROR;
    }

    private boolean refillOperation(double amount, int userId) {
        log.info("Client refills his account with amount: " + amount);
        RefillOperation refillOperation = new RefillOperation();
        refillOperation.setAmount(amount);
        refillOperation.setUserId(userId);
        refillOperation.setSenderAccountType(CREDIT_TYPE.getName());
        refillFacade.setRefillService(ServiceFactory.getRefillService());
        refillFacade.setCreditAccountService(ServiceFactory.getCreditAccountService());
        return refillFacade.refill(refillOperation);
    }
}
