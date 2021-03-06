package command;

import controller.validators.RegistrationValidator;
import controller.validators.Validator;
import enums.Mappings;
import facade.RegistrationFacade;
import factories.ServiceFactory;
import model.ClientDetails;
import org.apache.log4j.Logger;
import util.DateParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static enums.Attributes.*;
import static enums.Attributes.ERRORS;
import static enums.Errors.USER_ALREADY_EXISTS;
import static enums.Fields.ROLE;
import static enums.Fields.USER_ID;
import static enums.Mappings.*;
import static enums.Role.CLIENT;

/**
 * Define an object used for executing registration command on RegistrationFacade.
 *
 * @see RegistrationFacade
 */
public class RegistrationCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    private RegistrationFacade registrationFacade = new RegistrationFacade();

    private Map<String, String> errors;

    /**
     * Method to execute registration actions on HttpServletRequest and HttpServletResponse.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @return The enum value representing mapping value.
     * @see enums.Mappings
     */
    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(NAME.getName());
        String surname = request.getParameter(SURNAME.getName());
        String username = request.getParameter(USERNAME.getName());
        String password = request.getParameter(PASSWORD.getName());
        String phoneNumber = request.getParameter(PHONE_NUMBER.getName());
        String birthday = request.getParameter(BIRTHDAY.getName());

        if (name == null)
            return REGISTRATION_VIEW;

        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setName(name);
        clientDetails.setSurname(surname);
        clientDetails.setUsername(username);
        clientDetails.setPassword(password);
        clientDetails.setPhoneNumber(phoneNumber);
        clientDetails.setBirthday(DateParser.parse(birthday));

        if (validation(clientDetails)) {
            request.setAttribute(ERRORS.getName(), errors);
            return ERROR;
        } else {
            registrationFacade.setClientDetailsService(ServiceFactory.getInstance().getClientDetailsService());
            registrationFacade.setUserService(ServiceFactory.getInstance().getUserService());
            registrationFacade.setUserAccountService(ServiceFactory.getInstance().getUserAccountService());
            int userId = registrationFacade.addUser(clientDetails);

            if (userId > 0) {
                HttpSession session = request.getSession();
                LOG.info("User was registered with username: " + username);
                session.setAttribute(ROLE.getName(), CLIENT.getRoleId());
                session.setAttribute(USER_ID.getName(), userId);
                return HOME;
            } else {
                errors.put(USER.getName(), USER_ALREADY_EXISTS.getName());
                request.setAttribute(ERRORS.getName(), errors);
                return ERROR;
            }
        }
    }

    /**
     * Method to validate data from input fields.
     *
     * @param clientDetails The ClientDetails object
     * @return <code>true</code> if errors were occurred while validating input fields;
     * <code>false</code> otherwise.
     * @see enums.Mappings
     */
    private boolean validation(ClientDetails clientDetails) {
        Validator registrationValidator = new RegistrationValidator(clientDetails);
        errors = registrationValidator.validate();
        return !errors.isEmpty();
    }
}
