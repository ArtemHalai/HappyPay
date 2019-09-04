package command;

import enums.Mappings;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

public class CreditRequestCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreditRequestCommand.class);

    private CreditRequestFacade creditRequestFacade = new CreditRequestFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int role = (int) session.getAttribute(ROLE.getName());
        int userId = (int) session.getAttribute(USER_ID.getName());

        if (role == CLIENT.getRoleId() && userId > 0) {
            LOG.info("Client wants to open credit account");
            boolean hasCreditAccount = creditRequestFacade.hasCreditAccount(userId);
            if (hasCreditAccount)
                return UNSUCCESSFUL;
            return SUCCESSFUL;
        } else  {
            return LOGIN_VIEW;
        }
    }
}
