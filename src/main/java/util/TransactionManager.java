package util;

import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class TransactionManager {

    private TransactionManager(){

    }

    public static void setRepeatableRead(Connection connection) {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (SQLException e) {
            log.error("SQLException occurred when setting isolation level", e);
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            log.error("SQLException occurred when tried to commit", e);
        }
    }

    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            log.error("SQLException occurred when tried to rollback", e);
        }
    }
}
