package util;

import javax.servlet.http.HttpSession;

import java.util.function.Predicate;

import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Role.CLIENT;

public class CheckRoleAndId {

    private CheckRoleAndId(){

    }

    public static boolean check(HttpSession session) {
        Predicate<HttpSession> p = x -> session.getAttribute(ROLE.getName()) != null && session.getAttribute(USER_ID.getName()) != null;
        if (p.test(session)) {
            int role = (int) session.getAttribute(ROLE.getName());
            int userId = (int) session.getAttribute(USER_ID.getName());
            return role == CLIENT.getRoleId() && userId > 0;
        }
        return false;
    }
}
