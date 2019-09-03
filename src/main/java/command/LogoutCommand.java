package command;

import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static enums.Fields.ROLE;
import static enums.Mappings.HOME;
import static enums.Role.*;

public class LogoutCommand implements Command {

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        int role = (int) request.getSession().getAttribute(ROLE.getName());
        if (role == CLIENT.getRoleId() ||
                role == ADMIN.getRoleId()) {
            HttpSession session = request.getSession(false);
            session.invalidate();
        }
        return HOME;
    }
}
