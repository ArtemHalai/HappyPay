package argument_matchers;

import model.User;
import org.mockito.ArgumentMatcher;

public class UserArgumentMatcher extends ArgumentMatcher<User> {

    private static final String USER = "USERNAME";
    private static final String PASS = "11111111";

    @Override
    public boolean matches(Object argument) {
        User user = (User) argument;
        return user.getUsername().equals(USER) && user.getPassword().equals(PASS);
    }
}
