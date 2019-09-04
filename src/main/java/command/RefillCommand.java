package command;

import enums.Mappings;
import model.RefillOperation;
import org.apache.log4j.Logger;
import util.CheckOperationErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;

import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class RefillCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RefillCommand.class);

    private RefillFacade refillFacade = new RefillFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        long senderAccount = Long.parseLong(request.getParameter(ACCOUNT_NUMBER.getName()));

        if (senderAccount <= 0) {
            return REFILL;
        } else if (role != CLIENT.getRoleId() && userId <= 0) {
            return LOGIN_VIEW;
        }

        if (!CheckOperationErrors.errorsEmpty(request, amount, senderAccount)) {
            return ERROR;
        } else {
            LOG.info("Client refills his account with amount: " + amount);
            RefillOperation refillOperation = new RefillOperation();
            refillOperation.setAmount(amount);
            refillOperation.setAccountNumber(senderAccount);
            refillOperation.setUserId(userId);
            refillOperation.setDate(new Date(System.currentTimeMillis()));
            boolean refilled = refillFacade.refill(refillOperation);
            if (refilled)
                return SUCCESSFUL;
            else
                return UNSUCCESSFUL;
        }
    }
}
