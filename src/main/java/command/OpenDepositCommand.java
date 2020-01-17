package command;

import enums.Mappings;
import facade.DepositAccountFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.ERRORS;
import static enums.Errors.*;
import static enums.Fields.AMOUNT;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;

@Log4j
public class OpenDepositCommand implements Command {

    private DepositAccountFacade depositAccountFacade = new DepositAccountFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session)) {
            return LOGIN_VIEW;
        }
        int userId = (int) session.getAttribute(USER_ID.getName());
        depositAccountFacade.setUserAccountService(ServiceFactory.getUserAccountService());
        UserAccount userAccount = depositAccountFacade.getUserAccount(userId);

        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity())) {
            return CLIENT_ACCOUNTS;
        }

        if (!depositAccountFacade.checkDeposit(userId)) {
            return DEPOSIT;
        }

        if (request.getParameter(AMOUNT.getName()) == null) {
            return OPEN_DEPOSIT;
        }

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()).trim());

        Map<String, String> errors = new HashMap<>();

        if (amount <= 0) {
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
            request.setAttribute(ERRORS.getName(), errors);
            return OPEN_DEPOSIT;
        }
        log.info("Client wants to open deposit account");
        depositAccountFacade.setDepositAccountService(ServiceFactory.getDepositAccountService());
        depositAccountFacade.setRefillService(ServiceFactory.getRefillService());

        boolean openDeposit = depositAccountFacade.openDeposit(userId, amount);
        if (openDeposit) {
            return SUCCESSFUL;
        }

        errors.put(DEPOSIT.getName(), NOT_ENOUGH_ERROR.getName());
        request.setAttribute(ERRORS.getName(), errors);
        return OPEN_DEPOSIT;
    }
}
