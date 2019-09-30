package command;

import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Mappings.*;
import static enums.Role.ADMIN;

/**
 * Define an object used for executing home command.
 */
public class HomeCommand implements Command {

    /**
     * Method to execute home actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute(ROLE.getName()) != null) {
            int role = (int) session.getAttribute(ROLE.getName());
            if (role == ADMIN.getRoleId()) {
                return HOME_ADMIN;
            }
        }
        return HOME;
    }
}
