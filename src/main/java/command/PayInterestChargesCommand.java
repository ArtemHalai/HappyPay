package command;

import enums.Mappings;
import facade.PayInterestChargesFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
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

@Log4j
public class PayInterestChargesCommand implements Command {

    private PayInterestChargesFacade payInterestChargesFacade = new PayInterestChargesFacade();

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;
        int userId = (int) session.getAttribute(USER_ID.getName());
        payInterestChargesFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = payInterestChargesFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        payInterestChargesFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
        if (!payInterestChargesFacade.checkInterestCharges(userId))
            return CREDIT;

        if (request.getParameter(AMOUNT.getName()) == null)
            return PAY_INTEREST_CHARGES;

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        if (amount <= 0) {
            errors.clear();
            errors.put(AMOUNT.getName(), AMOUNT_ERROR.getName());
            request.setAttribute(ERRORS.getName(), errors);
            return PAY_INTEREST_CHARGES;
        }
        log.info("Client pays interest charges");
        boolean payedInterest = payInterestChargesFacade.payInterestCharges(userId, amount);
        if (payedInterest)
            return CLIENT_ACCOUNTS;
        errors.put(NOT_ENOUGH_AMOUNT.getName(), NOT_ENOUGH_ERROR.getName());
        return PAY_INTEREST_CHARGES;
    }
}
