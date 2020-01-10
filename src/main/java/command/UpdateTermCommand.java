package command;

import enums.Mappings;
import facade.UpdateTermFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.USER_ID;
import static enums.Mappings.*;

@Log4j
public class UpdateTermCommand implements Command {

    private UpdateTermFacade updateTermFacade = new UpdateTermFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session)) {
            return LOGIN_VIEW;
        }

        int userId = (int) session.getAttribute(USER_ID.getName());

        updateTermFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = updateTermFacade.getUserAccount(userId);
        if (userAccount.getValidity() != null && DateValidity.valid(userAccount.getValidity())) {
            return CLIENT_ACCOUNTS;
        }

        log.info("Client updates term of account");

        if (updateTermFacade.updateTerm(userId)) {
            return SUCCESSFUL;
        }
        return CLIENT_ACCOUNTS;
    }
}
