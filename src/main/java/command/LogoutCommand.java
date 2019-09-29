package command;

import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Mappings.HOME;

public class LogoutCommand implements Command {

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute(ROLE.getName()) != null)
            session.invalidate();
        return HOME;
    }
}
