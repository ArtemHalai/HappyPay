package command;

import enums.Mappings;
import facade.TransferFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.TransferOperation;
import model.UserAccount;
import util.CheckOperationErrors;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static enums.Attributes.ACCOUNT;
import static enums.Errors.ACCOUNT_ERROR;
import static enums.Errors.ACCOUNT_NUMBER_ERROR;
import static enums.Fields.*;
import static enums.Mappings.*;

@Log4j
public class TransferCommand implements Command {

    private TransferFacade transferFacade = new TransferFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session)) {
            return LOGIN_VIEW;
        }

        int userId = (int) session.getAttribute(USER_ID.getName());
        transferFacade.setUserAccountService(ServiceFactory.getUserAccountService());
        UserAccount userAccount = transferFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity())) {
            return CLIENT_ACCOUNTS;
        }

        if (request.getParameter(AMOUNT.getName()) == null) {
            return TRANSFER;
        }

        double amount = Double.parseDouble(request.getParameter(AMOUNT.getName()));
        long recipientAccountNumber = Long.parseLong(request.getParameter(ACCOUNT_NUMBER.getName()));

        Map<String, String> errors = CheckOperationErrors.errorsEmpty(request, amount);

        if (recipientAccountNumber <= 0) {
            errors.put(ACCOUNT_NUMBER.getName(), ACCOUNT_NUMBER_ERROR.getName());
        }

        if (errors.isEmpty()) {
            if (transferOperation(userId, amount, recipientAccountNumber)) {
                return SUCCESSFUL;
            }
            errors.put(ACCOUNT.getName(), ACCOUNT_ERROR.getName());
        }
        return ERROR;
    }

    private boolean transferOperation(int userId, double amount, long recipientAccountNumber) {
        log.info("Client transfers amount: " + amount);
        TransferOperation transferOperation = new TransferOperation();
        transferOperation.setAmount(amount);
        transferOperation.setRecipientAccountNumber(recipientAccountNumber);
        transferOperation.setUserId(userId);
        transferFacade.setTransferService(ServiceFactory.getTransferService());
        transferFacade.setUserAccountService(ServiceFactory.getUserAccountService());
        return transferFacade.transfer(transferOperation);
    }
}
