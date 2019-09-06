package command;

import enums.Mappings;
import facade.CreditAccountFacade;
import factories.ServiceFactory;
import model.CreditAccount;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class CreditAccountCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreditAccountCommand.class);

    private CreditAccountFacade creditAccountFacade = new CreditAccountFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());
        if (role == CLIENT.getRoleId() && userId > 0) {
            LOG.info("Client requests credit account");
            creditAccountFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
            CreditAccount creditAccount = creditAccountFacade.getCreditAccount(userId);
            session.setAttribute(CREDIT_ACCOUNT.getName(), creditAccount);
            return CREDIT;
        } else {
            return LOGIN_VIEW;
        }
    }
}
