package util;

import javax.servlet.http.HttpSession;

import java.util.function.Predicate;

import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Role.CLIENT;

/**
 * Define a class to check role and id of user.
 */
public class CheckRoleAndId {

    /**
     * Method to check user's role and id.
     *
     * @param session The HttpSession object.
     * @return <code>true</code> if user's session is active; <code>false</code> otherwise.
     */
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
