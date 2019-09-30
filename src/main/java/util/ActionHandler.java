package util;

import command.Command;
import enums.Mappings;
import factories.CommandFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Define a handler for actions received from request.
 */
public class ActionHandler {

    /**
     * Method used for getting necessary mapping using needed command for this.
     *
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @return The Mappings value which says what to do further.
     * @see Mappings
     * @see Command
     * @see CommandFactory
     */
    public static Mappings getPage(HttpServletRequest request, HttpServletResponse response){
        String action = request.getRequestURI()
                .substring(request.getRequestURI().lastIndexOf("/") + 1);

        Command command = CommandFactory.getCommand(action);

        return command.execute(request, response);
    }
}
