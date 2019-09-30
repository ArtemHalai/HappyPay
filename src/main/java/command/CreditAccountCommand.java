package command;

import enums.Mappings;
import facade.CreditAccountFacade;
import factories.ServiceFactory;
import model.CreditAccount;
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
 * Define an object used for executing credit account command on CreditAccountFacade.
 *
 * @see CreditAccountFacade
 */
public class CreditAccountCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreditAccountCommand.class);

    private CreditAccountFacade creditAccountFacade = new CreditAccountFacade();

    /**
     * Method to execute credit account actions on HttpServletRequest and HttpServletResponse.
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
            creditAccountFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
            creditAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
            UserAccount userAccount = creditAccountFacade.getUserAccount(userId);
            if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
                return CLIENT_ACCOUNTS;

            LOG.info("Client requests credit account");
            CreditAccount creditAccount = creditAccountFacade.getCreditAccount(userId);
            session.setAttribute(CREDIT_ACCOUNT.getName(), creditAccount);
            if (creditAccount.getUserId() > 0)
                return CREDIT;
            return CREDIT_REQUEST;
        }
        return LOGIN_VIEW;
    }
}
