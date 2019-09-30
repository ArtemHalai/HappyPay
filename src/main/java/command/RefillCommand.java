package command;

import enums.Mappings;
import facade.RefillFacade;
import factories.ServiceFactory;
import model.RefillOperation;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckOperationErrors;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static enums.AccountType.CREDIT_TYPE;
import static enums.Attributes.NOT_ENOUGH_AMOUNT;
import static enums.Errors.*;
import static enums.Fields.*;
import static enums.Mappings.*;

/**
 * Define an object used for executing refill command on RefillFacade.
 *
 * @see RefillFacade
 */
public class RefillCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RefillCommand.class);

    private RefillFacade refillFacade = new RefillFacade();

    private Map<String, String> errors = new HashMap<>();

    /**
     * Method to execute refill actions on HttpServletRequest and HttpServletResponse.
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

    /**
     * Method to execute refill action in case of lack of errors in input fields.
     *
     * @param session The HttpSession
     * @param amount The amount to refill
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    private Mappings refillOperation(HttpSession session, double amount) {
        int userId = (int) session.getAttribute(USER_ID.getName());
        LOG.info("Client refills his account with amount: " + amount);
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
