package util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private static final Logger LOG = Logger.getLogger(TransactionManager.class);

    public static void setRepeatableRead(Connection connection) {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransactionManager.class at setRepeatableRead() method");
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransactionManager.class at commitTransaction() method");
        }
    }

    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in TransactionManager.class at rollbackTransaction() method");
        }
    }
}
