package command;

import controller.validators.LoginValidator;
import controller.validators.Validator;
import enums.Mappings;
import enums.Role;
import exceptions.WrongUsernameOrPasswordException;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static enums.Attributes.*;
import static enums.Attributes.ERRORS;
import static enums.Errors.USER_DOES_NOT_EXIST;
import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;
import static enums.Role.ADMIN;
import static enums.Role.CLIENT;

public class LoginCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private LoginFacade loginFacade = new LoginFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(USERNAME.getName());
        String password = request.getParameter(PASSWORD.getName());

        User user = new User();

        Validator loginValidator = new LoginValidator(username, password);
        Map<String, String> errors = loginValidator.validate();
        if (!errors.isEmpty()) {
            request.setAttribute(ERRORS.getName(), errors);
            return ERROR;
        } else {
            user.setUsername(username);
            user.setPassword(password);
            user = loginFacade.getUserByUsernameAndPassword(user);

            if (user != null) {
                HttpSession session = request.getSession();

                LOG.info("User is logged in with username: " + username);
                if (user.getRole() == ADMIN.getRoleId()) {
                    session.setAttribute(ROLE.getName(), ADMIN.getRoleId());
                    return HOME_ADMIN;
                } else {
                    session.setAttribute(ROLE.getName(), CLIENT.getRoleId());
                    session.setAttribute(USER_ID.getName(), user.getUserId());
                    return HOME;
                }
                try {
                    throw new WrongUsernameOrPasswordException("Wrong username or password");
                } catch (WrongUsernameOrPasswordException e) {
                    LOG.error("Access denied to username = " + username + ", " + e.getMessage());
                }
            } else {
                errors.put(USER.getName(), USER_DOES_NOT_EXIST.getName());
                request.setAttribute(ERRORS.getName(), errors);
                return ERROR;
            }
        }
    }
}
