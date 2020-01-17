package command;

import controller.validators.RegistrationValidator;
import controller.validators.Validator;
import enums.Mappings;
import facade.RegistrationFacade;
import factories.ServiceFactory;
import lombok.extern.log4j.Log4j;
import model.ClientDetails;
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

@Log4j
public class RegistrationCommand implements Command {

    private RegistrationFacade registrationFacade = new RegistrationFacade();

    @Override
    public Mappings execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(NAME.getName());
        String surname = request.getParameter(SURNAME.getName());
        String username = request.getParameter(USERNAME.getName());
        String password = request.getParameter(PASSWORD.getName());
        String phoneNumber = request.getParameter(PHONE_NUMBER.getName());
        String birthday = request.getParameter(BIRTHDAY.getName());

        if (name == null) {
            return REGISTRATION_VIEW;
        }

        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setName(name);
        clientDetails.setSurname(surname);
        clientDetails.setUsername(username);
        clientDetails.setPassword(password);
        clientDetails.setPhoneNumber(phoneNumber);
        clientDetails.setBirthday(DateParser.parse(birthday));

        Map<String, String> errors = validation(clientDetails);
        if (!errors.isEmpty()) {
            request.setAttribute(ERRORS.getName(), errors);
            return ERROR;
        } else {
            registrationFacade.setClientDetailsService(ServiceFactory.getClientDetailsService());
            registrationFacade.setUserService(ServiceFactory.getUserService());
            registrationFacade.setUserAccountService(ServiceFactory.getUserAccountService());
            int userId = registrationFacade.addClientDetails(clientDetails);

            if (userId > 0) {
                HttpSession session = request.getSession();
                log.info("User was registered with username: " + username);
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

    private Map<String, String> validation(ClientDetails clientDetails) {
        Validator registrationValidator = new RegistrationValidator(clientDetails);
        return registrationValidator.validate();
    }
}
