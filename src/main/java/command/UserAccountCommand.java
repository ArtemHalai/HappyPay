package command;

import enums.Mappings;
import facade.UserAccountFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Attributes.ERRORS;
import static enums.Errors.VALIDITY_ERROR;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;

@Log4j
public class UserAccountCommand implements Command {

    private UserAccountFacade userAccountFacade = new UserAccountFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (CheckRoleAndId.check(session)) {
            int userId = (int) session.getAttribute(USER_ID.getName());
            log.info("Client requests his accounts");
            userAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
            UserAccount userAccount = userAccountFacade.getUserAccount(userId);
            if (userAccount.getValidity() != null && DateValidity.valid(userAccount.getValidity()))
                session.setAttribute(CLIENT_ACCOUNTS.getName(), userAccount);
            else
                request.setAttribute(ERRORS.getName(), VALIDITY_ERROR.getName());
            return CLIENT_ACCOUNTS;
        }
        return LOGIN_VIEW;
    }
}
