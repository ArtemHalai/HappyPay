package util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Define a class to handle transaction operations.
 */
public class TransactionManager {

    private static final Logger LOG = Logger.getLogger(TransactionManager.class);

    /**
     * This is a setter which sets the repeatable read isolation level to given connection.
     *
     * @param connection The Connection object.
     */
    public static void setRepeatableRead(Connection connection) {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransactionManager.class at setRepeatableRead() method");
        }
    }

    /**
     * This is a setter which commit transaction on given connection.
     *
     * @param connection The Connection object.
     */
    public static void commitTransaction(Connection connection) {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransactionManager.class at commitTransaction() method");
        }
    }

    /**
     * This is a setter which rollback transaction on given connection.
     *
     * @param connection The Connection object.
     */
    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransactionManager.class at rollbackTransaction() method");
        }
    }
}
