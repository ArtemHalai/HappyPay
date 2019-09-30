package factories;

import dao.intefaces.*;
import dao.jdbc.*;
import enums.DAOEnum;
import exceptions.UnknownDaoImplementation;

import java.sql.Connection;

/**
 * A class that provides factory to get dao implementation.
 */
public class DaoFactory {

    private static volatile DaoFactory factory = null;

    /**
     * Private constructor to prevent
     * the instantiation of this class directly.
     */
    private DaoFactory() {

    }

    /**
     * Gets the instance of factory.
     *
     * @return the instance of {@link #factory}.
     */
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

    /**
     * Gets the BillPaymentDAO implementation.
     *
     * @return the instance of BillPaymentDAO implementation.
     * @see BillPaymentDAO
     */
    public BillPaymentDAO getBillPaymentDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case BILL_PAYMENT_JDBC:
                return new BillPaymentJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the LimitRequestDAO implementation.
     *
     * @return the instance of LimitRequestDAO implementation.
     * @see LimitRequestDAO
     */
    public LimitRequestDAO getLimitRequestDAO(Connection connection, DAOEnum daoEnum){
        switch (daoEnum) {
            case LIMIT_JDBC:
                return new LimitRequestJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the ClientDetailsDAO implementation.
     *
     * @return the instance of ClientDetailsDAO implementation.
     * @see ClientDetailsDAO
     */
    public ClientDetailsDAO getClientDetailsDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case CLIENT_DETAILS_JDBC:
                return new ClientDetailsJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the CreditAccountDAO implementation.
     *
     * @return the instance of CreditAccountDAO implementation.
     * @see CreditAccountDAO
     */
    public CreditAccountDAO getCreditAccountDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case CREDIT_ACCOUNT_JDBC:
                return new CreditAccountJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the CreditApprovementDAO implementation.
     *
     * @return the instance of CreditApprovementDAO implementation.
     * @see CreditApprovementDAO
     */
    public CreditApprovementDAO getCreditApprovementDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case CREDIT_APPROVEMENT_JDBC:
                return new CreditApprovementJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the DepositAccountDAO implementation.
     *
     * @return the instance of DepositAccountDAO implementation.
     * @see DepositAccountDAO
     */
    public DepositAccountDAO getDepositAccountDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case DEPOSIT_ACCOUNT_JDBC:
                return new DepositAccountJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the RefillDAO implementation.
     *
     * @return the instance of RefillDAO implementation.
     * @see RefillDAO
     */
    public RefillDAO getRefillDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case REFILL_JDBC:
                return new RefillJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the TransferDAO implementation.
     *
     * @return the instance of TransferDAO implementation.
     * @see TransferDAO
     */
    public TransferDAO getTransferDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case TRANSFER_JDBC:
                return new TransferJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the UserAccountDAO implementation.
     *
     * @return the instance of UserAccountDAO implementation.
     * @see UserAccountDAO
     */
    public UserAccountDAO getUserAccountDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case USER_ACCOUNT_JDBC:
                return new UserAccountJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }

    /**
     * Gets the UserDAO implementation.
     *
     * @return the instance of UserDAO implementation.
     * @see UserDAO
     */
    public UserDAO getUserDAO(Connection connection, DAOEnum daoEnum) {
        switch (daoEnum) {
            case USER_JDBC:
                return new UserJDBC(connection);
            default:
                throw new UnknownDaoImplementation("Unknown dao implementation");
        }
    }
}
