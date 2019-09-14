package command;

import enums.Mappings;
import facade.TransferFacade;
import factories.ServiceFactory;
import model.TransferOperation;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckOperationErrors;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static enums.Attributes.ACCOUNT;
import static enums.Errors.ACCOUNT_ERROR;
import static enums.Errors.ACCOUNT_NUMBER_ERROR;
import static enums.Fields.*;
import static enums.Mappings.*;

public class TransferCommand implements Command {

    private static final Logger LOG = Logger.getLogger(TransferCommand.class);

    private TransferFacade transferFacade = new TransferFacade();

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;

        int userId = (int) session.getAttribute(USER_ID.getName());
        transferFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = transferFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        if (request.getParameter(AMOUNT.getName()) == null)
            return TRANSFER;

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        long recipientAccountNumber = Long.parseLong(request.getParameter(ACCOUNT_NUMBER.getName()));

        errors = CheckOperationErrors.errorsEmpty(request, amount);

        if (recipientAccountNumber <= 0)
            errors.put(ACCOUNT_NUMBER.getName(), ACCOUNT_NUMBER_ERROR.getName());

        if (!errors.isEmpty())
            return ERROR;
        else
            return transferOperation(userId, amount, recipientAccountNumber);
    }

    private Mappings transferOperation(int userId, double amount, long recipientAccountNumber) {
        LOG.info("Client transfers amount: " + amount);
        TransferOperation transferOperation = new TransferOperation();
        transferOperation.setAmount(amount);
        transferOperation.setRecipientAccountNumber(recipientAccountNumber);
        transferOperation.setUserId(userId);
        transferFacade.setTransferService(ServiceFactory.getInstance().getTransferService());
        transferFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        boolean transferred = transferFacade.transfer(transferOperation);
        if (transferred)
            return SUCCESSFUL;
        errors.put(ACCOUNT.getName(), ACCOUNT_ERROR.getName());
        return ERROR;
    }
}
