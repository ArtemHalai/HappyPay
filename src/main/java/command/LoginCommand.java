package command;

import controller.validators.LoginValidator;
import controller.validators.Validator;
import enums.Mappings;
import facade.LoginFacade;
import factories.ServiceFactory;
import model.User;
import org.apache.log4j.Logger;
import util.CheckRoleAndId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static enums.Attributes.*;
import static enums.Attributes.ERRORS;
import static enums.Errors.USER_DOES_NOT_EXIST;
import static enums.Fields.*;
import static enums.Fields.PASSWORD;
import static enums.Fields.USERNAME;
import static enums.Mappings.*;
import static enums.Role.ADMIN;
import static enums.Role.CLIENT;

public class LoginCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private LoginFacade loginFacade = new LoginFacade();

    private Map<String, String> errors;

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        if (CheckRoleAndId.check(session) || session.getAttribute(ROLE.getName()) != null) {
            return LOGGED_IN;
        }
        String username = request.getParameter(USERNAME.getName());
        String password = request.getParameter(PASSWORD.getName());

        if (username == null || password == null) {
            return LOGIN_VIEW;
        }
        if (validation(username, password)) {
            request.setAttribute(ERRORS.getName(), errors);
            return ERROR;
        } else {
            return loginUser(request, session, username, password);
        }
    }

    private Mappings loginUser(HttpServletRequest request, HttpSession session, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        loginFacade.setUserService(ServiceFactory.getInstance().getUserService());
        User exist = loginFacade.getUserByUsernameAndPassword(user);

        if (exist != null) {
            LOG.info("User is logged in with username: " + username);
            if (exist.getRole() == ADMIN.getRoleId()) {
                session.setAttribute(ROLE.getName(), ADMIN.getRoleId());
                session.setAttribute(ADMIN_ID.getName(), exist.getUserId());
                return HOME_ADMIN;
            } else {
                session.setAttribute(ROLE.getName(), CLIENT.getRoleId());
                session.setAttribute(USER_ID.getName(), exist.getUserId());
                return HOME;
            }
        } else {
            errors.put(USER.getName(), USER_DOES_NOT_EXIST.getName());
            request.setAttribute(ERRORS.getName(), errors);
            return ERROR;
        }
    }

    private boolean validation(String username, String password) {
        Validator loginValidator = new LoginValidator(username, password);
        errors = loginValidator.validate();
        return !errors.isEmpty();
    }
}
