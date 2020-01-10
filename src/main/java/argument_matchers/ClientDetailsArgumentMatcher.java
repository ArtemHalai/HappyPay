package argument_matchers;

import model.ClientDetails;
import org.mockito.ArgumentMatcher;

public class ClientDetailsArgumentMatcher extends ArgumentMatcher<ClientDetails> {
    private static final String INPUT_NAME = "name";
    private static final String INPUT_SURNAME = "surname";
    private static final String INPUT_USERNAME = "username";
    private static final String INPUT_PASSWORD = "12345678";
    private static final String INPUT_PHONE_NUMBER = "+380000000000";

    @Override
    public boolean matches(Object argument) {
        ClientDetails clientDetails = (ClientDetails) argument;
        return clientDetails.getName().equals(INPUT_NAME) && clientDetails.getSurname().equals(INPUT_SURNAME)
                && clientDetails.getUsername().equals(INPUT_USERNAME) && clientDetails.getPassword().equals(INPUT_PASSWORD)
                && clientDetails.getPhoneNumber().equals(INPUT_PHONE_NUMBER);
    }
}
