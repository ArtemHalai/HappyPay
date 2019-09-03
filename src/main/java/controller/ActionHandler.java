package controller;

import enums.Mappings;
import factories.CommandFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionHandler {
    public static Mappings getPage(HttpServletRequest request, HttpServletResponse response){
        String action = request.getRequestURI()
                .substring(request.getRequestURI().lastIndexOf("/") + 1);

        Command command = CommandFactory.getCommand(action);

        return command.execute(request, response);
    }
}
