package command;

import enums.Mappings;
import facade.RefillFacade;
import factories.ServiceFactory;
import model.AllOperationsDTO;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Attributes.*;
import static enums.Errors.NO_OPERATION_ERROR;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;

public class OperationListClientCommand implements Command {

    private static final Logger LOG = Logger.getLogger(OperationListClientCommand.class);

    private RefillFacade refillListClientFacade = new RefillFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!CheckRoleAndId.check(session))
            return LOGIN_VIEW;

        int userId = (int) session.getAttribute(USER_ID.getName());

        refillListClientFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
        UserAccount userAccount = refillListClientFacade.getUserAccount(userId);
        if (userAccount.getValidity() == null || !DateValidity.valid(userAccount.getValidity()))
            return CLIENT_ACCOUNTS;

        AllOperationsDTO allOperationsDTO = new AllOperationsDTO();
        allOperationsDTO.setUserId(userId);
        LOG.info("Client wants to see list of all operations.");
        refillListClientFacade.setRefillService(ServiceFactory.getInstance().getRefillService());
        refillListClientFacade.setBillPaymentService(ServiceFactory.getInstance().getBillPaymentService());
        refillListClientFacade.setTransferService(ServiceFactory.getInstance().getTransferService());
        AllOperationsDTO paginationDTO = refillListClientFacade.getAllOperations(allOperationsDTO);
        if (!paginationDTO.getList().isEmpty())
            request.setAttribute(OPERATION_LIST_CLIENT.getName(), paginationDTO.getList());
        else
            request.setAttribute(ERRORS.getName(), NO_OPERATION_ERROR.getName());

        return OPERATION_LIST_CLIENT;
    }
}
