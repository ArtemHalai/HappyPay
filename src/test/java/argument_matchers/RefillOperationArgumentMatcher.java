package argument_matchers;

import model.RefillOperation;
import org.mockito.ArgumentMatcher;

public class RefillOperationArgumentMatcher extends ArgumentMatcher<RefillOperation> {

    private static final int USER_ID = 1;
    private static final double BALANCE = 1000;

    @Override
    public boolean matches(Object argument) {
        RefillOperation refillOperation = (RefillOperation) argument;
        return refillOperation.getUserId() == USER_ID && refillOperation.getAmount() == BALANCE;
    }
}
