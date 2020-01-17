package command;

import enums.Mappings;
import facade.DepositAccountFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.DepositAccount;
import model.UserAccount;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.USER_ID;
import static enums.Mappings.*;

@Log4j
public class DepositAccountCommand implements Command {

    private DepositAccountFacade depositAccountFacade = new DepositAccountFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (CheckRoleAndId.check(session)) {
            int userId = (int) session.getAttribute(USER_ID.getName());
            log.info("Client requests deposit account");
            depositAccountFacade.setDepositAccountService(ServiceFactory.getDepositAccountService());
            DepositAccount depositAccount = depositAccountFacade.getDepositAccount(userId);

            depositAccountFacade.setUserAccountService(ServiceFactory.getUserAccountService());
            UserAccount userAccount = depositAccountFacade.getUserAccount(userId);
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