package command;

import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Mappings.HOME;

/**
 * Define an object used for executing logout command.
 */
public class LogoutCommand implements Command {

    /**
     * Method to execute logout actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute(ROLE.getName()) != null)
            session.invalidate();
        return HOME;
    }
}
