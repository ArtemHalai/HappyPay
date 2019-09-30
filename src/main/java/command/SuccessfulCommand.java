package command;

import enums.Mappings;
import util.RootPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static enums.Attributes.ROOT;
import static enums.Mappings.SUCCESSFUL;

/**
 * Define an object used for executing successful command.
 */
public class SuccessfulCommand implements Command {

    /**
     * Method to execute successful actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ROOT.getName(), RootPath.getRoot(request));
        return SUCCESSFUL;
    }
}
