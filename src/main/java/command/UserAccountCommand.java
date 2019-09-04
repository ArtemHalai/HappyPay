package command;

import enums.Mappings;
import model.UserAccount;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Mappings.CLIENT_ACCOUNTS;
import static enums.Mappings.LOGIN_VIEW;
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
            UserAccount userAccount = userAccountFacade.getUserAccount(userId);
            session.setAttribute(CLIENT_ACCOUNTS.getName(), userAccount);
            return CLIENT_ACCOUNTS;
        } else {
            return LOGIN_VIEW;
        }
    }
}
