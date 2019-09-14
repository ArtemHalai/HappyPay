package command;

import enums.Mappings;
import facade.PayArrearsFacade;
import factories.ServiceFactory;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.ERRORS;
import static enums.Attributes.NOT_ENOUGH_AMOUNT;
import static enums.Errors.AMOUNT_ERROR;
import static enums.Errors.NOT_ENOUGH_ERROR;
import static enums.Fields.AMOUNT;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;

public class PayArrearsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(BillPaymentCommand.class);
    private PayArrearsFacade payArrearsFacade = new PayArrearsFacade();
    private Map<String, String> errors = new HashMap<>();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;
        int userId = (int) session.getAttribute(USER_ID.getName());
        payArrearsFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = payArrearsFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        payArrearsFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
        if (!payArrearsFacade.checkArrears(userId))
            return CREDIT;

        if (request.getParameter(AMOUNT.getName()) == null)
            return PAY_ARREARS;

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        if (amount <= 0) {
            errors.clear();
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
            request.setAttribute(ERRORS.getName(), errors);
            return PAY_ARREARS;
        }
        LOG.info("Client pays credit arrears");
        boolean payedArrears = payArrearsFacade.payArrears(userId, amount);
        if (payedArrears)
            return CLIENT_ACCOUNTS;
        errors.put(NOT_ENOUGH_AMOUNT.getName(), NOT_ENOUGH_ERROR.getName());
        return PAY_ARREARS;
    }
}
