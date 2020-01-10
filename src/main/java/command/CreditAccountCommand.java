package command;

import enums.Mappings;
import facade.CreditAccountFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.CreditAccount;
import model.UserAccount;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.USER_ID;
import static enums.Mappings.*;

@Log4j
public class CreditAccountCommand implements Command {

    private CreditAccountFacade creditAccountFacade = new CreditAccountFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (CheckRoleAndId.check(session)) {
            int userId = (int) session.getAttribute(USER_ID.getName());
            creditAccountFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
            creditAccountFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
            UserAccount userAccount = creditAccountFacade.getUserAccount(userId);
            if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity())) {
                return CLIENT_ACCOUNTS;
            }

            log.info("Client requests credit account");
            CreditAccount creditAccount = creditAccountFacade.getCreditAccount(userId);
            session.setAttribute(CREDIT_ACCOUNT.getName(), creditAccount);
            if (creditAccount.getUserId() > 0) {
                return CREDIT;
            }
            return CREDIT_REQUEST;
        }
        return LOGIN_VIEW;
    }
}
