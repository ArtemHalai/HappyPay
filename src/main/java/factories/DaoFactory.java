package factories;

import dao.intefaces.*;
import dao.jdbc.*;

import java.sql.Connection;

public class DaoFactory {
    private static volatile DaoFactory factory = null;

    private static BillPaymentDAO billPaymentJDBC = null;
    private static ClientDetailsDAO clientDetailsJDBC = null;
    private static CreditAccountDAO creditAccountJDBC = null;
    private static CreditApprovementDAO creditApprovementJDBC = null;
    private static DepositAccountDAO depositAccountJDBC = null;
    private static RefillDAO refillJDBC = null;
    private static TransferDAO transferJDBC = null;
    private static UserAccountDAO userAccountJDBC = null;
    private static UserDAO userService = null;

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        if (factory == null) {
            synchronized (DaoFactory.class) {
                if (factory == null) {
                    factory = new DaoFactory();
                }
            }
        }
        return factory;
    }

    public BillPaymentDAO getBillPaymentDAO(Connection connection) {
        return new BillPaymentJDBC(connection);
    }

    public ClientDetailsDAO getClientDetailsDAO(Connection connection) {
        return new ClientDetailsJDBC(connection);
    }

    public CreditAccountDAO getCreditAccountDAO(Connection connection) {
        return new CreditAccountJDBC(connection);
    }

    public CreditApprovementDAO getCreditApprovementDAO(Connection connection) {
        return new CreditApprovementJDBC(connection);
    }

    public DepositAccountDAO getDepositAccountDAO(Connection connection) {
        return new DepositAccountJDBC(connection);
    }

    public RefillDAO getRefillDAO(Connection connection) {
        return new RefillJDBC(connection);
    }

    public TransferDAO getTransferDAO(Connection connection) {
        return new TransferJDBC(connection);
    }

    public UserAccountDAO getUserAccountDAO(Connection connection) {
        return new UserAccountJDBC(connection);
    }

    public UserDAO getUserDAO(Connection connection) {
        return new UserJDBC(connection);
    }
}
