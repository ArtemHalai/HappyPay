package command;

import enums.Fields;
import enums.Mappings;
import facade.BillPaymentFacade;
import factories.ServiceFactory;
import model.BillPaymentOperation;
import org.apache.log4j.Logger;
import util.CheckOperationErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;

import static enums.Attributes.ACCOUNT;
import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class BillPaymentCommand implements Command {

    private static final Logger LOG = Logger.getLogger(BillPaymentCommand.class);
    private BillPaymentFacade billPaymentFacade = new BillPaymentFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        long billNumber = Long.parseLong(request.getParameter(BILL_NUMBER.getName()));
        String purpose = request.getParameter(PURPOSE.getName());
        String account = request.getParameter(ACCOUNT.getName());

        if (billNumber <= 0) {
            return BILL_PAYMENT;
        } else if (role != CLIENT.getRoleId() && userId <= 0) {
            return LOGIN_VIEW;
        }

        if (!CheckOperationErrors.errorsEmpty(request, amount, billNumber)) {
            return ERROR;
        } else {
            LOG.info("Client pays bill from his account with amount: " + amount);
            BillPaymentOperation billPaymentOperation = new BillPaymentOperation();
            billPaymentOperation.setAmount(amount);
            billPaymentOperation.setBillNumber(billNumber);
            billPaymentOperation.setUserId(userId);
            billPaymentOperation.setDate(new Date(System.currentTimeMillis()));
            billPaymentOperation.setPurpose(purpose);
            billPaymentFacade.setBillPaymentService(ServiceFactory.getInstance().getBillPaymentService());
            billPaymentFacade.setCreditAccountService(ServiceFactory.getInstance().getCreditAccountService());
            billPaymentFacade.setDepositAccountService(ServiceFactory.getInstance().getDepositAccountService());
            boolean payed = billPaymentFacade.payBill(billPaymentOperation, Fields.valueOf(account));
            if (payed)
                return SUCCESSFUL;
            else
                return UNSUCCESSFUL;
        }
    }
}
