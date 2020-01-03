package command;

import enums.Mappings;
import util.RootPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static enums.Attributes.ROOT;
import static enums.Mappings.SUCCESSFUL;

public class SuccessfulCommand implements Command {

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ROOT.getName(), RootPath.getRoot(request));
        return SUCCESSFUL;
    }
}
