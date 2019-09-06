package command;

import enums.Mappings;
import facade.CreditRequestFacade;
import factories.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class CreditRequestCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreditRequestCommand.class);

    private CreditRequestFacade creditRequestFacade = new CreditRequestFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));

        if (role == CLIENT.getRoleId() && userId > 0) {
            LOG.info("Client wants to open credit account");
            creditRequestFacade.setCreditApprovementService(ServiceFactory.getInstance().getCreditApprovementService());
            creditRequestFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
            boolean createCreditRequest = creditRequestFacade.createCreditRequest(userId, amount);
            if (createCreditRequest)
                return UNSUCCESSFUL;
            return SUCCESSFUL;
        } else {
            return LOGIN_VIEW;
        }
    }
}
