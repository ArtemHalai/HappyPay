package command;

import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static enums.Mappings.LOGGED_IN;

public class LoggedInCommand implements Command {
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        return LOGGED_IN;
    }
}
