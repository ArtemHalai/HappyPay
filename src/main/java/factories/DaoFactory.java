package factories;

import dao.intefaces.DAO;
import dao.jdbc.*;
import enums.DAOEnum;

import java.sql.Connection;

public class DaoFactory {
    private static volatile DaoFactory factory = null;

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

    public DAO getDAO(DAOEnum dao, Connection connection) {

        if (dao == null)
            return null;
        DAO d = null;
        switch (dao) {
            case BILL_PAYMENT_JDBC:
                d = new BillPaymentJDBC(connection);
                break;
            case CLIENT_DETAILS_JDBC:
                d = new ClientDetailsJDBC(connection);
                break;
            case CREDIT_ACCOUNT_JDBC:
                d = new CreditAccountJDBC(connection);
                break;
            case CREDIT_APPROVEMENT_JDBC:
                d = new CreditApprovementJDBC(connection);
                break;
            case DEPOSIT_ACCOUNT_JDBC:
                d = new DepositAccountJDBC(connection);
                break;
            case REFILL_JDBC:
                d = new RefillJDBC(connection);
                break;
            case TRANSFER_JDBC:
                d = new TransferJDBC(connection);
                break;
            case USER_ACCOUNT_JDBC:
                d = new UserAccountJDBC(connection);
                break;
            case USER_JDBC:
                d = new UserJDBC(connection);
                break;
        }
        return d;
    }
}
