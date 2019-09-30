package command;

import enums.Mappings;
import facade.DepositAccountFacade;
import factories.ServiceFactory;
import model.DepositAccount;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.USER_ID;
import static enums.Mappings.*;

/**
 * Define an object used for executing deposit account command on DepositAccountFacade.
 *
 * @see DepositAccountFacade
 */
public class DepositAccountCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DepositAccountCommand.class);

    private DepositAccountFacade depositAccountFacade = new DepositAccountFacade();

    /**
     * Method to execute deposit account actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (CheckRoleAndId.check(session)) {
            int userId = (int) session.getAttribute(USER_ID.getName());
            LOG.info("Client requests deposit account");
            depositAccountFacade.setDepositAccountService(ServiceFactory.getInstance().getDepositAccountService());
            DepositAccount depositAccount = depositAccountFacade.getDepositAccount(userId);

            depositAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
            UserAccount userAccount = depositAccountFacade.getUserAccount((Integer) session.getAttribute(USER_ID.getName()));
            if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
                return CLIENT_ACCOUNTS;

            if (depositAccount.getUserId() < 0) {
                return OPEN_DEPOSIT;
            }
            session.setAttribute(DEPOSIT_ACCOUNT.getName(), depositAccount);
            return DEPOSIT;
        }
        return LOGIN_VIEW;
    }
}