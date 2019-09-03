package factories;

import java.util.HashMap;
import java.util.Map;

import static enums.Mappings.*;

public class CommandFactory {

    private static final Map<String, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        commandMap.put(BILL_PAYMENT.getName(), new BillPaymentCommand());
        commandMap.put(LOGIN_VIEW.getName(), new LoginCommand());
        commandMap.put(LOGGED_IN.getName(), new LoggedInCommand());
        commandMap.put(REGISTRATION_VIEW.getName(), new RegistrationCommand());
        commandMap.put(CLIENT_DETAILS.getName(), new ClientDetailsCommand());
        commandMap.put(CREDIT_ACCOUNT.getName(), new CreditAccountCommand());
        commandMap.put(CREDIT_APPROVEMENT.getName(), new CreditApprovementCommand());
        commandMap.put(DEPOSIT_ACCOUNT.getName(), new DepositAccountCommand());
        commandMap.put(REFILL.getName(), new RefillCommand());
        commandMap.put(TRANSFER.getName(), new TransferCommand());
        commandMap.put(USER_ACCOUNT.getName(), new UserAccountCommand());
        commandMap.put(HOME.getName(), new HomeCommand());
        commandMap.put(SUCCESSFUL.getName(), new SuccessfulCommand());
        commandMap.put(UNSUCCESSFUL.getName(), new UnsuccessfulCommand());
    }

    public static Command getCommand(String action) {
        return commandMap.getOrDefault(action, new HomeCommand());
    }
}
