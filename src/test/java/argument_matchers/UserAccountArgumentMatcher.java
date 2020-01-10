package argument_matchers;

import model.UserAccount;
import org.mockito.ArgumentMatcher;

public class UserAccountArgumentMatcher extends ArgumentMatcher<UserAccount> {
    @Override
    public boolean matches(Object argument) {
        int userId = 1;
        UserAccount userAccount = (UserAccount) argument;
        return userAccount.getUserId() == userId && !userAccount.isCredit() && !userAccount.isDeposit();
    }
}
