package command;

import enums.Fields;
import enums.Mappings;
import facade.UpdateTermFacade;
import factories.ServiceFactory;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ACCOUNT;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;

public class UpdateTermCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateTermCommand.class);

    private UpdateTermFacade updateTermFacade = new UpdateTermFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;

        int userId = (int) session.getAttribute(USER_ID.getName());

        updateTermFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = updateTermFacade.getUserAccount(userId);
        if (userAccount.getValidity() != null && DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        LOG.info("Client updates term of account");

        if (updateTermFacade.updateTerm(userId))
            return SUCCESSFUL;
        return CLIENT_ACCOUNTS;
    }
}
