package factories;

import command.*;
import model.CreditRequest;

import java.util.HashMap;
import java.util.Map;

import static enums.Mappings.*;

public class CommandFactory {

    private static final Map<String, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        commandMap.put(BILL_PAYMENT.getName(), new BillPaymentCommand());
        commandMap.put(LOGIN_VIEW.getName(), new LoginCommand());
        commandMap.put(LOGOUT.getName(), new LogoutCommand());
        commandMap.put(LOGGED_IN.getName(), new LoggedInCommand());
        commandMap.put(REGISTRATION_VIEW.getName(), new RegistrationCommand());
        commandMap.put(CREDIT_ACCOUNT.getName(), new CreditAccountCommand());
        commandMap.put(CREDIT_APPROVEMENT.getName(), new CreditRequestCommand());
        commandMap.put(DEPOSIT_ACCOUNT.getName(), new DepositAccountCommand());
        commandMap.put(REFILL.getName(), new RefillCommand());
        commandMap.put(TRANSFER.getName(), new TransferCommand());
        commandMap.put(USER_ACCOUNT.getName(), new UserAccountCommand());
        commandMap.put(HOME.getName(), new HomeCommand());
        commandMap.put(HOME_ADMIN.getName(), new HomeAdminCommand());
        commandMap.put(SUCCESSFUL.getName(), new SuccessfulCommand());
        commandMap.put(UNSUCCESSFUL.getName(), new UnsuccessfulCommand());
    }

    public static Command getCommand(String action) {
        return commandMap.getOrDefault(action, new HomeCommand());
    }
}
