package command;

import enums.Mappings;
import facade.BillPaymentFacade;
import factories.ServiceFactory;
import model.BillPaymentOperation;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckOperationErrors;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.ACCOUNT;
import static enums.Errors.ACCOUNT_ERROR;
import static enums.Errors.ACCOUNT_NUMBER_ERROR;
import static enums.Fields.*;
import static enums.Mappings.*;

public class BillPaymentCommand implements Command {

    private static final Logger LOG = Logger.getLogger(BillPaymentCommand.class);

    private BillPaymentFacade billPaymentFacade = new BillPaymentFacade();

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;

        int userId = (int) session.getAttribute(USER_ID.getName());

        billPaymentFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = billPaymentFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        if (request.getParameter(AMOUNT.getName()) == null)
            return BILL_PAYMENT;

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        long billNumber = Long.parseLong(request.getParameter(BILL_NUMBER.getName()));
        String purpose = request.getParameter(PURPOSE.getName());

        errors = CheckOperationErrors.errorsEmpty(request, amount);

        if (billNumber <= 0)
            errors.put(ACCOUNT_NUMBER.getName(), ACCOUNT_NUMBER_ERROR.getName());

        if (!errors.isEmpty())
            return ERROR;
        else
            return makeBillPayment(userId, amount, billNumber, purpose);
    }

    private Mappings makeBillPayment(int userId, double amount, long billNumber, String purpose) {
        LOG.info("Client pays bill from his account with amount: " + amount);
        BillPaymentOperation billPaymentOperation = new BillPaymentOperation();
        billPaymentOperation.setAmount(amount);
        billPaymentOperation.setBillNumber(billNumber);
        billPaymentOperation.setUserId(userId);
        billPaymentOperation.setPurpose(purpose);
        billPaymentFacade.setBillPaymentService(ServiceFactory.getInstance().getBillPaymentService());
        boolean payed = billPaymentFacade.payBill(billPaymentOperation);
        if (payed)
            return SUCCESSFUL;
        errors.put(ACCOUNT.getName(), ACCOUNT_ERROR.getName());
        return ERROR;
    }
}