package command;

import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static enums.Mappings.SUCCESSFUL;

public class SuccessfulCommand implements Command {
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        return SUCCESSFUL;
    }
}
