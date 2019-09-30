package factories;

import command.*;
import command.admin_command.CreditRequestAdminCommand;
import command.admin_command.HomeAdminCommand;
import command.admin_command.LimitRequestAdminCommand;

import java.util.HashMap;
import java.util.Map;

import static enums.Mappings.*;

/**
 * A class that provides factory to get command implementation associated with given action.
 */
public class CommandFactory {

    private static final Map<String, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        commandMap.put(BILL_PAYMENT.getName(), new BillPaymentCommand());
        commandMap.put(LOGIN_VIEW.getName(), new LoginCommand());
        commandMap.put(LOGOUT.getName(), new LogoutCommand());
        commandMap.put(LOGGED_IN.getName(), new LoggedInCommand());
        commandMap.put(REGISTRATION_VIEW.getName(), new RegistrationCommand());
        commandMap.put(CREDIT.getName(), new CreditAccountCommand());
        commandMap.put(CREDIT_REQUEST.getName(), new CreditRequestCommand());
        commandMap.put(DEPOSIT.getName(), new DepositAccountCommand());
        commandMap.put(REFILL.getName(), new RefillCommand());
        commandMap.put(TRANSFER.getName(), new TransferCommand());
        commandMap.put(CLIENT_ACCOUNTS.getName(), new UserAccountCommand());
        commandMap.put(HOME.getName(), new HomeCommand());
        commandMap.put(HOME_ADMIN.getName(), new HomeAdminCommand());
        commandMap.put(SUCCESSFUL.getName(), new SuccessfulCommand());
        commandMap.put(PAY_ARREARS.getName(), new PayArrearsCommand());
        commandMap.put(REFILL_LIST_CLIENT.getName(), new RefillListClientCommand());
        commandMap.put(CREDIT_REQUEST_ADMIN.getName(), new CreditRequestAdminCommand());
        commandMap.put(LIMIT_REQUEST.getName(), new LimitRequestCommand());
        commandMap.put(LIMIT_REQUEST_ADMIN.getName(), new LimitRequestAdminCommand());
        commandMap.put(UPDATE_TERM.getName(), new UpdateTermCommand());
        commandMap.put(OPERATION_LIST_CLIENT.getName(), new OperationListClientCommand());
        commandMap.put(OPEN_DEPOSIT.getName(), new OpenDepositCommand());
        commandMap.put(PAY_INTEREST_CHARGES.getName(), new PayInterestChargesCommand());
    }

    /**
     * Gets the command from commandMap.
     *
     * @param action The action to identify which command implementation to use.
     * @return the command implementation associated with given action from {@link #commandMap}.
     */
    public static Command getCommand(String action) {
        return commandMap.getOrDefault(action, new HomeCommand());
    }
}
