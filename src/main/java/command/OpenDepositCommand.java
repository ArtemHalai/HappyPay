package command;

import enums.Mappings;
import facade.DepositAccountFacade;
import factories.ServiceFactory;
import model.UserAccount;
import org.apache.log4j.Logger;
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

/**
 * Define an object used for executing open deposit command on DepositAccountFacade.
 *
 * @see DepositAccountFacade
 */
public class OpenDepositCommand implements Command {

    private static final Logger LOG = Logger.getLogger(OpenDepositCommand.class);

    private DepositAccountFacade depositAccountFacade = new DepositAccountFacade();

    private Map<String, String> errors = new HashMap<>();

    /**
     * Method to execute open deposit actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;
        int userId = (int) session.getAttribute(USER_ID.getName());
        depositAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = depositAccountFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        if (!depositAccountFacade.checkDeposit(userId))
            return DEPOSIT;
        if (request.getParameter(AMOUNT.getName()) == null)
            return OPEN_DEPOSIT;
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()).trim());

        if (amount <= 0) {
            errors.clear();
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
            request.setAttribute(ERRORS.getName(), errors);
            return OPEN_DEPOSIT;
        }
        LOG.info("Client wants to open deposit account");
        depositAccountFacade.setDepositAccountService(ServiceFactory.getInstance().getDepositAccountService());
        depositAccountFacade.setRefillService(ServiceFactory.getInstance().getRefillService());

        boolean openDeposit = depositAccountFacade.openDeposit(userId, amount);
        if (openDeposit)
            return SUCCESSFUL;
        errors.clear();
        errors.put(DEPOSIT.getName(), NOT_ENOUGH_ERROR.getName());
        request.setAttribute(ERRORS.getName(), errors);
        return OPEN_DEPOSIT;
    }
}
