package command;

import enums.Mappings;
import facade.RefillFacade;
import factories.ServiceFactory;
import model.RefillPaginationDTO;
import model.UserAccount;
import org.apache.log4j.Logger;
import util.CheckRoleAndId;
import util.DateValidity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Attributes.*;
import static enums.Errors.NO_REFILL_OPERATION_ERROR;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;

/**
 * Define an object used for executing refill list client command on RefillFacade.
 *
 * @see RefillFacade
 */
public class RefillListClientCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RefillListClientCommand.class);

    private RefillFacade refillListClientFacade = new RefillFacade();

    /**
     * Method to execute refill list client actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
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

        int page = 1;
        if (request.getParameter(PAGE.getName()) != null)
            page = Integer.parseInt(request.getParameter(PAGE.getName()));

        RefillPaginationDTO paginationDTO = new RefillPaginationDTO();
        paginationDTO.setUserId(userId);
        paginationDTO.setPage(page);
        LOG.info("Client wants to see list of refill operations.");
        refillListClientFacade.setRefillService(ServiceFactory.getInstance().getRefillService());
        RefillPaginationDTO refillPaginationDTO = refillListClientFacade.getRefillOperations(paginationDTO);
        if (!refillPaginationDTO.getList().isEmpty()) {
            request.setAttribute(REFILL_LIST_CLIENT.getName(), refillPaginationDTO.getList());
            request.setAttribute(COUNT.getName(), refillPaginationDTO.getCount());
            request.setAttribute(PAGE.getName(), refillPaginationDTO.getPage());
        } else {
            request.setAttribute(COUNT.getName(), 0);
            request.setAttribute(PAGE.getName(), page);
            request.setAttribute(ERRORS.getName(), NO_REFILL_OPERATION_ERROR.getName());
        }
        return REFILL_LIST_CLIENT;
    }
}
