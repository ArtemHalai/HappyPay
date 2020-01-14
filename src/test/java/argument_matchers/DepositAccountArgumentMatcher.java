package argument_matchers;

import model.DepositAccount;
import org.mockito.ArgumentMatcher;

public class DepositAccountArgumentMatcher extends ArgumentMatcher<DepositAccount> {

    private static final int USER_ID = 1;
    private static final double BALANCE = 1000;
    private static final long ACCOUNT_NUMBER = 12345678;

    @Override
    public boolean matches(Object argument) {
        DepositAccount depositAccount = (DepositAccount) argument;
        return depositAccount.getUserId() == USER_ID && depositAccount.getBalance() == BALANCE
                && depositAccount.getAccountNumber() == ACCOUNT_NUMBER;
    }
}
