package command.admin_command;

import command.Command;
import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static enums.Fields.ROLE;
import static enums.Mappings.*;
import static enums.Role.ADMIN;

/**
 * Define an object used for executing home admin command.
 */
public class HomeAdminCommand implements Command {

    /**
     * Method to execute home admin actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute(ROLE.getName()) != null) {
            int role = (int) request.getSession().getAttribute(ROLE.getName());
            if (role == ADMIN.getRoleId()) {
                return HOME_ADMIN;
            }
        }
        return LOGIN_VIEW;
    }
}
