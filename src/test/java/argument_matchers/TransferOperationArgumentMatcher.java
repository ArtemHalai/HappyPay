package argument_matchers;

import model.TransferOperation;
import org.mockito.ArgumentMatcher;

public class TransferOperationArgumentMatcher extends ArgumentMatcher<TransferOperation> {

    private static final int USER_ID = 1;
    private static final double AMOUNT = 1000;
    private static final long RECIPIENT_ACCOUNT_NUMBER = 12345678;

    @Override
    public boolean matches(Object argument) {
        TransferOperation transferOperation = (TransferOperation) argument;
        return transferOperation.getUserId() == USER_ID && transferOperation.getAmount() == AMOUNT
                && transferOperation.getRecipientAccountNumber() == RECIPIENT_ACCOUNT_NUMBER;
    }
}
