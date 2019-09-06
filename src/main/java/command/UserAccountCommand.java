package command;

import enums.Fields;
import enums.Mappings;
import facade.UserAccountFacade;
import factories.ServiceFactory;
import model.CreditAccount;
import model.DepositAccount;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Attributes.ACCOUNT;
import static enums.Errors.VALIDITY_ERROR;
import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class UserAccountCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UserAccountCommand.class);

    private UserAccountFacade userAccountFacade = new UserAccountFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());
        if (role == CLIENT.getRoleId() && userId > 0) {
            LOG.info("Client requests his accounts");
            userAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
            UserAccount userAccount = userAccountFacade.getUserAccount(userId);
            if (DateValidity.valid(userAccount.getValidity()))
                session.setAttribute(CLIENT_ACCOUNTS.getName(), userAccount);
            else
                request.setAttribute(ERROR.getName(), VALIDITY_ERROR.getName());
            return CLIENT_ACCOUNTS;
        } else {
            return LOGIN_VIEW;
        }
    }
}
