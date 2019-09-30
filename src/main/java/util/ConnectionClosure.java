package util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Define a class which close connection.
 */
public class ConnectionClosure {

    private static final Logger LOG = Logger.getLogger(ConnectionClosure.class);

    /**
     * Method used for closing the given connection.
     *
     * @param connection The Connection object.
     */
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in ConnectionClosure.class at close() method");
        }
    }
}
