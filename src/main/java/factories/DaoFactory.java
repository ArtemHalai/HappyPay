package factories;

import dao.intefaces.*;
import dao.jdbc.*;
import enums.DAOEnum;
import exceptions.UnknownDaoImplementation;

import java.sql.Connection;

public class DaoFactory {

    private static final String EXCEPTION = "Unknown dao implementation";
    private static DaoFactory factory = null;

    private DaoFactory() {

    }

    public static synchronized DaoFactory getInstance() {
        if (factory == null) {
                    factory = new DaoFactory();
        }
        return factory;
    }

    public BillPaymentDAO getBillPaymentDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case BILL_PAYMENT_JDBC:
                return new BillPaymentJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public LimitRequestDAO getLimitRequestDAO(Connection connection, DAOEnum daoEnum){
        switch (daoEnum) {
            case LIMIT_JDBC:
                return new LimitRequestJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public ClientDetailsDAO getClientDetailsDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case CLIENT_DETAILS_JDBC:
                return new ClientDetailsJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public CreditAccountDAO getCreditAccountDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case CREDIT_ACCOUNT_JDBC:
                return new CreditAccountJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public CreditApprovementDAO getCreditApprovementDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case CREDIT_APPROVEMENT_JDBC:
                return new CreditApprovementJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public DepositAccountDAO getDepositAccountDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case DEPOSIT_ACCOUNT_JDBC:
                return new DepositAccountJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public RefillDAO getRefillDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case REFILL_JDBC:
                return new RefillJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public TransferDAO getTransferDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case TRANSFER_JDBC:
                return new TransferJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public UserAccountDAO getUserAccountDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case USER_ACCOUNT_JDBC:
                return new UserAccountJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }

    public UserDAO getUserDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case USER_JDBC:
                return new UserJDBC(connection);
            default:
                throw new UnknownDaoImplementation(EXCEPTION);
        }
    }
}
