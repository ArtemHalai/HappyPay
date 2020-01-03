package command;

import enums.Mappings;
import facade.BillPaymentFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.BillPaymentOperation;
import model.UserAccount;
import util.CheckOperationErrors;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static enums.Attributes.ACCOUNT;
import static enums.Attributes.ERRORS;
import static enums.Errors.ACCOUNT_ERROR;
import static enums.Errors.ACCOUNT_NUMBER_ERROR;
import static enums.Fields.*;
import static enums.Mappings.*;

@Log4j
public class BillPaymentCommand implements Command {

    private BillPaymentFacade billPaymentFacade = new BillPaymentFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session)) {
            return LOGIN_VIEW;
        }

        int userId = (int) session.getAttribute(USER_ID.getName());

        billPaymentFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = billPaymentFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity())) {
            return CLIENT_ACCOUNTS;
        }

        if (request.getParameter(AMOUNT.getName()) == null) {
            return BILL_PAYMENT;
        }

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        long billNumber = Long.parseLong(request.getParameter(BILL_NUMBER.getName()));
        String purpose = request.getParameter(PURPOSE.getName());

        Map<String, String> errors = CheckOperationErrors.errorsEmpty(request, amount);

        if (billNumber <= 0) {
            errors.put(ACCOUNT_NUMBER.getName(), ACCOUNT_NUMBER_ERROR.getName());
        }

        if (!errors.isEmpty()) {
            request.setAttribute(ERRORS.getName(), errors);
            return ERROR;
        } else {
            Mappings billPayed = makeBillPayment(userId, amount, billNumber, purpose);
            if (billPayed.equals(ERROR)) {
                errors.put(ACCOUNT.getName(), ACCOUNT_ERROR.getName());
                request.setAttribute(ERRORS.getName(), errors);
                return ERROR;
            } else {
                return billPayed;
            }
        }
    }

    private Mappings makeBillPayment(int userId, double amount, long billNumber, String purpose) {
        log.info("Client pays bill from his account with amount: " + amount);
        BillPaymentOperation billPaymentOperation = new BillPaymentOperation();
        billPaymentOperation.setAmount(amount);
        billPaymentOperation.setBillNumber(billNumber);
        billPaymentOperation.setUserId(userId);
        billPaymentOperation.setPurpose(purpose);
        billPaymentFacade.setBillPaymentService(ServiceFactory.getInstance().getBillPaymentService());
        boolean payed = billPaymentFacade.payBill(billPaymentOperation);
        if (payed) {
            return SUCCESSFUL;
        }
        return ERROR;
    }
}
