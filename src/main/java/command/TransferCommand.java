package command;

import enums.Mappings;
import model.TransferOperation;
import org.apache.log4j.Logger;
import util.CheckOperationErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;

import static enums.Fields.*;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class TransferCommand implements Command {

    private static final Logger LOG = Logger.getLogger(TransferCommand.class);

    private TransferFacade transferFacade = new TransferFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());
        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        long recipientAccountNumber = Long.parseLong(request.getParameter(ACCOUNT_NUMBER.getName()));

        if (recipientAccountNumber <= 0) {
            return TRANSFER;
        } else if (role != CLIENT.getRoleId() && userId <= 0) {
            return LOGIN_VIEW;
        }

        if (!CheckOperationErrors.errorsEmpty(request, amount, recipientAccountNumber)) {
            return ERROR;
        } else {
            LOG.info("Client transfers amount: " + amount);
            TransferOperation transferOperation = new TransferOperation();
            transferOperation.setAmount(amount);
            transferOperation.setRecipientAccountNumber(recipientAccountNumber);
            transferOperation.setUserId(userId);
            transferOperation.setDate(new Date(System.currentTimeMillis()));
            boolean transferred = transferFacade.transfer(transferOperation);
            if (transferred)
                return SUCCESSFUL;
            else
                return UNSUCCESSFUL;
        }
    }
}
