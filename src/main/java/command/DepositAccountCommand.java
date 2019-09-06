package command;

import enums.Mappings;
import facade.DepositAccountFacade;
import factories.ServiceFactory;
import model.DepositAccount;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class DepositAccountCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DepositAccountCommand.class);

    private DepositAccountFacade depositAccountFacade = new DepositAccountFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());
        if (role == CLIENT.getRoleId() && userId > 0) {
            LOG.info("Client requests deposit account");
            depositAccountFacade.setDepositAccountService(ServiceFactory.getInstance().getDepositAccountService());
            DepositAccount depositAccount = depositAccountFacade.getDepositAccount(userId);
            session.setAttribute(DEPOSIT_ACCOUNT.getName(), depositAccount);
            return DEPOSIT;
        } else {
            return LOGIN_VIEW;
        }
    }
}
